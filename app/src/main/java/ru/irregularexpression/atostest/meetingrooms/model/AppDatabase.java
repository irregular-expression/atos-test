package ru.irregularexpression.atostest.meetingrooms.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.irregularexpression.atostest.meetingrooms.interfaces.dao.UserDao;
import ru.irregularexpression.atostest.meetingrooms.model.data.User;

import static ru.irregularexpression.atostest.meetingrooms.model.AppDatabase.DB_VERSION;

@Database(entities = { User.class }, version = DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public static final int DB_VERSION = 2;

    public abstract UserDao getUserDao();


}
