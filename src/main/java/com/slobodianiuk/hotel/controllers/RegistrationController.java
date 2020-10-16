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
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Registration class
 *
 * @author Yaroslav Slobodianiuk
 */
@WebServlet("/register")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 2308152438561227727L;
    private static final Logger log = Logger.getLogger(RegistrationController.class);

    private final UserRepository userRepository;

    public RegistrationController() {
        this(UserRepositorySingleton.getInstance());
    }

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns registration page
     * In case user is logged in redirects
     * according to role
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Integer roleId = (Integer) session.getAttribute("role");
        User userAtt = (User) session.getAttribute("user");

        if (userAtt != null) {

            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", userId: " + userAtt.getId() + ", userRoleId: " + userAtt.getRoleId() + " already logged in");

            switch (RoleEnum.getRole(roleId)) {
                case User:
                    session.setAttribute("name", userAtt.getFirstName());
                    resp.sendRedirect("/");
                    return;
                case Admin:
                    session.setAttribute("name", userAtt.getFirstName());
                    resp.sendRedirect("/admin");
                    return;
            }
        } else {
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " was forwarded to registration page");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    /**
     * Registers user: validates all fields,
     * check if does not exist user with given login,
     * checks current locale, writes it with all provided
     * information to DB
     */
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
            return;
        }

        if (!passwordValidation(password, passwordConfirmation)) {
            req.setAttribute("message", "Password does not match");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        Optional<User> optionalUser;
        try {

            optionalUser = userRepository.getUserByLogin(login);
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            return;
        }
        if (optionalUser.isPresent()) {
            req.setAttribute("message", "Login " + login + " is already exist");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        String locale = session.getAttribute("locale") != null ? (String) session.getAttribute("locale") : "en";

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", login: " + login + ", firstName: " + firstName + " lastName: " + lastName + " registration in progress");

        Optional<User> registeredUser = null;
        try {
            registeredUser = userRepository.registerUser(login, password, firstName, lastName, locale);
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            return;
        }
        if (!registeredUser.isPresent()) {
            req.setAttribute("message", "Something happened while registration, please try again");
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " something happened while registration");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("message", "User with " + login + " login was successfully registered");
        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + " user with " + login + " was successfully registered");
        resp.sendRedirect("/login");
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
