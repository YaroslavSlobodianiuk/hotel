package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomCapacityRepository {

    public static List<RoomCapacity> getRoomCapacities() {
        List<RoomCapacity> capacities = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("select * from room_capacity");
            set = preparedStatement.executeQuery();
            RoomCapacity roomCapacity;
            while (set.next()) {
                roomCapacity = new RoomCapacity(set.getInt(1), set.getInt(2));
                capacities.add(roomCapacity);
            }
        } catch (SQLException e) {
            connectionPool.releaseConnection(connection);
            e.printStackTrace();
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
                // Logger
            }
        }
    }
}
