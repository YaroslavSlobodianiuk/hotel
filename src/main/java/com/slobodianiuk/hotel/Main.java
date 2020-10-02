package com.slobodianiuk.hotel;


import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.println(ApartmentRepository.getApartmentById(1));

    }
}
