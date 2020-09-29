package com.slobodianiuk.hotel.db.entity;

public class ProcessedOrder {

    private int id;
    private int userId;
    private int orderId;
    private int apartmentId;
    private Double resultPrice;
    private int orderStatusId;

    public ProcessedOrder() {
    }

    public ProcessedOrder(int id, int userId, int orderId, int apartmentId, Double resultPrice, int orderStatusId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.apartmentId = apartmentId;
        this.resultPrice = resultPrice;
        this.orderStatusId = orderStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Double getResultPrice() {
        return resultPrice;
    }

    public void setResultPrice(Double resultPrice) {
        this.resultPrice = resultPrice;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Override
    public String toString() {
        return "ProcessedOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", apartmentId=" + apartmentId +
                ", resultPrice=" + resultPrice +
                ", orderStatusId=" + orderStatusId +
                '}';
    }
}
