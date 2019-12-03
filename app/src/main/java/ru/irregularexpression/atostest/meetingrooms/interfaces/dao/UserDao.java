package ru.irregularexpression.atostest.meetingrooms.interfaces.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.model.data.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> userList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Delete
    void deleteAll(List<User> users);

    @Query("SELECT * FROM users WHERE login = :login")
    User getUser(String login);

    @Query("SELECT * FROM users WHERE active")
    User getActiveUser();

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}
