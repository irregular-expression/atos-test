package ru.irregularexpression.atostest.meetingrooms.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.irregularexpression.atostest.meetingrooms.interfaces.dao.UserDao;
import ru.irregularexpression.atostest.meetingrooms.model.AppDatabase;
import ru.irregularexpression.atostest.meetingrooms.model.Repository;

@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(@ForApplication Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "meeting-rooms-app-db").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    UserDao providesUserDao(AppDatabase appDatabase) {
        return appDatabase.getUserDao();
    }

    @Singleton
    @Provides
    Repository repository(UserDao userDao) {
        return new Repository(userDao);
    }

}
