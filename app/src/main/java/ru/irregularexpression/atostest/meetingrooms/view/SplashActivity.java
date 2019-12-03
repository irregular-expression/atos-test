package ru.irregularexpression.atostest.meetingrooms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    public static final String KEY_APP_START = "app_start";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(KEY_APP_START, true);
        startActivity(intent);
        finish();
    }

}
