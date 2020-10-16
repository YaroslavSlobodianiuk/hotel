package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.sql.SQL;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaroslav Slobodianiuk
 */
public class CategoryRepository {

    private static final Logger log = Logger.getLogger(CategoryRepository.class);

    public CategoryRepository() {
    }

    /**
     * Returns apartment categories
     *
     * @return List<Category> categories
     * @throws DBException when db crashes, connection lost
     */
    public List<Category> getCategories() throws DBException {
        List<Category> categories = new ArrayList<>();
        ConnectionPool pool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        ResultSet set = null;
        PreparedStatement preparedStatement = null;

        log.trace("time: " + new java.util.Date() + "sql: " + SQL.SQL_GET_CATEGORIES);

        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL.SQL_GET_CATEGORIES);
            set = preparedStatement.executeQuery();
            Category category;
            while (set.next()) {
                category = new Category(set.getInt(1), set.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            log.error("time: " + new java.util.Date() + ", SQLException: ", e);
            throw new DBException("Unable to select data from DB");
        } finally {
            pool.releaseConnection(connection);
            close(preparedStatement, set);
        }
        return categories;
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
}
