package ru.irregularexpression.atostest.meetingrooms.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    /*
    for demo data creating
    */

    @GET("createUser")
    Call<ServerResponse> createUser(@Query("login") String login, @Query("password") String password, @Query("name") String name);

    @GET("createRoom")
    Call<ServerResponse> createRoom(@Query("name") String name, @Query("description") String description, @Query("chairsCount") Integer chairsCount, @Query("projector") Boolean projector, @Query("board") Boolean board);

}
