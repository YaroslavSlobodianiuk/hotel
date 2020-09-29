package com.slobodianiuk.hotel.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseProperties {

    public final String connectionUrl;
    public final String username;
    public final String password;

    public DatabaseProperties() {
        System.out.println("init");
        Properties properties = new Properties();
        FileInputStream in;

        try {
            in = new FileInputStream("src/main/java/database.properties");
            properties.load(in);
            in.close();
        } catch (IOException e) {
            Logger.getAnonymousLogger().severe(e.getMessage());
        }

        connectionUrl = properties.getProperty("connection.url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }
}
