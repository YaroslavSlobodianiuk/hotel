package com.slobodianiuk.hotel.db.enums;

public enum SortingOrder {

    ASC("asc"), DESC("desc");

    private String value;

    SortingOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
