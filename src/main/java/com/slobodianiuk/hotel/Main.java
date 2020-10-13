package com.slobodianiuk.hotel;


import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.entity.UserOrder;
import com.slobodianiuk.hotel.db.pool.BasicConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPool;
import com.slobodianiuk.hotel.db.pool.ConnectionPoolManager;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.staticVar.Variables;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Main {

    public static void main(String[] args) throws SQLException, ParseException {

//        DateTimeFormatter formatter
//                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//
//        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
//        dateTime = dateTime.plusYears(1);
//        String tenYearsAfterString = dateTime.format(formatter);
//        System.out.println(tenYearsAfterString);

//        List<UserOrderBean> orders = UserOrderRepository.getOrders();
//        for (UserOrderBean userOrderBean : orders) {
//            System.out.println(userOrderBean);
//        }

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime().getTime());
        calendar.add(Calendar.DATE, 2);
        System.out.println(calendar.getTime().getTime());

//        UserOrderRepository.setTransactionStart(1);
//
//        List<UserOrderBean> orders = UserOrderRepository.getOrdersByUserId(2);
//
//        UserOrderBean order = orders.get(0);
//
//        System.out.println(order.getTransactionStart());

//        Date nowUtc = order.getTransactionStart();
//        TimeZone aDefault = TimeZone.getTimeZone("Europe/Copenhagen");
//        System.out.println(nowUtc);
//        Calendar nowAsiaSingapore = Calendar.getInstance(aDefault);
//        nowAsiaSingapore.setTime(new Date());
//        System.out.println(nowAsiaSingapore.getTime());






    }
}
