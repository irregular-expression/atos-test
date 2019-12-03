package ru.irregularexpression.atostest.meetingrooms.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

import static org.junit.Assert.*;

public class OrderTest {

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    public void getId() {
        assertNull(order.getId());
    }

    @Test
    public void setId() {
        String id = UUID.randomUUID().toString();
        order.setId(id);
        assertEquals(order.getId(), id);
    }

    @Test
    public void getRoomName() {
        assertNull(order.getRoomName());
    }

    @Test
    public void setRoomName() {
        String name = UUID.randomUUID().toString();
        order.setRoomName(name);
        assertEquals(order.getRoomName(), name);
    }

    @Test
    public void getTimeStart() {
        assertEquals(order.getTimeStart(), 0);
    }

    @Test
    public void setTimeStart() {
        long timeStart = new Random().nextLong();
        order.setTimeStart(timeStart);
        assertEquals(order.getTimeStart(), timeStart);
    }

    @Test
    public void getTimeEnd() {
        assertEquals(order.getTimeEnd(), 0);
    }

    @Test
    public void setTimeEnd() {
        long timeEnd = new Random().nextLong();
        order.setTimeEnd(timeEnd);
        assertEquals(order.getTimeEnd(), timeEnd);
    }

    @Test
    public void getUser() {
        assertNull(order.getUser());
    }

    @Test
    public void setUser() {
        String name = UUID.randomUUID().toString();
        order.setUser(name);
        assertEquals(order.getUser(), name);
    }

    @Test
    public void getEvent() {
        assertNull(order.getUser());
    }

    @Test
    public void setEvent() {
        String event = UUID.randomUUID().toString();
        order.setEvent(event);
        assertEquals(order.getUser(), event);
    }

    @Test
    public void isIntersect() {
        Calendar calendar = Calendar.getInstance();
        order.setTimeStart(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        order.setTimeEnd(calendar.getTimeInMillis());
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long otherStart = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        long otherEnd = calendar.getTimeInMillis();
        order.setRoomName("123");
        Order otherOrder = new Order("123", otherStart, otherEnd, "", "", "");
        System.out.println(otherStart + " " + otherEnd);
        System.out.println(otherOrder.getFullDateStartAsString() + " " + otherOrder.getFullDateEndAsString());
        assertTrue(order.getTimeEnd() < otherOrder.getTimeStart());
        System.out.println(order.getFullDateStartAsString() + " " + order.getFullDateEndAsString());
        assertFalse(order.isIntersect(otherOrder));

    }
}