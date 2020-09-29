package com.slobodianiuk.hotel.db.entity;

public class Apartment {

    private int id;
    private String title;
    private int roomCapacityId;
    private int categoryId;
    private Double price;
    private int statusId;

    public Apartment() {
    }

    public Apartment(int id, String title, int roomCapacityId, int categoryId, Double price, int statusId) {
        this.id = id;
        this.title = title;
        this.roomCapacityId = roomCapacityId;
        this.categoryId = categoryId;
        this.price = price;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", roomCapacityId=" + roomCapacityId +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", statusId=" + statusId +
                '}';
    }
}
