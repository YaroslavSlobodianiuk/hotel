package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import com.slobodianiuk.hotel.staticVar.Variables;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/me")
public class UserAccountController extends HttpServlet {

    private static final long serialVersionUID = 5119304756105727785L;
    private static final Logger log = Logger.getLogger(UserAccountController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<UserOrderBean> orders = null;
        try {
            orders = UserOrderRepository.getOrdersByUserId(user.getId());
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }
        req.setAttribute("orders", orders);
        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId()  + " Account page");
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", action: " + action + ", orderId: " + orderId);

        if ("waiting for approve".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.APPROVED);

            try {
                TransactionsRepository.updateOrderStatusIdAndApartmentStatus(orderId, Variables.APPROVED, apartmentId, Variables.RESERVED);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.APPROVED);
            resp.sendRedirect("/me");
        }

        if ("waiting for payment".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.PAID);

            try {
                TransactionsRepository.updateOrderStatusIdAndApartmentStatus(orderId, Variables.PAID, apartmentId, Variables.BOOKED);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.PAID);

            resp.sendRedirect("/me");
        }

        if ("declined".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.DECLINED);

            try {
                TransactionsRepository.updateOrderStatusIdAndApartmentStatus(orderId, Variables.DECLINED, apartmentId, Variables.FREE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }


            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.DECLINED);
            resp.sendRedirect("/me");
        }

        if ("expired".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.EXPIRED);

            try {
                TransactionsRepository.updateOrderStatusIdAndApartmentStatus(orderId, Variables.EXPIRED, apartmentId, Variables.FREE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.EXPIRED);
        }

    }
}
