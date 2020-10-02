package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/apartments")
public class ApartmentsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Apartment> apartmentsAttribute = (List<Apartment>) session.getAttribute("apartments");

        if (apartmentsAttribute == null) {
            List<Apartment> apartments = ApartmentRepository.getApartments();
            session.setAttribute("apartments", apartments);
        }
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }
}
