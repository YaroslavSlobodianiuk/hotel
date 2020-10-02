package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentRepository {

    public static Optional<Apartment> getApartmentById(int id) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        Apartment apartment = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id where apartments.id =(?)");
            preparedStatement.setInt(1, id);
            set = preparedStatement.executeQuery();

            if (set.next()) {
                apartment = extractApartments(set);
            }
        } catch (SQLException e) {
            //Logger
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return Optional.of(apartment);
    }

    public static List<Apartment> getApartments() {
        List<Apartment> apartments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement("select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id order by apartments.id;");
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
        apartments.setRoomCapacity(rs.getInt("capacity"));
        apartments.setCategory(rs.getString("category_name"));
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
