package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingType;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/apartments")
public class ApartmentsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("sort"));
        SortingType sortingType = SortingType.DEFAULT;
        if (req.getParameter("sort") != null) {
            String sort = req.getParameter("sort");
            if (SortingType.PRICE.getValue().equals(sort)) {
                System.out.println("a");
                sortingType = SortingType.PRICE;
            } else if (SortingType.CAPACITY.getValue().equals(sort)) {
                System.out.println("b");
                sortingType = SortingType.CAPACITY;
            }
        }

        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        List<Apartment> apartments = ApartmentRepository.getApartments((page-1)*recordsPerPage, recordsPerPage, sortingType);
        int numberOfRecords = ApartmentRepository.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("apartments", apartments);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("sortingType", sortingType);
        System.out.println("c");
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
        System.out.println("d");

//        if (apartmentsAttribute == null) {
//            List<Apartment> apartments = ApartmentRepository.getApartments();
//            session.setAttribute("apartments", apartments);
//        }
//        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }
}
