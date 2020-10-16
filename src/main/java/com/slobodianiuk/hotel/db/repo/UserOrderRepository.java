package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.sql.SQL;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author Yaroslav Slobodianiuk
 */
public class UserOrderRepository {

    private static final Logger log = Logger.getLogger(UserOrderRepository.class);

    protected UserOrderRepository() {}

    /**
     * Returns list of orders
     *
     * @return List<UserOrderBean> of orders
     * @throws DBException when db crashes, connection lost
     */
    public List<UserOrderBean> getOrders() throws DBException {
        List<UserOrderBean> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;

        log.trace("time: " + new java.util.Date() + "sql: " + SQL.SQL_GET_ORDERS);
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_GET_ORDERS);
            set = preparedStatement.executeQuery();
            Calendar calendar = Calendar.getInstance();
            while (set.next()) {
                Optional<UserOrderBean> order = extractOrder(set, calendar);
                order.ifPresent(orders::add);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return orders;
    }

    /**
     * Creates new order
     *
     * @param userId user id
     * @param apartmentId apartment id
     * @param categoryId category id
     * @param roomCapacityId room capacity id
     * @param arrival date to arrive
     * @param departure date to departure
     * @param comment comment
     * @return true if order successfully was added to DB
     * @throws DBException when db crashes, connection lost
     */
    public boolean createOrder(int userId, int apartmentId, int categoryId, int roomCapacityId, Date arrival, Date departure, String comment) throws DBException {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_CREATE_ORDER);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_CREATE_ORDER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, apartmentId);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.setInt(4, roomCapacityId);
            preparedStatement.setDate(5, new java.sql.Date(arrival.getTime()));
            preparedStatement.setDate(6, new java.sql.Date(departure.getTime()));
            preparedStatement.setString(7, comment);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to insert data to DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return true;
    }

    /**
     * Returns list of orders by user id
     *
     * @param id user id
     * @return List<UserOrderBean> orders by user id
     * @throws DBException when db crashes, connection lost
     */
    public List<UserOrderBean> getOrdersByUserId(int id) throws DBException {
        List<UserOrderBean> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        ResultSet set = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_GET_ORDERS_BY_USER_ID + ", userId: " + id);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_GET_ORDERS_BY_USER_ID);
            preparedStatement.setInt(1, id);
            set = preparedStatement.executeQuery();
            Calendar calendar = Calendar.getInstance();
            while (set.next()) {
                Optional<UserOrderBean> order = extractOrder(set, calendar);
                order.ifPresent(orders::add);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return orders;
    }

    private Optional<UserOrderBean> extractOrder(ResultSet rs, Calendar calendar) throws SQLException {
        UserOrderBean order = new UserOrderBean();

        order.setId(rs.getInt("user_orders.id"));
        order.setUserId(rs.getInt("user_id"));
        order.setUsername(rs.getString("first_name"));
        order.setCategoryId(rs.getInt("category_id"));
        order.setCategory(rs.getString("category_name"));
        order.setCapacityId(rs.getInt("room_capacity_id"));
        order.setCapacity(rs.getInt("capacity"));
        order.setApartmentId(rs.getInt("apartment_id"));
        order.setApartmentName(rs.getString("title"));
        order.setPrice(rs.getDouble("price"));
        order.setArrival(rs.getDate("arrival"));
        order.setDeparture(rs.getDate("departure"));
        order.setTransactionStart(rs.getTimestamp("transaction_start", calendar));
        order.setOrderStatusId(rs.getInt("order_status_id"));
        order.setOrderStatus(rs.getString("status_name"));
        order.setComment(rs.getString("user_comment"));

        return Optional.of(order);
    }

    /**
     * Updates order status id
     * @param id order id
     * @param statusId status id
     * @return true if update was successful, false otherwise
     * @throws DBException when db crashes, connection lost
     */
    public boolean updateStatusId(int id, int statusId) throws DBException {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_UPDATE_STATUS_ID +  ", id: " + id + ", statusId: " + statusId);
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_UPDATE_STATUS_ID);
            preparedStatement.setInt(1, statusId);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to update data in the DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return true;
    }

    /**
     * Sets start of transaction
     *
     * @param orderId
     * @throws DBException when db crashes, connection lost
     */
    public void setTransactionStart(int orderId) throws DBException {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_SET_TRANSACTION_START + "orderId: " + orderId);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_SET_TRANSACTION_START);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to update data in the DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
    }

    private void close(Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                log.error("time: " + new java.util.Date() + ", SQLException: " + ex);
            }
        }
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
