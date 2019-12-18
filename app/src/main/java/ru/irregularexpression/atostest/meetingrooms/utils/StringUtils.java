package ru.irregularexpression.atostest.meetingrooms.utils;

import android.content.Context;



import ru.irregularexpression.atostest.meetingrooms.R;

public class StringUtils {
    public static String getRoomDescription(String type, Context context) {
        switch (type) {
            case RoomType.TRAINING:
                return context.getResources().getString(R.string.room_type_training);
            case RoomType.CONFERENCE_HALL:
                return context.getResources().getString(R.string.room_type_conference_hall);
            default:
                return context.getResources().getString(R.string.room_type_default);
        }
    }

}
