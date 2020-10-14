package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import com.slobodianiuk.hotel.staticVar.Variables;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = -2329553678423385742L;
    private static final Logger log = Logger.getLogger(AdminController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " trying to receive /admin page");

        if (user != null && user.getRoleId() == 1) {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " got /admin page");
            List<UserOrderBean> orders = null;

            try {
                orders = UserOrderRepository.getOrders();
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            req.setAttribute("orders", orders);
            req.getRequestDispatcher("adminPanel.jsp").forward(req, resp);
        } else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " was redirected to /login page");
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String action = req.getParameter("action");
        int orderId = Integer.parseInt(req.getParameter("id"));

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", action: " + action + ", orderId: " + orderId);

        if ("new".equals(action)) {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId ==> " + Variables.WAITING_FOR_APPROVE);

            try {
                UserOrderRepository.updateStatusId(orderId, Variables.WAITING_FOR_APPROVE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId: " + Variables.WAITING_FOR_APPROVE);
            resp.sendRedirect("/admin");
        }

        if ("approved".equals(action)) {

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId ==> " + Variables.WAITING_FOR_PAYMENT);

            try {
                UserOrderRepository.setTransactionStart(orderId);
                UserOrderRepository.updateStatusId(orderId, Variables.WAITING_FOR_PAYMENT);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId: " + Variables.WAITING_FOR_PAYMENT);
            resp.sendRedirect("/admin");
        }

        if ("cancel".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.CANCELED);

            try {
                UserOrderRepository.updateStatusId(orderId, Variables.CANCELED);
                ApartmentRepository.updateApartmentStatus(apartmentId, Variables.FREE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.CANCELED);

            resp.sendRedirect("/admin");
        }
    }
}
