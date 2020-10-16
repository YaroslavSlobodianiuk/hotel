package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Logout class
 * @author Yaroslav Slobodianiuk
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private static final long serialVersionUID = -3234733105217010163L;
    private static final Logger log = Logger.getLogger(LogoutController.class);

    /**
     * Receives request from user to log out
     * If request comes from not registered user
     * redirects to main page
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null) {
            log("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " logged out");
            session.invalidate(); //removes all session attributes bound to the session
            resp.sendRedirect("/login");
        } else {
            resp.sendRedirect("/");
        }
    }
}
