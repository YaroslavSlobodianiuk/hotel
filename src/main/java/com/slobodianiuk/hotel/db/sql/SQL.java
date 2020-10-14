package com.slobodianiuk.hotel.db.sql;

public class SQL {

    // UserRepository
    public static final String SQL_GET_USER_BY_LOGIN = "select * from users where login = (?);";
    public static final String SQL_UPDATE_USER = "update users set locale_name = (?) where id = (?);";
    public static final String SQL_REGISTER_USER = "insert into users (login, password, first_name, last_name, role_id, locale_name) values (?, ?, ?, ?, ?, ?);";

    // UserOrderRepository
    public static final String SQL_GET_ORDERS = "select * from user_orders inner join order_statuses on user_orders.order_status_id = order_statuses.id inner join apartments on user_orders.apartment_id = apartments.id inner join categories on user_orders.category_id = categories.id inner join room_capacity on user_orders.room_capacity_id = room_capacity.id inner join users on user_orders.user_id = users.id;";
    public static final String SQL_CREATE_ORDER = "insert into user_orders (user_id, apartment_id, category_id, room_capacity_id, arrival, departure, user_comment) values (?, ?, ?, ?, ?, ?, ?);";
    public static final String SQL_GET_ORDERS_BY_USER_ID = "select * from user_orders inner join order_statuses on user_orders.order_status_id = order_statuses.id inner join apartments on user_orders.apartment_id = apartments.id inner join categories on user_orders.category_id = categories.id inner join room_capacity on user_orders.room_capacity_id = room_capacity.id inner join users on user_orders.user_id = users.id where users.id = (?);";
    public static final String SQL_UPDATE_STATUS_ID = "update user_orders set order_status_id = (?) where id = (?);";
    public static final String SQL_SET_TRANSACTION_START = "update user_orders set transaction_start = now() where id = (?);";

    // RoomCapacityRepository
    public static final String SQL_GET_ROOM_CAPACITIES_BY_CATEGORY_ID = "select distinct room_capacity.id, capacity from room_capacity inner join apartments on apartments.room_capacity_id = room_capacity.capacity inner join categories on apartments.category_id = categories.id where categories.id = ?;";
    public static final String SQL_GET_ROOM_CAPACITIES = "select * from room_capacity;";

    // CategoryRepository
    public static final String SQL_GET_CATEGORIES= "select * from categories;";

    // ApartmentRepository
    public static final String SQL_GET_APARTMENT_BY_ID = "select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id inner join statuses on apartments.status_id = statuses.id where apartments.id = (?);";
    public static final String SQL_GET_NUMBER_OF_RECORDS = "select count(*) from apartments;";
    public static final String SQL_GET_FREE_APARTMENTS_BY_CATEGORY_AND_CAPACITY = "select id, title from apartments where category_id = (?) and room_capacity_id = (?) and status_id = (?);";
    public static final String SQL_UPDATE_APARTMENT_STATUS = "update apartments set status_id = (?) where id = (?);";
    public static final String SQL_GET_APARTMENTS = "select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id inner join statuses on apartments.status_id = statuses.id";

}
