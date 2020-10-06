package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

@WebServlet("/language")
public class LanguageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean updateUser = false;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String localeFromSession = (String) session.getAttribute("locale");
        String localeFromRequest = req.getParameter("locale");

        if (user != null && !user.getLocaleName().equals(localeFromRequest)) {
            user.setLocaleName(localeFromRequest);
            updateUser = true;
        }

        if (!localeFromRequest.equals(localeFromSession)) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeFromRequest);
            session.setAttribute("locale", localeFromRequest);
        }

        if (updateUser) {
            try {
                UserRepository.updateUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        resp.sendRedirect("/");
    }
}
