package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingOrder;
import com.slobodianiuk.hotel.db.enums.SortingType;
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
import java.util.List;


/**
 * Class that responsible for displaying all apartments
 *
 * @author Yaroslav Slobodianiuk
 */
@WebServlet("/apartments")
public class ApartmentsController extends HttpServlet {

    private static final long serialVersionUID = 1721189415750601025L;
    private static final Logger log = Logger.getLogger(ApartmentsController.class);

    /**
     * Returns page with all apartments
     * Support sorting and pagination
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        SortingOrder sortingOrder = SortingOrder.ASC;
        if (req.getParameter("order") != null) {
            String order = req.getParameter("order");
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", order:" + order);
            if (SortingOrder.DESC.getValue().equals(order)) {
                sortingOrder = SortingOrder.DESC;
            }
        }

        SortingType sortingType = SortingType.DEFAULT;
        if (req.getParameter("sort") != null) {
            String sort = req.getParameter("sort").toLowerCase();
            log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", sort:" + sort);
            if (SortingType.PRICE.getValue().equals(sort)) {
                sortingType = SortingType.PRICE;
            } else if (SortingType.CAPACITY.getValue().equals(sort)) {
                sortingType = SortingType.CAPACITY;
            } else if (SortingType.CATEGORY_NAME.getValue().equals(sort)) {
                sortingType = SortingType.CATEGORY_NAME;
            } else if (SortingType.STATUS_NAME.getValue().equals(sort)) {
                sortingType = SortingType.STATUS_NAME;
            }
        }

        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", page:" + page +
                ", recordsPerPage: " + recordsPerPage + ", offset: " + (page-1)*recordsPerPage + ", sortingType: " + sortingType + ", sortingOrder: " + sortingOrder);

        List<Apartment> apartments = null;
        int numberOfRecords = 0;
        try {
            ApartmentRepository apartmentRepository = new ApartmentRepository();
            apartments = apartmentRepository.getApartments((page-1)*recordsPerPage, recordsPerPage, sortingType, sortingOrder);
            numberOfRecords = apartmentRepository.getNumberOfRecords();
        } catch (DBException e) {
            session.setAttribute("errorMessage", e.getMessage());
            log.error("time: " + new Date() + ", sessionId: " + session.getId() + ", errorMessage: " + e.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }

        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);

        log.trace("time: "+ new Date() + ", sessionId: " + session.getId() + ", numberOfRecords: " + numberOfRecords + ", numberOfPages: " + numberOfPages);

        req.setAttribute("apartments", apartments);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("sortingType", sortingType.toString().toLowerCase());
        req.setAttribute("sortingOrder", sortingOrder.toString().toLowerCase());

        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }
}
