package com.slobodianiuk.hotel.db.pool;

import com.slobodianiuk.hotel.properties.DatabaseProperties;

import java.sql.SQLException;

public class ConnectionPoolManager {

    private static volatile ConnectionPool instance;

    private ConnectionPoolManager() {}

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPoolManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance  = localInstance = BasicConnectionPool.create("jdbc:mysql://localhost:3306/final_project_db", "root", "admin");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return localInstance;
    }
}
