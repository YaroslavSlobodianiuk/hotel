package com.slobodianiuk.hotel.db.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Connection pool interface
 *
 * @author Yarosalv Slobodianiuk
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
    List<Connection> getConnectionPool();
    int getSize();
    String getUrl();
    String getUser();
    String getPassword();
    void shutdown() throws SQLException;
    List<Connection> getUsedConnections();
}