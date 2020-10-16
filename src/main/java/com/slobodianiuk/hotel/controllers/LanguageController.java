package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.ApartmentRepositorySingleton;
import com.slobodianiuk.hotel.db.repo.UserRepository;
import com.slobodianiuk.hotel.db.repo.UserRepositorySingleton;
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

/**
 * Language page class
 *
 * @author Yaroslav Slobodianiuk
 */
@WebServlet("/language")
public class LanguageController extends HttpServlet {

    private static final long serialVersionUID = 2604665310889984564L;
    private static final Logger log = Logger.getLogger(LanguageController.class);

    private final UserRepository userRepository;

    public LanguageController() {
        this(UserRepositorySingleton.getInstance());
    }

    public LanguageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Receives GET request to change language on page
     * In case language from request differs from language from session it changes and
     * if it is session of registered user then changes language for this user in DB
     * In case language from request the same as language from session does nothing
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

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
                userRepository.updateUser(user);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
                return;
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
