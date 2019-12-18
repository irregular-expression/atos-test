package ru.irregularexpression.atostest.meetingrooms.interfaces.contracts;

public interface LoginContract {
    interface Model {}
    interface View {
        void setDefaultInput(String login, String password);
        void showTestUserCredentials();
        void doOnSuccess();
        void doOnFailure(int error);
    }
    interface Presenter {
        void setView(LoginContract.View view);
        void getSavedInput();
        void doLoginAction(String login, String password);
        void createDemoSession();
        void onDestroy();
        boolean isRunning();
    }
}
