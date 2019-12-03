package ru.irregularexpression.atostest.meetingrooms.model.web;

public class OrderCreateResponse extends ServerResponse {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;
}
