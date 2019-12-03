package ru.irregularexpression.atostest.meetingrooms.model.web;


import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;

public class MeetingRoomsResponse extends ServerResponse {
    private List<MeetingRoom> rooms;

    public List<MeetingRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<MeetingRoom> rooms) {
        this.rooms = rooms;
    }
}
