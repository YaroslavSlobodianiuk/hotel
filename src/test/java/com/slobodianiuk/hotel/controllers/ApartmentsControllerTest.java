package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingOrder;
import com.slobodianiuk.hotel.db.enums.SortingType;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentsControllerTest extends Mockito {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    List<Apartment> apartments;
    @Mock
    Apartment apartment;
    @Mock
    ApartmentRepository apartmentRepository;

    @InjectMocks
    ApartmentsController apartmentsController;

    @Test
    public void doGetDefaultASCSortingTest() throws DBException, ServletException, IOException {


        when(request.getSession()).thenReturn(session);
        when(request.getParameter("order")).thenReturn(null);
        when(request.getParameter("sort")).thenReturn(null);
        when(request.getParameter("page")).thenReturn(null);
        when(apartmentRepository.getApartments(anyInt(), anyInt(), Matchers.any(SortingType.class), Matchers.any(SortingOrder.class))).thenReturn(apartments);
        when(apartmentRepository.getNumberOfRecords()).thenReturn(10);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        apartmentsController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetStatusDESCSortingTest() throws DBException, ServletException, IOException {


        when(request.getSession()).thenReturn(session);
        when(request.getParameter("order")).thenReturn("desc");
        when(request.getParameter("sort")).thenReturn("status_name");
        when(apartmentRepository.getApartments(anyInt(), anyInt(), Matchers.any(SortingType.class), Matchers.any(SortingOrder.class))).thenReturn(apartments);
        when(apartmentRepository.getNumberOfRecords()).thenReturn(10);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        apartmentsController.doGet(request, response);

        verify(rd).forward(request, response);


    }
}
