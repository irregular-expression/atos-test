package ru.irregularexpression.atostest.meetingrooms.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.interfaces.dao.UserDao;
import ru.irregularexpression.atostest.meetingrooms.model.AppDatabase;
import ru.irregularexpression.atostest.meetingrooms.model.Repository;
import ru.irregularexpression.atostest.meetingrooms.view.CreateOrderActivity;
import ru.irregularexpression.atostest.meetingrooms.view.LoginActivity;
import ru.irregularexpression.atostest.meetingrooms.view.MainActivity;
import ru.irregularexpression.atostest.meetingrooms.view.RoomDataActivity;

@Singleton
@Component( modules = { AppModule.class, RoomModule.class })
public interface AppComponent {
    void inject(MeetingRoomsApp meetingRoomsApp);
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
    void inject(RoomDataActivity roomDataActivity);
    void inject(CreateOrderActivity createOrderActivity);

    UserDao userDao();
    AppDatabase appDatabase();
    Repository repository();
}
