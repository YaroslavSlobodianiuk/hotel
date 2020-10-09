package com.slobodianiuk.hotel.db.entity;

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

    @Override
    public String toString() {
        return "RoomCapacity{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
