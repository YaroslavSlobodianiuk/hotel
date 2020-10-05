package com.slobodianiuk.hotel.controllers;


import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.repo.CategoryRepository;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/booking")
public class BookingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<RoomCapacity> capacities = RoomCapacityRepository.getRoomCapacities();
        List<Category> categories = CategoryRepository.getCategories();
        req.setAttribute("capacities", capacities);
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("booking.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String capacity = req.getParameter("capacity");
        String category = req.getParameter("category");
        String from = req.getParameter("trip-start");
        String to = req.getParameter("trip-finish");

        req.setAttribute("capacity", capacity);
        req.setAttribute("category", category);
        req.setAttribute("from", from);
        req.setAttribute("to", to);


//        req.setAttribute("message", message);
        req.getRequestDispatcher("book.jsp").forward(req, resp);
    }
}
