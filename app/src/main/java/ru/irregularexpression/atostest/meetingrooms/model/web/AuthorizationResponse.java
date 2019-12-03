package ru.irregularexpression.atostest.meetingrooms.model.web;

public class AuthorizationResponse extends ServerResponse {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
