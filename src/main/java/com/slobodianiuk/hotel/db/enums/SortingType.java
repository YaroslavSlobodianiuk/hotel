package com.slobodianiuk.hotel.db.enums;

public enum  SortingType {
    DEFAULT(""),
    PRICE("price"),
    CAPACITY("capacity"),
    CATEGORY("category"),
    STATUS("status");

    private String value;

    SortingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
