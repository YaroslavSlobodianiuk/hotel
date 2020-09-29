package com.slobodianiuk.hotel.db.enums;

public enum CategoryEnum {

    Unknown(0), Econom(1), Standart(2), Luxe(3), Deluxe(4);

    private Integer categoryId;

    CategoryEnum(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public static CategoryEnum getRole(Integer categoryId) {
        for (CategoryEnum categoryEnum : values()) {
            if (categoryEnum.categoryId.equals(categoryId)) return categoryEnum;
        }
        return Unknown;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
