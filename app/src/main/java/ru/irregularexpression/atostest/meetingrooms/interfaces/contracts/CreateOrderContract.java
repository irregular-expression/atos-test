package ru.irregularexpression.atostest.meetingrooms.interfaces.contracts;

import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

public interface CreateOrderContract {
    interface Model {}
    interface View {
        void doOnFailure(int error);
        void doOnSuccess();
        void setOrder(Order order);
    }
    interface Presenter {
        void setView(CreateOrderContract.View view);
        Order getOrder();
        void loadDefaultOrderProperties(String roomName);
        void setOrder(Order order);
        void sendOrder(String description);
        boolean isRunning();
        void onDestroy();
    }
}
