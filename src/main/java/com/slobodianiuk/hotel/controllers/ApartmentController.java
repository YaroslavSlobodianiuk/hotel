package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
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

@WebServlet("/apartments/*")
public class ApartmentController extends HttpServlet {

    private static final long serialVersionUID = 8112143507193620200L;
    private static final Logger log = Logger.getLogger(ApartmentController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", apartmentId: " + apartmentId);
            Optional<Apartment> apartmentOptional = null;
            try {
                apartmentOptional = ApartmentRepository.getApartmentById(apartmentId);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
            if (apartmentOptional.isPresent()) {
                req.setAttribute("apartment", apartmentOptional.get());
                req.getRequestDispatcher("/apartment.jsp").forward(req, resp);
            } else {
                log.trace("time: " + new Date() + ", sessionId: " + session.getId() + "==> 404");
                resp.setStatus(404);
            }
        } catch (NumberFormatException ex) {
            log.error("time: " + new Date() + ", error: " + ex);
            resp.setStatus(404);
        }
    }
}
