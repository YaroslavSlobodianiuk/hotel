package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserOrderRepository {

    public static List<UserOrderBean> getOrders() {
        List<UserOrderBean> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select * from user_orders\n" +
                    "inner join order_statuses\n" +
                    "on user_orders.order_status_id = order_statuses.id\n" +
                    "inner join apartments\n" +
                    "on user_orders.apartment_id = apartments.id\n" +
                    "inner join categories\n" +
                    "on user_orders.category_id = categories.id\n" +
                    "inner join room_capacity\n" +
                    "on user_orders.room_capacity_id = room_capacity.id\n" +
                    "inner join users\n" +
                    "on user_orders.user_id = users.id;");
            set = preparedStatement.executeQuery();
            while (set.next()) {
                Optional<UserOrderBean> order = extractOrder(set);
                order.ifPresent(orders::add);
            }
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return orders;
    }

    public static boolean createOrder(int userId, int apartmentId, int categoryId, int roomCapacityId, Date arrival, Date departure, String comment) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("insert into user_orders (user_id, apartment_id, category_id, room_capacity_id, arrival, departure, user_comment) values (?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, apartmentId);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.setInt(4, roomCapacityId);
            preparedStatement.setDate(5, arrival);
            preparedStatement.setDate(6, departure);
            preparedStatement.setString(7, comment);
            preparedStatement.execute();
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return true;
    }

    private static Optional<UserOrderBean> extractOrder(ResultSet rs) {
        UserOrderBean order = new UserOrderBean();
        try {
            order.setId(rs.getInt("user_orders.id"));
            order.setUserId(rs.getInt("user_id"));
            order.setUsername(rs.getString("first_name"));
            order.setCategoryId(rs.getInt("category_id"));
            order.setCategory(rs.getString("category_name"));
            order.setCapacityId(rs.getInt("room_capacity_id"));
            order.setCapacity(rs.getInt("capacity"));
            order.setApartmentId(rs.getInt("apartment_id"));
            order.setApartmentName(rs.getString("title"));
            order.setArrival(rs.getDate("arrival"));
            order.setDeparture(rs.getDate("departure"));
            order.setOrderStatus(rs.getString("status_name"));
            order.setComment(rs.getString("user_comment"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Logger
        }
        return Optional.of(order);
    }

    private static void close(Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                // Logger
            }
        }
    }
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // Logger
            }
        }
    }
}
