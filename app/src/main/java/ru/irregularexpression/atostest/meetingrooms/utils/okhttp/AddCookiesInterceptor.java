package ru.irregularexpression.atostest.meetingrooms.utils.okhttp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Unused until server doesn't use cookies.
 */

public class AddCookiesInterceptor implements Interceptor {
    public static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());
        String cookiestring = "";
        for (String cookie : preferences) {
            String[] parser = cookie.split(";");
            Log.d("Cookie", parser[0]);
            cookiestring = cookiestring + parser[0] + "; ";
        }
        builder.addHeader("Cookie", cookiestring);

        /*
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        */
        return chain.proceed(builder.build());
    }
}
