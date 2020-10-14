package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.controllers.AdminController;
import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingOrder;
import com.slobodianiuk.hotel.db.enums.SortingType;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.staticVar.Variables;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentRepository {

    private static final Logger log = Logger.getLogger(ApartmentRepository.class);

    public static Optional<Apartment> getApartmentById(int id) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        Apartment apartment = null;

        log.trace("time: " + new java.util.Date() + ", sql: select * from apartments " +
                "inner join categories on apartments.category_id = categories.id " +
                "inner join room_capacity on apartments.room_capacity_id = room_capacity.id " +
                "inner join statuses on apartments.status_id = statuses.id where apartments.id =(?);" + ", id: " + id);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select * from apartments inner join categories on apartments.category_id = categories.id inner join room_capacity on apartments.room_capacity_id = room_capacity.id inner join statuses on apartments.status_id = statuses.id where apartments.id =(?);");
            preparedStatement.setInt(1, id);
            set = preparedStatement.executeQuery();

            if (set.next()) {
                apartment = extractApartments(set);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
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

        log.trace("time: " + new java.util.Date() + ", sql:" + queryBuilder.toString() + ", offset: " + offset + ", numberOfRecords: " + numberOfRecords + ", sortingType: " + sortingType + ", sortingOrder: " + sortingOrder);

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            set = preparedStatement.executeQuery();
            while (set.next()) {
                apartments.add(extractApartments(set));
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
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

        log.trace("time: " + new java.util.Date() + "sql: select count(*) from apartments");

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) from apartments");
            set = preparedStatement.executeQuery();
            if (set.next()) {
                numberOfRecords = set.getInt(1);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
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

        log.trace("time: " + new java.util.Date() + "sql: select id, title from apartments where category_id = (?) and room_capacity_id = (?) and status_id = (?);"
                + ", categoryId: " + categoryId + ", capacityId" + capacityId + "status_id: " + Variables.FREE);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select id, title from apartments where category_id = (?) and room_capacity_id = (?) and status_id = (?);");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, capacityId);
            preparedStatement.setInt(3, Variables.FREE);
            set = preparedStatement.executeQuery();
            while (set.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(set.getInt("id"));
                apartment.setTitle(set.getString("title"));
                apartments.add(apartment);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return apartments;
    }

    public static void updateApartmentStatus(int apartmentId, int statusId) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: update apartments set status_id = (?) where id = (?);" + ", apartmentId: " + apartmentId + ", statusId: " + statusId);


        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("update apartments set status_id = (?) where id = (?);");
            preparedStatement.setInt(1, statusId);
            preparedStatement.setInt(2, apartmentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
    }

    private static Apartment extractApartments(ResultSet rs) {
        Apartment apartments = new Apartment();
        try {
            apartments.setId(rs.getInt("id"));
            apartments.setTitle(rs.getString("title"));
            apartments.setRoomCapacity(rs.getInt("capacity"));
            apartments.setCategory(rs.getString("category_name"));
            apartments.setPrice(rs.getDouble("price"));
            apartments.setStatus(rs.getString("status_name"));
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        }

        return apartments;
    }

    private static void close(Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                log.error("time: " + new java.util.Date() + ", error: " + ex);
            }
        }
    }
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                log.error("time: " + new java.util.Date() + ", error: " + ex);
            }
        }
    }
}
