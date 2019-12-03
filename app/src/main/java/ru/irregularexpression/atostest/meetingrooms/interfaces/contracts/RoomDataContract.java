package ru.irregularexpression.atostest.meetingrooms.interfaces.contracts;

import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

public interface RoomDataContract {
    interface Model {}
    interface View {
        void doOnFailure(int error);
        void setOrders(List<Order> orders);
    }
    interface Presenter {
        void setView(RoomDataContract.View view);
        void restoreRoomName(String name);
        String getRestoredRoomName();
        void loadOrders();
        void loadRestoredOrders();
        boolean isRunning();
        void onDestroy();
    }
}
