package ru.irregularexpression.atostest.meetingrooms.interfaces.contracts;

import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;

public interface MainContract {
    interface Model {}
    interface View {
        void setRooms(List<MeetingRoom> rooms);
        void doOnFailure(int error);
    }
    interface Presenter {
        void setView(MainContract.View view);
        void loadRoomsList();
        void loadRestoredRoomsList();
        boolean isRunning();
        void onDestroy();
    }
}
