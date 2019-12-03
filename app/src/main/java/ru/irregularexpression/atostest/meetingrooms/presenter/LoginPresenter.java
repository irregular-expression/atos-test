package ru.irregularexpression.atostest.meetingrooms.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableObserver;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.LoginContract;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.data.User;
import ru.irregularexpression.atostest.meetingrooms.model.web.ServerResponse;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private final static String TAG = "LoginPresenter";
    private LoginContract.View mView;
    private final DataManager dataManager;
    private boolean running;

    @Inject
    public LoginPresenter(@Named("DataManager") DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setView(LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void getSavedInput() {
        dataManager.addDisposableObserver(dataManager.getActiveUserObservable(), new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                if (user != null) {
                    mView.setDefaultInput(user.getLogin(), user.getPassword());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.doOnFailure(ErrorHandler.Error.UNKNOWN_ERROR);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void doLoginAction(String login, String password) {
        running = true;
        dataManager.addDisposableObserver(dataManager.getLoginActionObservable(login, password), new DisposableObserver<ServerResponse>() {
            @Override
            public void onNext(ServerResponse serverResponse) {
                if (serverResponse.isSuccess()) {
                    mView.doOnSuccess();
                } else {
                    mView.doOnFailure(serverResponse.getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.doOnFailure(ErrorHandler.Error.UNKNOWN_ERROR);
            }

            @Override
            public void onComplete() {
               running = false;
            }
        });
    }

    @Override
    public void onDestroy() {
         dataManager.clearTasks();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

}
