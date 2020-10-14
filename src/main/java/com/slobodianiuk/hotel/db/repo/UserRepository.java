package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    private static final Logger log = Logger.getLogger(UserRepository.class);

    private UserRepository() {}

    public static Optional<User> registerUser(String login, String password, String firstName, String lastName, String locale) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        User user;
        ResultSet set;

        log.trace("time: " + new java.util.Date() + ", sql: insert into users (login, password, first_name, last_name, role_id, locale_name) values (?, ?, ?, ?, ?, ?);" +
                ", login: " + login + ", firstName: " + firstName + ", " + lastName + ", locale" + locale);

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("insert into users (login, password, first_name, last_name, role_id, locale_name) values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
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
            log.error("time: " + new java.util.Date() + ", error: " + e);
            return Optional.empty();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return Optional.of(user);
    }

    public static Optional<User> getUserByLogin(String login) {
        Optional<User> user = Optional.empty();
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;

        log.trace("time: " + new java.util.Date() + ", sql: select * from users where login = (?);" + ", login: " + login);

        try {
            connection = connectionPool.getConnection();
            // ask about sql queries
            preparedStatement = connection.prepareStatement("select * from users where login = (?);");
            preparedStatement.setString(1, login);

            set = preparedStatement.executeQuery();
            if (set.next()) {
                user = extractUser(set);
            }
        } catch(SQLException e){
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return user;
    }

    public static boolean updateUser(User user) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + ", sql: update users set locale_name = (?) where id = (?);" + ", userId: " + user.getId() + ", localeName: " + user.getLocaleName());

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("update users set locale_name = (?) where id = (?);");
            preparedStatement.setString(1, user.getLocaleName());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement);
        }
        return true;
    }

    private static Optional<User> extractUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRoleId(rs.getInt("role_id"));
            user.setLocaleName(rs.getString("locale_name"));
        } catch (SQLException ex) {
            log.error("time: " + new java.util.Date() + ", error: " + ex);
        }
        return Optional.of(user);
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