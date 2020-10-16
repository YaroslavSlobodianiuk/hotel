package com.slobodianiuk.hotel.db.enums;

/**
 * @author Yarosalv Slobodianiuk
 */
public enum RoleEnum {

    Unknown(0), Admin(1), User(2);

    private Integer roleId;

    RoleEnum(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * Returns RoleEnum according role id parameter
     * @param roleId RoleEnum id
     * @return RoleEnum
     */
    public static RoleEnum getRole(Integer roleId) {
        for (RoleEnum roleEnum : values()) {
            if (roleEnum.roleId.equals(roleId)) return roleEnum;
        }
        return Unknown;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
