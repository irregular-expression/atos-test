package ru.irregularexpression.atostest.meetingrooms.model.web;

import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

public class OrdersResponse extends ServerResponse {
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private List<Order> orders;
}
