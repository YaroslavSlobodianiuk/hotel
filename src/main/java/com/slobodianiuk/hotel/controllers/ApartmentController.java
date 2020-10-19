package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.*;
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
 * Class that handling the displaying of
 * separate dynamic apartment page
 *
 * @author Yaroslav Slobodianiuk
 */

@WebServlet("/apartments/*")
public class ApartmentController extends HttpServlet {

    private static final long serialVersionUID = 8112143507193620200L;
    private static final Logger log = Logger.getLogger(ApartmentController.class);

    private final ApartmentRepository apartmentRepository;

    public ApartmentController() {
        this(ApartmentRepositorySingleton.getInstance());
    }

    public ApartmentController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String apartmentIdStr = req.getRequestURI();
        try {
            int apartmentId = Integer.parseInt(String.valueOf(apartmentIdStr.charAt(apartmentIdStr.length() -1)));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", apartmentId: " + apartmentId);
            Optional<Apartment> apartmentOptional;
            try {
                apartmentOptional = apartmentRepository.getApartmentById(apartmentId);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
                return;
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

    /**
     * Returns dynamic apartment page by id
     * If not apartment with specified id or
     * format of id does meet requirements
     * returns 404 error page
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            int apartmentId = Integer.parseInt(req.getParameter("apartmentId"));
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", apartmentId: " + apartmentId);
            Optional<Apartment> apartmentOptional;
            try {
                apartmentOptional = apartmentRepository.getApartmentById(apartmentId);
            } catch (DBException e) {
                session.setAttribute("errorMessage", e.getMessage());
                log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
                return;
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
