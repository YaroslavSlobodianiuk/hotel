package com.slobodianiuk.hotel;


import com.slobodianiuk.hotel.db.pool.BasicConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

//        DateTimeFormatter formatter
//                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//
//        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
//        dateTime = dateTime.plusYears(1);
//        String tenYearsAfterString = dateTime.format(formatter);
//        System.out.println(tenYearsAfterString);

        BasicConnectionPool basicConnectionPool = (BasicConnectionPool) ConnectionPoolManager.getInstance();

        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        for (int i = 0; i < 5; i++) {
            connection = connectionPool.getConnection();

            connectionPool.releaseConnection(connection);
            System.out.println(connectionPool.getUsedConnections().size());
        }

        ConnectionPool connectionPool1 = ConnectionPoolManager.getInstance();
        Connection connection1 = null;
        for (int i = 0; i < 5; i++) {
            connection = connectionPool1.getConnection();
            connectionPool1.releaseConnection(connection1);

            System.out.println(connectionPool1.getUsedConnections().size());
        }

        ConnectionPool connectionPool2 = ConnectionPoolManager.getInstance();
        Connection connection2 = null;
        for (int i = 0; i < 5; i++) {
            connection = connectionPool2.getConnection();
            connectionPool2.releaseConnection(connection1);

            System.out.println(connectionPool2.getUsedConnections().size());
        }




    }
}
