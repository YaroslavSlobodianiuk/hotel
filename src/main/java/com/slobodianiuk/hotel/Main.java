package com.slobodianiuk.hotel;


import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
        dateTime = dateTime.plusYears(1);
        String tenYearsAfterString = dateTime.format(formatter);
        System.out.println(tenYearsAfterString);


    }
}
