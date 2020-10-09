package com.slobodianiuk.hotel;


import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.pool.BasicConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

//        DateTimeFormatter formatter
//                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//
//        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
//        dateTime = dateTime.plusYears(1);
//        String tenYearsAfterString = dateTime.format(formatter);
//        System.out.println(tenYearsAfterString);

        List<RoomCapacity> capacities = RoomCapacityRepository.getRoomCapacitiesByCategoryId(3);
        for (RoomCapacity roomCapacity : capacities) {
            System.out.println(roomCapacity);
        }


    }
}
