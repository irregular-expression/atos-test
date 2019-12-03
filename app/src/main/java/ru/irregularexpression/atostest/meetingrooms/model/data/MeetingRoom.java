package ru.irregularexpression.atostest.meetingrooms.model.data;

public class MeetingRoom {

    private String name;
    private String description;
    private String earliestTime;
    private int chairsCount;
    private boolean projector;
    private boolean board;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChairsCount() {
        return chairsCount;
    }

    public void setChairsCount(int chairsCount) {
        this.chairsCount = chairsCount;
    }

    public boolean hasProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public boolean hasBoard() {
        return board;
    }

    public void setBoard(boolean board) {
        this.board = board;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

}
