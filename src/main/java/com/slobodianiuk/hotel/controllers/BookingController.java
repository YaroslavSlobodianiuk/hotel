package com.slobodianiuk.hotel.controllers;


import com.google.gson.Gson;
import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.CategoryRepository;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/booking")
public class BookingController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            req.getRequestDispatcher("notRegisteredUser.jsp").forward(req, resp);
        }

        String op = req.getParameter("operation");

        if (op == null) {
            req.getRequestDispatcher("booking.jsp").forward(req, resp);
        }

        if ("category".equals(op)) {
            List<Category> categories = CategoryRepository.getCategories();
            Gson json = new Gson();
            String categoriesList = json.toJson(categories);
            resp.setContentType("text/html");
            resp.getWriter().write(categoriesList);
        }

        if ("capacity".equals(op)) {
            int id = Integer.parseInt(req.getParameter("id"));
            List<RoomCapacity> capacities = RoomCapacityRepository.getRoomCapacitiesByCategoryId(id);
            Gson json = new Gson();
            String capacitiesList = json.toJson(capacities);
            resp.setContentType("text/html");
            resp.getWriter().write(capacitiesList);
        }

        if ("apartment".equals(op)) {
            System.out.println("start");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            int capacityId = Integer.parseInt(req.getParameter("capacityId"));
            System.out.println(categoryId + " - " + capacityId);
            List<Apartment> apartments = ApartmentRepository.getFreeApartmentsByCategoryAndCapacity(categoryId, capacityId);
            if (!apartments.isEmpty()) {
                Gson json = new Gson();
                String apartmentsList = json.toJson(apartments);
                resp.setContentType("text/html");
                resp.getWriter().write(apartmentsList);
            } else {
                resp.setContentType("text/html");
                resp.getWriter().write("");
            }

            System.out.println("end");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(req.getParameter("trip-start"), formatter);
        LocalDate to = LocalDate.parse(req.getParameter("trip-finish"), formatter);

        if (!dateValidation(from, to)) {
            req.setAttribute("dateErrorMessage", "First date should be earlier second one");
            req.getRequestDispatcher("booking.jsp").forward(req, resp);
        }
        int apartmentId = 0;
        int categoryId = 0;
        int capacityId = 0;

        try {
            categoryId = Integer.parseInt(req.getParameter("category"));
        } catch (NumberFormatException e) {
            req.setAttribute("categoryErrorMessage", "Please select category");
            req.getRequestDispatcher("booking.jsp").forward(req, resp);
        }

        try {
            capacityId = Integer.parseInt(req.getParameter("capacity"));
        } catch (NumberFormatException e) {
            req.setAttribute("capacityErrorMessage", "Please select capacity");
        }

        try {
            apartmentId = Integer.parseInt(req.getParameter("apartment"));
        } catch (NumberFormatException e) {
            req.setAttribute("appErrorMessage","Please select free apartment");
            req.getRequestDispatcher("booking.jsp").forward(req, resp);
        }
        String comment = req.getParameter("comment");

        boolean orderFlag  = UserOrderRepository.createOrder(user.getId(), apartmentId, categoryId, capacityId,
                Date.valueOf(from), Date.valueOf(to), comment);

        req.setAttribute("apartment", apartmentId);
        req.setAttribute("capacity", capacityId);
        req.setAttribute("category", categoryId);
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        req.setAttribute("comment", comment);

        if (orderFlag) {
            req.getRequestDispatcher("thankYouPage.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
    }

    private boolean dateValidation(LocalDate from, LocalDate to) {
        if (to.compareTo(from) > 0) {
            return true;
        }
        return false;
    }

}