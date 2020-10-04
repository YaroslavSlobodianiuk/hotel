package com.slobodianiuk.hotel.db.entity;

public class Apartment {

    private int id;
    private String title;
    private int roomCapacity;
    private String category;
    private Double price;
    private String status;

    public Apartment() {
    }

    public Apartment(int id, String title, int roomCapacity, String category, Double price, String status) {
        this.id = id;
        this.title = title;
        this.roomCapacity = roomCapacity;
        this.category = category;
        this.price = price;
        this.status = status;
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

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", roomCapacity=" + roomCapacity +
                ", category=" + category +
                ", price=" + price +
                ", statusId=" + status +
                '}';
    }
}
