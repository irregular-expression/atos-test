package ru.irregularexpression.atostest.meetingrooms.utils;

import android.content.Context;



import ru.irregularexpression.atostest.meetingrooms.R;

public class StringUtils {
    public static String getRoomDescription(String type, Context context) {
        switch (type) {
            case "room_type_training":
                return context.getResources().getString(R.string.room_type_training);
            case "room_type_conference_hall":
                return context.getResources().getString(R.string.room_type_conference_hall);
            default:
                return context.getResources().getString(R.string.room_type_default);
        }
    }

}
