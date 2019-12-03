package ru.irregularexpression.atostest.meetingrooms.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import javax.inject.Inject;

import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.databinding.ActivityLoginBinding;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.LoginContract;
import ru.irregularexpression.atostest.meetingrooms.presenter.LoginPresenter;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    private static final String KEY_PERCENT_PROGRESS = "percent_progress";

    ActivityLoginBinding binder;
    @Inject LoginContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MeetingRoomsApp) getApplication()).getAppComponent().inject(this);
        loginPresenter.setView(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binder.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                setProgressText(getString(R.string.web_progress));
                attemptLogin();
            }
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(KEY_CURRENT_PROGRESS)) {
                showProgress();
                setProgressText(savedInstanceState.getString(KEY_PERCENT_PROGRESS));
            } else {
                hideProgress();
            }
        } else {
            showProgress();
            loginPresenter.getSavedInput();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_CURRENT_PROGRESS, loginPresenter.isRunning());
        outState.putString(KEY_PERCENT_PROGRESS, binder.loginProgressText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(KEY_CURRENT_PROGRESS)) {
            showProgress();
            setProgressText(savedInstanceState.getString(KEY_PERCENT_PROGRESS));
        } else {
            hideProgress();
        }
    }

    public void attemptLogin() {
        binder.login.setError(null);
        binder.password.setError(null);

        String login = binder.login.getText().toString();
        String password = binder.password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            binder.password.setError(getString(R.string.error_empty_password));
            focusView = binder.password;
            cancel = true;
        }

        if (TextUtils.isEmpty(login)) {
            binder.login.setError(getString(R.string.error_login_required));
            focusView = binder.login;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            hideProgress();
        } else {
            loginPresenter.doLoginAction(login, password);
        }
    }

    public void showProgress() {
        binder.loginProgress.setVisibility(View.VISIBLE);
        binder.loginInputForm.setVisibility(View.GONE);
    }

    public void hideProgress() {
        binder.loginProgress.setVisibility(View.GONE);
        binder.loginInputForm.setVisibility(View.VISIBLE);
    }


    @Override
    public void setDefaultInput(String login, String password) {
        binder.login.setText(login);
        binder.password.setText(password);
        if (getIntent().getBooleanExtra(SplashActivity.KEY_APP_START, false)) {
            attemptLogin();
        } else {
            hideProgress();
        }
    }

    @Override
    public void doOnSuccess() {
        setProgressText("");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void doOnFailure(int error) {
        setProgressText("");
        hideProgress();
        if (error != ErrorHandler.Error.UNKNOWN_ERROR) showErrorAlert(getString(R.string.error, error),
                getString(ErrorHandler.getErrorTextResourse(error)));
    }

    public void setProgressText(String progressText) {
        binder.loginProgressText.setText(progressText);
    }

}
