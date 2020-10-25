package com.slobodianiuk.hotel.db.pool;

import java.sql.SQLException;

/**
 * Singleton class that helps to use only connection pool
 *
 * @author Yarosalv Slobodianiuk
 */
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
                        instance  = localInstance = BasicConnectionPool.create("jdbc:mysql://localhost:3306/final_project_db?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8", "root", "admin");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return localInstance;
    }
}
