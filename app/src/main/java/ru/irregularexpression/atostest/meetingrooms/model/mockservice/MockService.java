package ru.irregularexpression.atostest.meetingrooms.model.mockservice;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.view.RoomDataActivity;


/**
 * In real app here should be a FCMService class or some alternative solution
 * to handle push notifications from the server. This app isn't integrated with Firebase, so it'll be a mock class.
 */
public class MockService extends Service {
    final String LOG_TAG = "myLogs";

    public int error;
    private Context context;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        error = intent.getIntExtra("error", -1);
        context = this;
        mockTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();

    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Waits 5 seconds, then send notification.
     */
    void mockTask() {
        Completable c = Completable.fromRunnable(() -> {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        c.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                if (error == 0) {
                    sendNotification(getApplicationContext().getString(R.string.mock_manager_accept_title), getApplicationContext().getString(R.string.mock_manager_accept));
                    Intent intent = new Intent(RoomDataActivity.REFRESH_ORDERS);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    sendNotification(getApplicationContext().getString(R.string.mock_manager_decline_title), getApplicationContext().getString(R.string.mock_manager_decline));
                }
                stopSelf();
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

    public void sendNotification(String message, String title) {
        int notificationId = new Random().nextInt(60000);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "MockChannel")
                .setSmallIcon(R.mipmap.ic_launcher_round)  //a resource for your custom small icon
                .setContentTitle(title) //the "title" value you sent in your notification
                .setContentText(message) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }
}
