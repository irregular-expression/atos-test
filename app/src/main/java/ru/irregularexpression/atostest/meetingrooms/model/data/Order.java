package ru.irregularexpression.atostest.meetingrooms.model.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Order {
    private String id;
    private String roomName;
    private long timeStart;
    private long timeEnd;
    private String user;
    private String username;
    private String event;

    public Order(String roomName, long timeStart, long timeEnd, String user, String username, String event) {
        this.id = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.user = user;
        this.event = event;
        this.username = username;
    }

    public Order() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIntersect(Order order) {
        return roomName.equals(order.getRoomName()) && !(timeEnd < order.timeStart || order.getTimeEnd() < timeStart);
    }

    public boolean isIntersect(List<Order> orders) {
        for (Order order : orders) {
            if (isIntersect(order)) return true;
        }
        return false;
    }

    public String getFullDateStartAsString() {
        return getTimeMillisecondsAsString(timeStart);
    }

    public String getFullDateEndAsString() {
        return getTimeMillisecondsAsString(timeEnd);
    }

    private String getTimeMillisecondsAsString(long d) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(d));
        return String.format(Locale.FRANCE, "%02d-%02d-%04d %02d:%02d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    public String getStringDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timeStart));
        return String.format(Locale.FRANCE, "%02d-%02d-%04d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));

    }

    public String getStringTimeStart() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timeStart));
        return String.format(Locale.FRANCE, "%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    public String getStringTimeEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timeEnd));
        return String.format(Locale.FRANCE, "%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }
}
