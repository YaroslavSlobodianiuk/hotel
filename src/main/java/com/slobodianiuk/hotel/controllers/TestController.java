package com.slobodianiuk.hotel.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet("/test")
public class TestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);
        req.setAttribute("date", calendar.getTime().getTime());
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }
}
