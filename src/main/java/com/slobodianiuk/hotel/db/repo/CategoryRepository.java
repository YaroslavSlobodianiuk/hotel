package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.controllers.BookingController;
import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static final Logger log = Logger.getLogger(CategoryRepository.class);

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        ConnectionPool pool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        ResultSet set = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + "sql: select * from categories");

        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement("select * from categories;");
            set = preparedStatement.executeQuery();
            Category category;
            while (set.next()) {
                category = new Category(set.getInt(1), set.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", error: " + e);
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return categories;
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
}
