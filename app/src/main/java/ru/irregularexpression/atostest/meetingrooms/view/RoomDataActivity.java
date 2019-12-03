package ru.irregularexpression.atostest.meetingrooms.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.irregularexpression.atostest.meetingrooms.MeetingRoomsApp;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.databinding.ActivityRoomdataBinding;
import ru.irregularexpression.atostest.meetingrooms.interfaces.contracts.RoomDataContract;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.presenter.RoomDataPresenter;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;
import ru.irregularexpression.atostest.meetingrooms.utils.MarginItemDecoration;
import ru.irregularexpression.atostest.meetingrooms.utils.StringUtils;
import ru.irregularexpression.atostest.meetingrooms.view.adapters.OrdersAdapter;

public class RoomDataActivity extends BaseActivity<RoomDataPresenter> implements RoomDataContract.View {

    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    public static final String KEY_ROOM_NAME = "room_name";
    public static final String KEY_HAS_PROJECTOR = "room_has_projector";
    public static final String KEY_HAS_BOARD = "room_has_board";
    public static final String KEY_ROOM_TYPE = "room_type";
    public static final String KEY_CHAIR_COUNT = "room_chair_count";

    ActivityRoomdataBinding binder;
    @Inject RoomDataContract.Presenter roomDataPresenter;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MeetingRoomsApp) getApplication()).getAppComponent().inject(this);
        roomDataPresenter.setView(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_roomdata);

        binder.boardSign.setVisibility(getIntent().getBooleanExtra(KEY_HAS_BOARD, false) ? View.VISIBLE : View.INVISIBLE);
        binder.projectorSign.setVisibility(getIntent().getBooleanExtra(KEY_HAS_PROJECTOR, false) ? View.VISIBLE : View.INVISIBLE);
        setSupportActionBar(binder.toolbar);
        roomDataPresenter.restoreRoomName(getIntent().getStringExtra(KEY_ROOM_NAME));
        getSupportActionBar().setTitle(getString(R.string.room_name_template, roomDataPresenter.getRestoredRoomName()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binder.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binder.roomChairCountDescription.setText(getString(R.string.room_places_description, getIntent().getIntExtra(KEY_CHAIR_COUNT,0)));
        binder.roomTypeDescription.setText(StringUtils.getRoomDescription(getIntent().getStringExtra(KEY_ROOM_TYPE),this));

        binder.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              roomDataPresenter.loadOrders();
            }
        });

        binder.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binder.recyclerview.addItemDecoration(new MarginItemDecoration(getResources().getInteger(R.integer.recycler_view_item_margin)));
        ordersAdapter = new OrdersAdapter(new ArrayList<Order>(), this);
        binder.recyclerview.setAdapter(ordersAdapter);
        binder.reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateOrderActivity.class);
                intent.putExtra(KEY_ROOM_NAME, roomDataPresenter.getRestoredRoomName());
                startActivity(intent);
            }
        });


        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(KEY_CURRENT_PROGRESS)) {
                showProgress();
            } else {
                hideProgress();
                roomDataPresenter.loadRestoredOrders();
            }
        } else {
            showProgress();
            roomDataPresenter.loadOrders();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_CURRENT_PROGRESS, roomDataPresenter.isRunning());
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

    public void showProgress() {
        if (!binder.swiperefresh.isRefreshing()) binder.swiperefresh.setRefreshing(true);
    }

    public void hideProgress() {
        binder.swiperefresh.setRefreshing(false);
    }


    @Override
    public void doOnFailure(int error) {
        hideProgress();
        showErrorAlert(getString(R.string.error, error),
                getString(ErrorHandler.getErrorTextResourse(error)));
    }

    @Override
    public void setOrders(List<Order> orders) {
        hideProgress();
        ordersAdapter.setData(orders);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
