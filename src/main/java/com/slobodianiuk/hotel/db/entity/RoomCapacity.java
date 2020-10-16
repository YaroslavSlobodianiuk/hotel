package com.slobodianiuk.hotel.db.entity;

/**
 * @author Yarosalv Slobodianiuk
 */
public class RoomCapacity {

    private int id;
    private int capacity;

    public RoomCapacity() {
    }

    public RoomCapacity(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
