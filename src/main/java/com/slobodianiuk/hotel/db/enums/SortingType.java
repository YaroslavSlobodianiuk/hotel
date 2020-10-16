package com.slobodianiuk.hotel.db.enums;

/**
 * Enum responsible for sorting type
 *
 * @author Yarosalv Slobodianiuk
 */
public enum  SortingType {
    DEFAULT("apartments.id"),
    PRICE("price"),
    CAPACITY("capacity"),
    CATEGORY_NAME("category_name"),
    STATUS_NAME("status_name");

    private String value;

    SortingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
