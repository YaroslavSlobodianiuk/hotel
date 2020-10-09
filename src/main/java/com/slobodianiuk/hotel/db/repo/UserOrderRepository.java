package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.Date;

public class UserOrderRepository {

    public static boolean createOrder(int userId, int roomCapacityId, int categoryId, Date arrival, Date departure, String comment) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean created;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("insert into user_orders (user_id, room_capacity_id, category_id, arrival, departure, user_comment) values (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, roomCapacityId);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.setDate(4, (java.sql.Date) arrival);
            preparedStatement.setDate(5, (java.sql.Date) departure);
            preparedStatement.setString(6, comment);
            created = preparedStatement.execute();
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return created;
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
