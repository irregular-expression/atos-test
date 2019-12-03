package ru.irregularexpression.atostest.meetingrooms.utils.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.irregularexpression.atostest.meetingrooms.model.DataManager;

public class OfflineResponseInterceptor implements Interceptor {

    private Context context;

    public OfflineResponseInterceptor(Context context)  {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        String cacheHeaderValue = isNetworkAvailable()
                ? "public, max-age=5"
                : "public, only-if-cached, max-stale=2419200" ; //"public, max-age=5"
        Request request = originalRequest.newBuilder().build();
        Response response = chain.proceed(request);
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheHeaderValue)
                .build();

/*
        Request request = chain.request();
        if (!DataManager.getDataManager().isNetworkAvailable()) {
            request = request.newBuilder()
                    .header("Cache-Control",
                            "public, only-if-cached, max-stale=" + 2419200)
                    .build();
        }
        return chain.proceed(request);
*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
