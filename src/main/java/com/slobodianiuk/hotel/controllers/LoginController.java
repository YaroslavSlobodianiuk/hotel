package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.enums.RoleEnum;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    public LoginController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User userAtt = (User) session.getAttribute("user");

        if (userAtt != null) {

            switch (RoleEnum.getRole(userAtt.getRoleId())) {
                case Admin:
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    break;
                case User:
                    resp.sendRedirect(req.getContextPath() + "/welcome");
                    break;
            }
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<User> optionalUser = UserRepository.getUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                switch (RoleEnum.getRole(user.getRoleId())) {
                    case Admin:
                        session.setAttribute("user", user);
                        resp.sendRedirect(req.getContextPath() + "/admin");
                        break;
                    case User:
                        session.setAttribute("user", user);
                        resp.sendRedirect(req.getContextPath() + "/welcome");
                        break;
                }
            }
        }  else {
            req.setAttribute("message", "Invalid login or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
