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

@WebServlet("/register")
public class RegistrationController extends HttpServlet {

    public RegistrationController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();


        Integer roleId = (Integer) session.getAttribute("role");
        User userAtt = (User) session.getAttribute("user");


        if (userAtt != null) {

            switch (RoleEnum.getRole(roleId)) {
                case User:
                    session.setAttribute("name", userAtt.getFirstName());
                    resp.sendRedirect("welcome.jsp");
                    break;
                case Admin:
                    session.setAttribute("name", userAtt.getFirstName());
                    resp.sendRedirect("admin.jsp");
                    break;
                default:
                    break;
            }
        } else {
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordConfirmation = req.getParameter("password_confirmation");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");

        String message;
        if (!"".equals(message = emptyValidation(login, password, passwordConfirmation, firstName, lastName))) {
            req.setAttribute("message", message);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            System.out.println(message);
        }

        if (!passwordValidation(password, passwordConfirmation)) {
            req.setAttribute("message", "Password does not match");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            System.out.println("Password does not match");
        }

        Optional<User> optionalUser = UserRepository.getUserByLogin(login);
        if (optionalUser.isPresent()) {
            req.setAttribute("message", "Login " + login + " is already exist");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            System.out.println("Login " + login + "is already exist");
        }
        String locale = session.getAttribute("locale") != null ? (String) session.getAttribute("locale") : "en";
        Optional<User> registeredUser = UserRepository.registerUser(login, password, firstName, lastName, locale);
        if (!registeredUser.isPresent()) {
            req.setAttribute("message", "Something happened while registration, please try again");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            System.out.println("Something happened while registration, please try again");
        }
        req.setAttribute("message", "User with " + login + " login was successfully registered");
        resp.sendRedirect("/login");
        System.out.println("User with " + login + " login was successfully registered");
    }

    private boolean passwordValidation(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    private String emptyValidation(String login, String password, String passwordConfirmation, String firstName, String lastName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (login.isEmpty()) {
            stringBuilder.append("Login field is empty").append(System.lineSeparator());
        }
        if (password.isEmpty()) {
            stringBuilder.append("Password field is empty").append(System.lineSeparator());
        }
        if (passwordConfirmation.isEmpty()) {
            stringBuilder.append("Confirm password field is empty").append(System.lineSeparator());
        }
        if (firstName.isEmpty()) {
            stringBuilder.append("First name field is empty").append(System.lineSeparator());
        }
        if (lastName.isEmpty()) {
            stringBuilder.append("Last name field is empty");
        }
        return stringBuilder.toString();
    }
}
