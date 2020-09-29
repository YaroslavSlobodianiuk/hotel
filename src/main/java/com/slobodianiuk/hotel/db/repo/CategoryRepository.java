package com.slobodianiuk.hotel.db.repo;

import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.ResultSet;

public class CategoryRepository {

    public static String getCategoryNameByI() {
        ConnectionPool pool = ConnectionPoolManager.getInstance();
        Connection connection = null;
        ResultSet set = null;
        return "";
    }
}
