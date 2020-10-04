package com.slobodianiuk.hotel;


import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingType;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.UserRepository;

import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {
        List<Apartment> apartments = ApartmentRepository.getApartments(0, 5, SortingType.DEFAULT);
        for (Apartment apartment : apartments) {
            System.out.println(apartment);
        }


    }
}
