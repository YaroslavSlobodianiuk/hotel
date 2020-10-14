package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Date;

@WebServlet("/language")
public class LanguageController extends HttpServlet {

    private static final long serialVersionUID = 2604665310889984564L;
    private static final Logger log = Logger.getLogger(LanguageController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean updateUser = false;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String localeFromSession = (String) session.getAttribute("locale");
        String localeFromRequest = req.getParameter("locale");

        if (user != null && !user.getLocaleName().equals(localeFromRequest)) {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() +
                    ", localeFromSession: " + localeFromSession + ", localeFromRequest: " + localeFromRequest);

            user.setLocaleName(localeFromRequest);
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeFromRequest);
            try {
                UserRepository.updateUser(user);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }

        } else if (!localeFromRequest.equals(localeFromSession)) {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() +
                    ", localeFromSession: " + localeFromSession + ", localeFromRequest: " + localeFromRequest);
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeFromRequest);
            session.setAttribute("locale", localeFromRequest);
        }

        resp.sendRedirect("/");
    }
}
