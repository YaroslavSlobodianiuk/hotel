package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.sql.SQL;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TransactionsRepository class for support transactions
 *
 * @author Yaroslav Slobodianiuk
 */
public class TransactionsRepository {

    private static final Logger log = Logger.getLogger(TransactionsRepository.class);

    public TransactionsRepository() {
    }

    /**
     * Method for transaction operations which updating
     * orderStatusId for particular order and updating
     * apartmentStatusId for certain apartment
     * @param orderId id that identificates order
     * @param orderStatusId id that identificates order status
     * @param apartmentId id that identificates apartment
     * @param apartmentStatusId id that identificates apartment status
     * @throws DBException if db crashed, connection was lost
     */
    public boolean updateOrderStatusIdAndApartmentStatus(int orderId, int orderStatusId, int apartmentId, int apartmentStatusId) throws DBException {

        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement updateStatusIdStatement = null;
        PreparedStatement updateApartmentStatusStatement = null;


        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            updateStatusIdStatement = connection.prepareStatement(SQL.SQL_UPDATE_STATUS_ID);
            updateStatusIdStatement.setInt(1, orderStatusId);
            updateStatusIdStatement.setInt(2, orderId);
            log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_UPDATE_STATUS_ID +  ", orderId: " + orderId + ", orderStatusId: " + orderStatusId);
            updateStatusIdStatement.execute();

            updateApartmentStatusStatement = connection.prepareStatement(SQL.SQL_UPDATE_APARTMENT_STATUS);
            updateApartmentStatusStatement.setInt(1, apartmentStatusId);
            updateApartmentStatusStatement.setInt(2, apartmentId);
            log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_UPDATE_APARTMENT_STATUS + ", apartmentId: " + apartmentId + ", statusId: " + apartmentStatusId);
            updateApartmentStatusStatement.execute();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("time: " + new java.util.Date() + ", SQLException rollback(): ", e);
            }
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to update data in the DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(updateStatusIdStatement);
            close(updateApartmentStatusStatement);
        }
        return true;
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                log.error("time: " + new java.util.Date() + ", SQLException: " + ex);
            }
        }
    }
}
