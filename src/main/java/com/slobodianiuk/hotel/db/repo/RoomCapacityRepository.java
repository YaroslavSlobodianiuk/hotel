package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.sql.SQL;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomCapacityRepository {

    private static final Logger log = Logger.getLogger(RoomCapacityRepository.class);

    public static List<RoomCapacity> getRoomCapacities() throws DBException {
        List<RoomCapacity> capacities = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;

        log.trace("time: " + new java.util.Date() + "sql: " + SQL.SQL_GET_ROOM_CAPACITIES);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_GET_ROOM_CAPACITIES);
            set = preparedStatement.executeQuery();
            RoomCapacity roomCapacity;
            while (set.next()) {
                roomCapacity = new RoomCapacity(set.getInt(1), set.getInt(2));
                capacities.add(roomCapacity);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return capacities;
    }

    public static List<RoomCapacity> getRoomCapacitiesByCategoryId(int id) throws DBException {
        List<RoomCapacity> capacities = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;

        log.trace("time: " + new java.util.Date() + "sql: " + SQL.SQL_GET_ROOM_CAPACITIES_BY_CATEGORY_ID + ", categoryId: " + id);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_GET_ROOM_CAPACITIES_BY_CATEGORY_ID);
            preparedStatement.setInt(1, id);
            set = preparedStatement.executeQuery();

            while (set.next()) {
                RoomCapacity capacity = new RoomCapacity(set.getInt("id"), set.getInt("capacity"));
                capacities.add(capacity);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return capacities;
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


}
