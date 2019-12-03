package ru.irregularexpression.atostest.meetingrooms.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

import static org.junit.Assert.*;

public class MeetingRoomTest {

    private MeetingRoom room;

    @Before
    public void setUp() {
        room = new MeetingRoom();
    }

    @Test
    public void getName() {
        assertNull(room.getName());
    }

    @Test
    public void setName() {
        String name = UUID.randomUUID().toString();
        room.setName(name);
        assertEquals(room.getName(), name);
    }

    @Test
    public void getDescription() {
        assertNull(room.getDescription());
    }

    @Test
    public void setDescription() {
        String description = UUID.randomUUID().toString();
        room.setDescription(description);
        assertEquals(room.getDescription(), description);
    }

    @Test
    public void getChairsCount() {
        assertEquals(room.getChairsCount(), 0);
    }

    @Test
    public void setChairsCount() {
        int value = new Random().nextInt();
        room.setChairsCount(value);
        assertEquals(room.getChairsCount(), value);
    }

    @Test
    public void hasProjector() {
        assertFalse(room.hasProjector());
    }

    @Test
    public void setProjector() {
        room.setProjector(true);
        assertTrue(room.hasProjector());
    }

    @Test
    public void hasBoard() {
        assertFalse(room.hasBoard());
    }

    @Test
    public void setBoard() {
        room.setBoard(true);
        assertTrue(room.hasBoard());
    }
}