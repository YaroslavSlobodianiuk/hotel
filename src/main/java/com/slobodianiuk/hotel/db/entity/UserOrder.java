package com.slobodianiuk.hotel.db.entity;

import java.util.Date;

public class UserOrder {

    private int id;
    private int userId;
    private int roomCapacityId;
    private int categoryId;
    private Date arrival;
    private Date departure;
    private String comment;

    public UserOrder() {
    }

    public UserOrder(int id, int userId, int roomCapacityId, int categoryId, Date arrival, Date departure, String comment) {
        this.id = id;
        this.userId = userId;
        this.roomCapacityId = roomCapacityId;
        this.categoryId = categoryId;
        this.arrival = arrival;
        this.departure = departure;
        this.comment = comment;
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

    public int getRoomCapacityId() {
        return roomCapacityId;
    }

    public void setRoomCapacityId(int roomCapacityId) {
        this.roomCapacityId = roomCapacityId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomCapacityId=" + roomCapacityId +
                ", categoryId=" + categoryId +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", comment='" + comment + '\'' +
                '}';
    }
}
