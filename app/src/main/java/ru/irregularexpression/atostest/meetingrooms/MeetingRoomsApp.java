package ru.irregularexpression.atostest.meetingrooms;

import android.app.Application;

import javax.inject.Inject;

import ru.irregularexpression.atostest.meetingrooms.di.AppComponent;
import ru.irregularexpression.atostest.meetingrooms.di.AppModule;
import ru.irregularexpression.atostest.meetingrooms.di.DaggerAppComponent;
import ru.irregularexpression.atostest.meetingrooms.di.RoomModule;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;

public class MeetingRoomsApp extends Application {

    private AppComponent appComponent;
    @Inject DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();
        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
