package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepository {

    public static List<Apartment> getApartments() {
        List<Apartment> apartments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement("select * from apartments");
            set = preparedStatement.executeQuery();
            while (set.next()) {
                apartments.add(extractApartments(set));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return apartments;
    }

    private static Apartment extractApartments(ResultSet rs) throws SQLException {
        Apartment apartments = new Apartment();
        apartments.setId(rs.getInt("id"));
        apartments.setTitle(rs.getString("title"));
        apartments.setRoomCapacityId(rs.getInt("room_capacity_id"));
        apartments.setCategoryId(rs.getInt("category_id"));
        apartments.setPrice(rs.getDouble("price"));
        apartments.setStatusId(rs.getInt("status_id"));
        return apartments;
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
