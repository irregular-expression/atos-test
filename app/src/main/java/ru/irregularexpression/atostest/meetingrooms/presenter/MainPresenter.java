package ru.irregularexpression.atostest.meetingrooms.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableObserver;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.MainContract;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;
import ru.irregularexpression.atostest.meetingrooms.model.web.MeetingRoomsResponse;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private final static String TAG = "LoginPresenter";
    private MainContract.View mView;
    private final DataManager dataManager;
    private boolean running;
    private List<MeetingRoom> restoredListData;

    @Inject
    public MainPresenter(@Named("DataManager") DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadRoomsList() {
        running = true;
        dataManager.addDisposableObserver(dataManager.getRoomsObservable(), new DisposableObserver<MeetingRoomsResponse>() {
            @Override
            public void onNext(MeetingRoomsResponse meetingRoomsResponse) {
                if (meetingRoomsResponse.isSuccess()) {
                    restoredListData = meetingRoomsResponse.getRooms();
                    mView.setRooms(meetingRoomsResponse.getRooms());
                } else {
                    mView.doOnFailure(meetingRoomsResponse.getError());
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
    public void loadRestoredRoomsList() {
        if (restoredListData != null) mView.setRooms(restoredListData);
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
