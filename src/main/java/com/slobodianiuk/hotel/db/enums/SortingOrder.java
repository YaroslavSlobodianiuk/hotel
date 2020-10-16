package com.slobodianiuk.hotel.db.enums;

/**
 * Enum responsible for sorting order
 * @author Yarosalv Slobodianiuk
 */
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
