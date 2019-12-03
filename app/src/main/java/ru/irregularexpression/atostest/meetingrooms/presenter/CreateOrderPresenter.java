package ru.irregularexpression.atostest.meetingrooms.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.CreateOrderContract;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrderCreateResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrdersResponse;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class CreateOrderPresenter extends BasePresenter implements CreateOrderContract.Presenter {

    private final static String TAG = "CreateOrderPresenter";
    private CreateOrderContract.View mView;
    private final DataManager dataManager;
    private Order order;
    private boolean running;

    @Inject
    public CreateOrderPresenter(@Named("DataManager") DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setView(CreateOrderContract.View view) {
        this.mView = view;
    }


    @Override
    public void onDestroy() {
        dataManager.clearTasks();
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public void loadDefaultOrderProperties(String roomName) {
          dataManager.addDisposableObserver(dataManager.getDefaultOrderObservable(roomName), new DisposableObserver<Order>() {
              @Override
              public void onNext(Order o) {
                  order = o;
                  mView.setOrder(o);
              }

              @Override
              public void onError(Throwable e) {
                  mView.doOnFailure(ErrorHandler.Error.UNKNOWN_ERROR);
              }

              @Override
              public void onComplete() {

              }
          });
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void sendOrder(String s) {
        order.setEvent(s);
        running = true;
        dataManager.addDisposableObserver(dataManager.sendOrderObservable(order), new DisposableObserver<OrderCreateResponse>() {
            @Override
            public void onNext(OrderCreateResponse orderCreateResponse) {
                if (orderCreateResponse.isSuccess() || orderCreateResponse.getError() == ErrorHandler.Error.TIME_IS_ALREADY_RESERVED) {
                    mView.doOnSuccess();
                } else {
                    mView.doOnFailure(orderCreateResponse.getError());
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
    public boolean isRunning() {
        return running;
    }

}
