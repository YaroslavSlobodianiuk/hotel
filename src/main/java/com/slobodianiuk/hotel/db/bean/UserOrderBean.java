package com.slobodianiuk.hotel.db.bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Class responsible for keeping information
 * about orders
 *
 * @author Yaroslav Slobodianiuk
 */
public class UserOrderBean {

    private int id;
    private int userId;
    private String username;
    private int categoryId;
    private String category;
    private int capacityId;
    private int capacity;
    private int apartmentId;
    private String apartmentName;
    private Double price;
    private Date arrival;
    private Date departure;
    private Timestamp transactionStart;
    private int orderStatusId;
    private String orderStatus;
    private String comment;

    public UserOrderBean() {
    }


    public long getDeadline() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transactionStart);
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime().getTime();
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Timestamp getTransactionStart() {
        return transactionStart;
    }

    public void setTransactionStart(Timestamp transactionStart) {
        this.transactionStart = transactionStart;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(int capacityId) {
        this.capacityId = capacityId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
