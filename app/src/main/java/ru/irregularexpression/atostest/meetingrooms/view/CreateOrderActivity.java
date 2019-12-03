package ru.irregularexpression.atostest.meetingrooms.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.databinding.ActivityNewOrderBinding;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.CreateOrderContract;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.model.mockservice.MockService;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class CreateOrderActivity extends BaseActivity implements CreateOrderContract.View, DatePickerDialog.OnDateSetListener {

    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    private static final String KEY_PERCENT_PROGRESS = "percent_progress";

    ActivityNewOrderBinding binder;

    @Inject CreateOrderContract.Presenter createOrderPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MeetingRoomsApp) getApplication()).getAppComponent().inject(this);
        createOrderPresenter.setView(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_new_order);

        binder.cancelButton.setOnClickListener((view) -> finish());
        binder.okButton.setOnClickListener((view) -> {
            if (validateInput()) {
                createOrderPresenter.sendOrder(binder.name.getText().toString());
            } else {
                showErrorAlert(getString(R.string.error_default), getString(R.string.error_empty));
            }
        });
        binder.date.setOnClickListener((view) -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    CreateOrderActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");
        });
        binder.timeStart.setOnClickListener((view) -> {
            Calendar now = Calendar.getInstance();
            TimePickerDialog.OnTimeSetListener callback = (v, hourOfDay, minute, second) -> {
                String hourOfDayString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                String timeString = hourOfDayString + ":" + minuteString;
                binder.timeStart.setText(timeString);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(createOrderPresenter.getOrder().getTimeStart()));
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                createOrderPresenter.getOrder().setTimeStart(calendar.getTimeInMillis());
             };
            TimePickerDialog tpd = TimePickerDialog.newInstance(callback,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);
            tpd.show(getFragmentManager(), "Timepickerdialog");
        });
        binder.timeEnd.setOnClickListener((view) -> {
            Calendar now = Calendar.getInstance();
            TimePickerDialog.OnTimeSetListener callback = (v, hourOfDay, minute, second) -> {
                String hourOfDayString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                String timeString = hourOfDayString + ":" + minuteString;
                binder.timeEnd.setText(timeString);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(createOrderPresenter.getOrder().getTimeEnd()));
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                createOrderPresenter.getOrder().setTimeEnd(calendar.getTimeInMillis());
            };
            TimePickerDialog tpd = TimePickerDialog.newInstance(callback,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);
            tpd.show(getFragmentManager(), "Timepickerdialog");
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
            createOrderPresenter.loadDefaultOrderProperties(getIntent().getStringExtra(RoomDataActivity.KEY_ROOM_NAME));
        }
    }

    @Override
    public void doOnFailure(int error) {
           //send notification
        Intent intent = new Intent(this, MockService.class);
        intent.putExtra("error", error);
        startService(intent);
        finish();
        //showErrorAlert(getString(R.string.error, error), getString(ErrorHandler.getErrorTextResourse(error)));

    }

    @Override
    public void doOnSuccess() {
          //send notification
        Intent intent = new Intent(this, MockService.class);
        intent.putExtra("error", 0);
        startService(intent);
        finish();
        //showErrorAlert(getString(R.string.mock_order_is_sent_title), getString(R.string.mock_order_is_sent));

    }

    @Override
    public void setOrder(Order order) {
         hideProgress();
         binder.date.setText(order.getStringDate());
         binder.timeStart.setText(order.getStringTimeStart());
         binder.timeEnd.setText(order.getStringTimeEnd());
         binder.username.setText(order.getUsername());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String yearString = "" + year;
        String monthOfYearString = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : ""  + (monthOfYear + 1);
        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String date = dayOfMonthString + "." + monthOfYearString + "." + yearString;
        binder.date.setText(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(createOrderPresenter.getOrder().getTimeStart()));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        createOrderPresenter.getOrder().setTimeStart(calendar.getTimeInMillis());
        calendar.setTime(new Date(createOrderPresenter.getOrder().getTimeEnd()));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        createOrderPresenter.getOrder().setTimeEnd(calendar.getTimeInMillis());
    }

    private boolean validateInput() {
        if (binder.name.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_CURRENT_PROGRESS, createOrderPresenter.isRunning());
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
    public void showProgress() {
        binder.loginProgress.setVisibility(View.VISIBLE);
        binder.sendForm.setVisibility(View.GONE);
    }

    public void hideProgress() {
        binder.loginProgress.setVisibility(View.GONE);
        binder.sendForm.setVisibility(View.VISIBLE);
    }

    public void setProgressText(String progressText) {
        binder.loginProgressText.setText(progressText);
    }

}
