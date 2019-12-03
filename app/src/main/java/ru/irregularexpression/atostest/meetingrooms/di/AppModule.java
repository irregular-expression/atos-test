package ru.irregularexpression.atostest.meetingrooms.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.CreateOrderContract;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.LoginContract;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.MainContract;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.RoomDataContract;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.Repository;
import ru.irregularexpression.atostest.meetingrooms.presenter.CreateOrderPresenter;
import ru.irregularexpression.atostest.meetingrooms.presenter.LoginPresenter;
import ru.irregularexpression.atostest.meetingrooms.presenter.MainPresenter;
import ru.irregularexpression.atostest.meetingrooms.presenter.RoomDataPresenter;

@Module
public class AppModule {

    private final MeetingRoomsApp application;

    public AppModule(MeetingRoomsApp application) {
        this.application = application;
    }

    /**
     * From Jake Wharton's solution: https://github.com/yongjhih/dagger2-sample
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton @Named("DataManager")
    DataManager provideDataManager(@ForApplication Context context, Repository repository) {
        return new DataManager(context, repository);
    }

    @Provides @Singleton
    public LoginContract.Presenter provideLoginPresenter(@Named("DataManager") DataManager dataManager) {
        return new LoginPresenter(dataManager);
    }

    @Provides @Singleton
    public RoomDataContract.Presenter provideRoomDataPresenter(@Named("DataManager") DataManager dataManager) {
        return new RoomDataPresenter(dataManager);
    }

    @Provides @Singleton
    public CreateOrderContract.Presenter provideCreateOrderPresenter(@Named("DataManager") DataManager dataManager) {
        return new CreateOrderPresenter(dataManager);
    }

    @Provides @Singleton
    public MainContract.Presenter provideMainPresenter(@Named("DataManager") DataManager dataManager) {
        return new MainPresenter(dataManager);
    }

}
