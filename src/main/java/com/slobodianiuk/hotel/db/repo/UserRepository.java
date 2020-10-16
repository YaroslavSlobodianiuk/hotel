package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.sql.SQL;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Optional;

/**
 * @author Yaroslav Slobodianiuk
 */
public class UserRepository {

    private static final Logger log = Logger.getLogger(UserRepository.class);

    public UserRepository() {}

    /**
     * Registers user
     * @param login user login
     * @param password password
     * @param firstName user first name
     * @param lastName user last name
     * @param locale language
     * @return Optional<User> if success, otherwise throws exception
     * @throws DBException when db crashes, connection lost
     */
    public Optional<User> registerUser(String login, String password, String firstName, String lastName, String locale) throws DBException {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        User user;
        ResultSet set;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_REGISTER_USER +
                ", login: " + login + ", firstName: " + firstName + ", lastName: " + lastName + ", locale: " + locale);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_REGISTER_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, RoleEnum.User.getRoleId());
            preparedStatement.setString(6, locale);
            preparedStatement.execute();

            set = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (set.next()) {
                id = set.getInt(1);
            }
            user = new User(id, login, password, firstName, lastName, RoleEnum.User.getRoleId(), locale);

        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to insert data to DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return Optional.of(user);
    }

    /**
     * Returns user by login
     * @param login login
     * @return Optional<User> if user exists, Optional.empty() if does not
     * @throws DBException when db crashes, connection lost
     */
    public Optional<User> getUserByLogin(String login) throws DBException {
        Optional<User> user = Optional.empty();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_GET_USER_BY_LOGIN + ", login: " + login);

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SQL.SQL_GET_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            set = preparedStatement.executeQuery();
            if (set.next()) {
                user = extractUser(set);
            }
        } catch(SQLException e){
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return user;
    }

    /**
     * Updates user information
     *
     * @param user
     * @return true if update was done successfully, throws exception in other case
     * @throws DBException when db crashes, connection lost
     */
    public boolean updateUser(User user) throws DBException {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: " + SQL.SQL_UPDATE_USER + ", userId: " + user.getId() + ", localeName: " + user.getLocaleName());

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_UPDATE_USER);
            preparedStatement.setString(1, user.getLocaleName());
            preparedStatement.setInt(2, user.getId());
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

    private Optional<User> extractUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setRoleId(rs.getInt("role_id"));
        user.setLocaleName(rs.getString("locale_name"));

        return Optional.of(user);
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