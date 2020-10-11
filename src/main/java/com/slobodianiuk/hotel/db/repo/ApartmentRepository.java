package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingOrder;
import com.slobodianiuk.hotel.db.enums.SortingType;
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
            preparedStatement = connection.prepareStatement("select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id inner join statuses on apartments.status_id = statuses.id where apartments.id =(?)");
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

    public static List<Apartment> getApartments(int offset, int numberOfRecords, SortingType sortingType, SortingOrder sortingOrder) {
        StringBuilder queryBuilder = new StringBuilder();
        List<Apartment> apartments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;

        queryBuilder.append("select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id inner join statuses on apartments.status_id = statuses.id");
        if (sortingType != null) {
            queryBuilder.append(" order by ").append(sortingType.getValue());
        }
        if (!SortingOrder.ASC.equals(sortingOrder)) {
            queryBuilder.append(" ").append(sortingOrder.getValue());
        }
        queryBuilder.append(" limit ").append(offset).append(", ").append(numberOfRecords);
        System.out.println(queryBuilder.toString());
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            set = preparedStatement.executeQuery();
            while (set.next()) {
                apartments.add(extractApartments(set));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("size" + connectionPool.getSize());
            connectionPool.releaseConnection(connection);
            System.out.println("size" + connectionPool.getSize());
            close(preparedStatement, set);
        }
        return apartments;
    }

    public static int getNumberOfRecords() {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        int numberOfRecords = 0;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) from apartments");
            set = preparedStatement.executeQuery();
            if (set.next()) {
                numberOfRecords = set.getInt(1);
            }
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return numberOfRecords;
    }

    public static List<Apartment> getFreeApartmentsByCategoryAndCapacity(int categoryId, int capacityId) {
        List<Apartment> apartments = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select id, title from apartments where category_id = (?) and room_capacity_id = (?) and status_id = 1;");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, capacityId);
            set = preparedStatement.executeQuery();
            while (set.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(set.getInt("id"));
                apartment.setTitle(set.getString("title"));
                apartments.add(apartment);
            }
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
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
        apartments.setStatus(rs.getString("status_name"));
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
