package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    private UserRepository() {}

    public static Optional<User> registerUser(String login, String password, String firstName, String lastName) {
        ConnectionPool connectionPool = ConnectionPoolManager.getInstance();
        PreparedStatement preparedStatement = null;
        User user = null;
        ResultSet set;
        try {
            preparedStatement = connectionPool.getConnection().prepareStatement("insert into users (login, password, first_name, last_name, role_id) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, RoleEnum.User.getRoleId());
            preparedStatement.execute();

            set = preparedStatement.getGeneratedKeys();
            int id = 0;
            if (set.next()) {
                id = set.getInt(1);
            }
            user = new User(id, login, password, firstName, lastName, RoleEnum.User.getRoleId());

        } catch (SQLException e) {
            //Logger
            return Optional.empty();
        } finally {
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
        try {
            connection = connectionPool.getConnection();
            // ask about sql queries
            preparedStatement = connection.prepareStatement("select * from users where login = (?)");
            preparedStatement.setString(1, login);

            set = preparedStatement.executeQuery();
            if (set.next()) {
                user = extractUser(set);
            }
        } catch(SQLException e){
            //Logger
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return user;
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
        } catch (SQLException ex) {
            // Logger
        }
        return Optional.of(user);
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
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // Logger
            }
        }
    }

}