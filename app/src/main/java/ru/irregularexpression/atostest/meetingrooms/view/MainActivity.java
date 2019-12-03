package ru.irregularexpression.atostest.meetingrooms.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.databinding.ActivityMainBinding;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.MainContract;
import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;
import ru.irregularexpression.atostest.meetingrooms.presenter.MainPresenter;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;
import ru.irregularexpression.atostest.meetingrooms.utils.MarginItemDecoration;
import ru.irregularexpression.atostest.meetingrooms.view.adapters.MeetingRoomAdapter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_CURRENT_PROGRESS = "current_progress";

    ActivityMainBinding binder;
    private MeetingRoomAdapter meetingRoomAdapter;

    @Inject MainContract.Presenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MeetingRoomsApp) getApplication()).getAppComponent().inject(this);
        mainPresenter.setView(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binder.navView.setNavigationItemSelectedListener(this);
        binder.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.loadRoomsList();
            }
        });

        binder.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binder.recyclerview.addItemDecoration(new MarginItemDecoration(getResources().getInteger(R.integer.recycler_view_item_margin)));
        meetingRoomAdapter = new MeetingRoomAdapter(new ArrayList<MeetingRoom>(), this);
        meetingRoomAdapter.publisher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getClickedRoomObserver());
        binder.recyclerview.setAdapter(meetingRoomAdapter);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(KEY_CURRENT_PROGRESS)) {
                showProgress();
            } else {
                mainPresenter.loadRestoredRoomsList();
            }
        } else {
            showProgress();
            mainPresenter.loadRoomsList();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_CURRENT_PROGRESS, mainPresenter.isRunning());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(KEY_CURRENT_PROGRESS)) {
            showProgress();
        } else {
            hideProgress();
        }
    }

    private DisposableObserver<MeetingRoom> getClickedRoomObserver() {
        return new DisposableObserver<MeetingRoom>() {
            @Override
            public void onNext(MeetingRoom meetingRoom) {
                Intent intent = new Intent(getApplicationContext(), RoomDataActivity.class);
                intent.putExtra(RoomDataActivity.KEY_ROOM_NAME, meetingRoom.getName());
                intent.putExtra(RoomDataActivity.KEY_HAS_BOARD, meetingRoom.hasBoard());
                intent.putExtra(RoomDataActivity.KEY_HAS_PROJECTOR, meetingRoom.hasProjector());
                intent.putExtra(RoomDataActivity.KEY_ROOM_TYPE, meetingRoom.getDescription());
                intent.putExtra(RoomDataActivity.KEY_CHAIR_COUNT, meetingRoom.getChairsCount());
                startActivity(intent);
            }

            @Override public void onError(Throwable e) {}

            @Override public void onComplete() {}
        };
    }

    public void showProgress() {
        if (!binder.swiperefresh.isRefreshing()) binder.swiperefresh.setRefreshing(true);
    }

    public void hideProgress() {
        binder.swiperefresh.setRefreshing(false);
    }


    @Override
    public void setRooms(List<MeetingRoom> rooms) {
        hideProgress();
        meetingRoomAdapter.setData(rooms);
    }

    @Override
    public void doOnFailure(int error) {
        hideProgress();
        showErrorAlert(getString(R.string.error, error),
                getString(ErrorHandler.getErrorTextResourse(error)));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
