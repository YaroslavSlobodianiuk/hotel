package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.staticVar.Variables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRoleId() == 1) {
            List<UserOrderBean> orders = UserOrderRepository.getOrders();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("adminPanel.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        if ("new".equals(action)) {
            UserOrderRepository.updateStatusId(id, Variables.WAITING_FOR_APPROVE);
            resp.sendRedirect("/admin");
        }

        if ("approved".equals(action)) {
            UserOrderRepository.setTransactionStart(id);
            UserOrderRepository.updateStatusId(id, Variables.WAITING_FOR_PAYMENT);
            resp.sendRedirect("/admin");
        }
    }
}
