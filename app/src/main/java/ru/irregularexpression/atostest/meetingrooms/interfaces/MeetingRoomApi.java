package ru.irregularexpression.atostest.meetingrooms.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.model.web.AuthorizationResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.MeetingRoomsResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrderCreateResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrdersResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.ServerResponse;

public interface MeetingRoomApi {
    @GET("auth")
    Call<AuthorizationResponse> getSession(@Query("login") String login, @Query("password") String password);

    @GET("rooms")
    Call<MeetingRoomsResponse> getRooms();

    @GET("orders")
    Call<OrdersResponse> getOrders(@Query("roomName") String room);

    @POST("reserveRoom")
    Call<OrderCreateResponse> createOrder(@Body Order order);

}
