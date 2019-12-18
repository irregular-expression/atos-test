package ru.irregularexpression.atostest.meetingrooms.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.irregularexpression.atostest.meetingrooms.di.ForApplication;
import ru.irregularexpression.atostest.meetingrooms.interfaces.MeetingRoomApi;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;
import ru.irregularexpression.atostest.meetingrooms.model.data.User;
import ru.irregularexpression.atostest.meetingrooms.model.web.AuthorizationResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.MeetingRoomsResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrderCreateResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.OrdersResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.ServerResponse;
import ru.irregularexpression.atostest.meetingrooms.utils.Constants;
import ru.irregularexpression.atostest.meetingrooms.utils.ErrorHandler;
import ru.irregularexpression.atostest.meetingrooms.utils.RoomType;
import ru.irregularexpression.atostest.meetingrooms.utils.okhttp.OfflineResponseInterceptor;

public class DataManager {

    private final static String TAG = "DataManager";

    private Context context;
    private CompositeDisposable disposables;
    private final Retrofit retrofit;
    private final MeetingRoomApi meetingRoomApi;
    private final Repository repository;

    @Inject
    public DataManager(@ForApplication Context context, Repository repository) {
        this.context = context;
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        int cacheSize = 5 * 1024 * 1024; // 5 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new OfflineResponseInterceptor(context))
                .addInterceptor(interceptor)
                //.addInterceptor(new AddCookiesInterceptor(context))  // - will be added if we have to send cookies, i.e. for passing session id
                //.addInterceptor(new RecievedCookiesInterceptor(context))  // - will be added if we have to recieve cookies, i.e. for passing session id
                .build();
        this.retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.meetingRoomApi = retrofit.create(MeetingRoomApi.class);

    }

    public Context getContext(){
        return context;
    }

    public MeetingRoomApi getApi() {
        return this.meetingRoomApi;
    }

    public <T> void addDisposableObserver(Observable<T> observable, DisposableObserver<T> d) {
        if (disposables.isDisposed()) disposables = new CompositeDisposable();
        disposables.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(d));
    }

    public void clearTasks() {
        disposables.clear();
    }

    public void finishTasks() {
        disposables.dispose();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Handling all api calls.
     */
    public <T extends ServerResponse> T callApi(Call<T> call, T response) {
        try {
            Response<T> r = call.execute();
            if (r.isSuccessful()) {
                if (r.body() != null && r.body().isSuccess()) {
                    response = r.body();
                    response.setSuccess(true);
                } else if (r.body() != null) {
                    response.setError(r.body().getError());
                } else {
                    response.setError(ErrorHandler.Error.INCORRECT_SERVER_RESPONSE);
                }
            } else {
                response.setError(r.code());
            }
        } catch (SocketTimeoutException se) {
            response.setError(ErrorHandler.Error.SOCKET_TIMEOUT_EXCEPTION);
        } catch (IOException e) {
            if (!isNetworkAvailable()) {
                response.setError(ErrorHandler.Error.NO_INTERNET_CONNECTION);
            } else {
                response.setError(ErrorHandler.Error.NO_SERVER_CONNECTION);
            }
        }
        return response;
    }


    public Observable<ServerResponse> getLoginActionObservable(final String login, final String password) {
        return Observable.fromCallable(() -> {
                AuthorizationResponse response = callApi(getApi().getSession(login, password), new AuthorizationResponse());
                if (response.isSuccess()) {
                    repository.logout();
                    repository.login(new User(response.getName(), login, password, true));
                }
                return response;
        });
    }

    public Observable<MeetingRoomsResponse> getRoomsObservable() {
        return Observable.fromCallable(() -> callApi(getApi().getRooms(), new MeetingRoomsResponse()));
    }

    public Observable<ServerResponse> getTestUserDataObservable() {
        return Observable.fromCallable(() -> {
             ServerResponse response = callApi(getApi().createUser("user", "12345", "Ivanov Ivan"), new ServerResponse());
             if (!response.isSuccess()) return response;
             response = callApi(getApi().createRoom("3.2424", RoomType.TRAINING, 38, true, false), new ServerResponse());
             if (!response.isSuccess()) return response;
             response = callApi(getApi().createRoom("2.1425", RoomType.CONFERENCE_HALL, 12, true, true), new ServerResponse());
             if (!response.isSuccess()) return response;
             response = callApi(getApi().createRoom("1.9425", RoomType.DEFAULT, 20, false, true), new ServerResponse());
             return response;
        });
    }

    public Observable<OrdersResponse> getOrdersObservable(final String room) {
        return Observable.fromCallable(() -> callApi(getApi().getOrders(room), new OrdersResponse()));
    }

    public Observable<User> getActiveUserObservable() {
        return Observable.fromCallable(repository::getActiveUser);
    }

    public Observable<Order> getDefaultOrderObservable(String roomName) {
        return Observable.fromCallable(() -> {
                User user = repository.getActiveUser();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                long start = calendar.getTimeInMillis();
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                long end = calendar.getTimeInMillis();
                return new Order(roomName, start, end, user.getLogin(), user.getName(), "Моё мероприятие");
        });
    }

    public Observable<OrderCreateResponse> sendOrderObservable(final Order order) {
        return Observable.fromCallable(() -> callApi(getApi().createOrder(order), new OrderCreateResponse()));
    }
}
