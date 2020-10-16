package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import com.slobodianiuk.hotel.staticVar.Variables;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Admin Controller Class
 *
 * @author Yaroslav Slobodianiuk
 */

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = -2329553678423385742L;
    private static final Logger log = Logger.getLogger(AdminController.class);

    public AdminController() {
    }

    /**
     * Returns Admin panel with all orders only
     * in case admin is trying to get this page
     * Otherwise returns login page
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " trying to receive /admin page");

        if (user != null && user.getRoleId() == 1) {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " got /admin page");
            List<UserOrderBean> orders = null;
            try {
                UserOrderRepository userOrderRepository = new UserOrderRepository();
                orders = userOrderRepository.getOrders();
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                RequestDispatcher rd = req.getRequestDispatcher("errorPage.jsp");
                rd.forward(req, resp);
                return;
            }
            req.setAttribute("orders", orders);
            RequestDispatcher rd = req.getRequestDispatcher("adminPanel.jsp");
            rd.forward(req, resp);
        } else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " was redirected to /login page");
            resp.sendRedirect("/login");
        }
    }

    /**
     * Receives requests from admin like:
     * send for approval, send for payment, cancel.
     *
     * @param req via req method is receiving action and orderId
     * @param resp via resp method refresh page to see changes after each action
     * @throws DBException when connection could not be established, DB crashes
     */
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
                UserOrderRepository userOrderRepository = getUserOrderRepository();
                userOrderRepository.updateStatusId(orderId, Variables.WAITING_FOR_APPROVE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                RequestDispatcher rd = req.getRequestDispatcher("errorPage.jsp");
                rd.forward(req, resp);
                return;
            }
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId: " + Variables.WAITING_FOR_APPROVE);
            resp.sendRedirect("/admin");
            return;
        }

        if ("approved".equals(action)) {

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId ==> " + Variables.WAITING_FOR_PAYMENT);

            try {
                UserOrderRepository userOrderRepository = new UserOrderRepository();
                userOrderRepository.setTransactionStart(orderId);
                userOrderRepository.updateStatusId(orderId, Variables.WAITING_FOR_PAYMENT);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
                return;
            }
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId:" + orderId + ", orderStatusId: " + Variables.WAITING_FOR_PAYMENT);
            resp.sendRedirect("/admin");
            return;
        }

        if ("cancel".equals(action)) {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId ==> " + Variables.CANCELED);

            try {
                TransactionsRepository transactionsRepository = new TransactionsRepository();
                transactionsRepository.updateOrderStatusIdAndApartmentStatus(orderId, Variables.CANCELED, apartmentId, Variables.FREE);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
                return;
            }

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + ", orderId: " + orderId + ", apartmentId: " + apartmentId + ", orderStatusId: " + Variables.CANCELED);

            resp.sendRedirect("/admin");
            return;
        }
    }

    /**
     * Returns repository(dao) class
     * @return UserOrderRepository
     */
    public UserOrderRepository getUserOrderRepository() {
        return new UserOrderRepository();
    }
}