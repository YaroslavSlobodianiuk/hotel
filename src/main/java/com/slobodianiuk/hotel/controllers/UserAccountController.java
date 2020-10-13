package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.staticVar.Variables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@WebServlet("/me")
public class UserAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //----------------------------------------
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);
        req.setAttribute("date", calendar.getTime().getTime());
        //----------------------------------------
        User user = (User) session.getAttribute("user");
        List<UserOrderBean> orders = UserOrderRepository.getOrdersByUserId(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        if ("waiting for approve".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            UserOrderRepository.updateStatusId(id, Variables.APPROVED);
            ApartmentRepository.updateApartmentStatus(apartmentId, Variables.RESERVED);
            resp.sendRedirect("/me");
        }

        if ("waiting for payment".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            UserOrderRepository.updateStatusId(id, Variables.PAID);
            ApartmentRepository.updateApartmentStatus(apartmentId, Variables.BOOKED);
            resp.sendRedirect("/me");
        }

        if ("declined".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            UserOrderRepository.updateStatusId(id, Variables.DECLINED);
            ApartmentRepository.updateApartmentStatus(apartmentId, Variables.FREE);
            resp.sendRedirect("/me");
        }

        if ("expired".equals(action)) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAA");
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            UserOrderRepository.updateStatusId(id, Variables.EXPIRED);
            ApartmentRepository.updateApartmentStatus(apartmentId, Variables.FREE);
            resp.sendRedirect("/");
        }

    }
}
