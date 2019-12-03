package ru.irregularexpression.atostest.meetingrooms.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableObserver;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.MainContract;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.RoomDataContract;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.model.web.MeetingRoomsResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrdersResponse;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class RoomDataPresenter extends BasePresenter implements RoomDataContract.Presenter {

    private final static String TAG = "LoginPresenter";
    private RoomDataContract.View mView;
    private final DataManager dataManager;
    private boolean running;
    private String restoredRoomName;
    private List<Order> restoredOrders;

    @Inject
    public RoomDataPresenter(@Named("DataManager") DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setView(RoomDataContract.View view) {
        this.mView = view;
    }

    @Override
    public void restoreRoomName(String name) {
        restoredRoomName = name;
    }

    @Override
    public String getRestoredRoomName() {
        return restoredRoomName;
    }

    @Override
    public void loadOrders() {
        running = true;
        dataManager.addDisposableObserver(dataManager.getOrdersObservable(getRestoredRoomName()), new DisposableObserver<OrdersResponse>() {
            @Override
            public void onNext(OrdersResponse ordersResponse) {
                if (ordersResponse.isSuccess()) {
                    restoredOrders = ordersResponse.getOrders();
                    mView.setOrders(restoredOrders);
                } else {
                    mView.doOnFailure(ordersResponse.getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.doOnFailure(ErrorHandler.Error.UNKNOWN_ERROR);
            }

            @Override
            public void onComplete() {
               running = false;
            }
        });
    }

    @Override
    public void loadRestoredOrders() {
        mView.setOrders(restoredOrders);
    }

    @Override
    public void onDestroy() {
         dataManager.clearTasks();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

}
