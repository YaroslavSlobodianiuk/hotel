package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;
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

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1768147553023467819L;
    private static final Logger log = Logger.getLogger(LoginController.class);

    public LoginController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User userAtt = (User) session.getAttribute("user");

        if (userAtt != null) {

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + userAtt.getId() + ", userRoleId: " + userAtt.getRoleId() + " already logged in");

            switch (RoleEnum.getRole(userAtt.getRoleId())) {
                case Admin:
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    break;
                case User:
                    resp.sendRedirect(req.getContextPath() + "/");
                    break;
            }
        } else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " redirected to login page");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login);

        Optional<User> optionalUser = UserRepository.getUserByLogin(login);
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
                        break;
                    case User:
                        session.setAttribute("user", user);
                        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", user.getLocaleName());
                        session.setAttribute("locale", user.getLocaleName());
                        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + user.getId() + ", userRoleId: " + user.getRoleId() + " Successfully logged in");
                        resp.sendRedirect(req.getContextPath() + "/");
                        break;
                }
            } else {
                log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login + " Invalid login or password");
                req.setAttribute("message", "Invalid login or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }  else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login + " Invalid login or password");
            req.setAttribute("message", "Invalid login or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
