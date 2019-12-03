package ru.irregularexpression.atostest.meetingrooms.model;

import javax.inject.Inject;

import ru.irregularexpression.atostest.meetingrooms.interfaces.dao.UserDao;
import ru.irregularexpression.atostest.meetingrooms.model.data.User;

public class Repository {

    private UserDao userDao;

    @Inject
    public Repository(UserDao userDao) {
        this.userDao = userDao;
    }

    public void logout() {
        User activeUser = userDao.getActiveUser();
        if (activeUser != null) {
            activeUser.setActive(false);
            userDao.insert(activeUser);
        }
    }

    public void login(User user) {
        user.setActive(true);
        userDao.insert(user);
    }

    public User getActiveUser() {
        return userDao.getActiveUser();
    }

}
