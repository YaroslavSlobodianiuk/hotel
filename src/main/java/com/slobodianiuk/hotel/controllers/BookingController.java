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
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/booking")
public class BookingController extends HttpServlet {


    private static final long serialVersionUID = -6929357303989579846L;
    private static final Logger log = Logger.getLogger(BookingController.class);

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

        log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", operation: " + op);

        if ("category".equals(op)) {
            List<Category> categories = null;
            try {
                categories = CategoryRepository.getCategories();
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new java.util.Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
            Gson json = new Gson();
            String categoriesList = json.toJson(categories);
            resp.setContentType("text/html");
            log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", operation: " + op + ", categoriesList: " + categoriesList);
            resp.getWriter().write(categoriesList);
        }

        if ("capacity".equals(op)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            List<RoomCapacity> capacities = null;
            try {
                capacities = RoomCapacityRepository.getRoomCapacitiesByCategoryId(categoryId);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new java.util.Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
            Gson json = new Gson();
            String capacitiesList = json.toJson(capacities);
            resp.setContentType("text/html");
            log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", operation: " + op + ", categoryId: " + categoryId + ", capacitiesList: " + capacitiesList);
            resp.getWriter().write(capacitiesList);
        }

        if ("apartment".equals(op)) {

            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            int capacityId = Integer.parseInt(req.getParameter("capacityId"));

            List<Apartment> apartments = null;
            try {
                apartments = ApartmentRepository.getFreeApartmentsByCategoryAndCapacity(categoryId, capacityId);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new java.util.Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
            if (!apartments.isEmpty()) {
                Gson json = new Gson();
                String apartmentsList = json.toJson(apartments);
                resp.setContentType("text/html");
                log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", operation: " + op + ", categoryId: " + categoryId + ", capacityId: " + capacityId + ", apartmentsList: " + apartmentsList);
                resp.getWriter().write(apartmentsList);
            } else {
                log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", operation: " + op + ", categoryId: " + categoryId + ", capacityId: " + capacityId);

                resp.setContentType("text/html");
                resp.getWriter().write("");
            }

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

        int categoryId = 0;
        int capacityId = 0;
        int apartmentId = 0;

        List<Integer> numbers = selectValidation(req, resp);

        categoryId = numbers.get(0);
        capacityId = numbers.get(1);
        apartmentId = numbers.get(2);

        String comment = req.getParameter("comment");

        log.trace("time: "+ new java.util.Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() +
                ", from: " + from + ", to: " + to + ", apartmentId: " + apartmentId + ", categoryId: " + categoryId + ", capacityId: " + capacityId + ", comment: " + comment);


        boolean orderFlag  = false;
        try {
            orderFlag = UserOrderRepository.createOrder(user.getId(), apartmentId, categoryId, capacityId,
                    Date.valueOf(from), Date.valueOf(to), comment);
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new java.util.Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }

        if (orderFlag) {
            resp.sendRedirect("/me");
        } else {
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }
    }

    private List<Integer> selectValidation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Integer> numbers = new ArrayList<>();

        boolean forward = false;
        try {
            numbers.add(Integer.parseInt(req.getParameter("category")));
        } catch (NumberFormatException e) {
            req.setAttribute("categoryErrorMessage", "Please select category");
            forward = true;
        }

        try {
            numbers.add(Integer.parseInt(req.getParameter("capacity")));
        } catch (NumberFormatException e) {
            req.setAttribute("capacityErrorMessage", "Please select capacity");
            forward = true;
        }

        try {
            numbers.add(Integer.parseInt(req.getParameter("apartment")));
        } catch (NumberFormatException e) {
            req.setAttribute("appErrorMessage","Please select free apartment");
            forward = true;
        }
        if (forward) {
            req.getRequestDispatcher("booking.jsp").forward(req, resp);
        }
        return numbers;
    }


    private boolean dateValidation(LocalDate from, LocalDate to) {
        if (to.compareTo(from) > 0) {
            return true;
        }
        return false;
    }

}