package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
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
import java.util.Optional;

/**
 * Login page class
 *
 * @author Yaroslav Slobodianiuk
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1768147553023467819L;
    private static final Logger log = Logger.getLogger(LoginController.class);

    private final UserRepository userRepository;

    public LoginController() {
        this(UserRepositorySingleton.getInstance());
    }

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns login page
     * In case user or admin already logged
     * in returns main page according to user role
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User userAtt = (User) session.getAttribute("user");

        if (userAtt != null) {

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + userAtt.getId() + ", userRoleId: " + userAtt.getRoleId() + " already logged in");

            switch (RoleEnum.getRole(userAtt.getRoleId())) {
                case Admin:
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    return;
                case User:
                    resp.sendRedirect(req.getContextPath() + "/");
                    return;
            }
        } else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " redirected to login page");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
    }

    /**
     * Checks if login exists in DB, if password equals to specified
     * If OK redirects to main page with access to private account
     * If NO returns message about invalid login or password
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login);

        Optional<User> optionalUser = null;
        try {
            optionalUser = userRepository.getUserByLogin(login);
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            return;
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                switch (RoleEnum.getRole(user.getRoleId())) {
                    case Admin:
                        session.setAttribute("user", user);
                        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", user.getLocaleName());
                        session.setAttribute("locale", user.getLocaleName());
                        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " Successfully logged in");
                        resp.sendRedirect(req.getContextPath() + "/admin");
                        return;
                    case User:
                        session.setAttribute("user", user);
                        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", user.getLocaleName());
                        session.setAttribute("locale", user.getLocaleName());
                        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " Successfully logged in");
                        resp.sendRedirect(req.getContextPath() + "/");
                        return;
                }
            } else {
                log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login + " Invalid login or password");
                req.setAttribute("message", "Invalid login or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
        }  else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login + " Invalid login or password");
            req.setAttribute("message", "Invalid login or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
    }
}
