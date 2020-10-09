package com.slobodianiuk.hotel.controllers;


import com.google.gson.Gson;
import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.RoomCapacity;
import com.slobodianiuk.hotel.db.entity.User;
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
import java.util.List;

@WebServlet("/booking")
public class BookingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String op = req.getParameter("operation");

        if (op.equals("category")) {
            List<Category> categories = CategoryRepository.getCategories();
            Gson json = new Gson();
            String categoriesList = json.toJson(categories);
            resp.setContentType("text/html");
            resp.getWriter().write(categoriesList);
        }

        if (op.equals("capacity")) {
            int id = Integer.parseInt(req.getParameter("id"));
            List<RoomCapacity> capacities = RoomCapacityRepository.getRoomCapacitiesByCategoryId(id);
            Gson json = new Gson();
            String capacitiesList = json.toJson(capacities);
            resp.setContentType("text/html");
            resp.getWriter().write(capacitiesList);
        }


//        List<RoomCapacity> capacities = RoomCapacityRepository.getRoomCapacities();
//        List<Category> categories = CategoryRepository.getCategories();
//        req.setAttribute("capacities", capacities);
//        req.setAttribute("categories", categories);
//        req.getRequestDispatcher("booking.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String category = req.getParameter("category");
        String capacity = req.getParameter("capacity");

        System.out.println(capacity + " " + category);

//        User user = (User) session.getAttribute("user");
//        String capacity = req.getParameter("capacity");
//        String category = req.getParameter("category");
//        String from = req.getParameter("trip-start");
//        String to = req.getParameter("trip-finish");

        //Data validation

//        int capacityId =
//
//        boolean orderFlag  = UserOrderRepository.createOrder(user.getId(), );


        req.setAttribute("capacity", capacity);
        req.setAttribute("category", category);

//        req.setAttribute("message", message);
        req.getRequestDispatcher("book.jsp").forward(req, resp);
    }
}
