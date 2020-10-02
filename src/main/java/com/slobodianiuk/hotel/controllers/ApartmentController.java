package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/apartments/*")
public class ApartmentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("details"));
            Optional<Apartment> apartmentOptional = ApartmentRepository.getApartmentById(id);
            if (apartmentOptional.isPresent()) {
                req.setAttribute("apartment", apartmentOptional.get());
                req.getRequestDispatcher("/apartment.jsp").forward(req, resp);
            } else {
                resp.setStatus(404);
            }
        } catch (NumberFormatException ex) {
            resp.setStatus(404);
        }
    }
}
