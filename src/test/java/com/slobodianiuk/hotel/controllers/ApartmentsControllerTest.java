package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.enums.SortingOrder;
import com.slobodianiuk.hotel.db.enums.SortingType;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApartmentRepository.class })
public class ApartmentsControllerTest extends Mockito {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher rd;
    private HttpSession session;

    @Before
    public void setUp() {

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void doGetDefaultASCSortingTest() throws DBException, ServletException, IOException {
        PowerMockito.mockStatic(ApartmentRepository.class);
        List<Apartment> apartments = mock(List.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("order")).thenReturn(null);
        when(request.getParameter("sort")).thenReturn(null);
        when(request.getParameter("page")).thenReturn(null);
        //PowerMockito.when(ApartmentRepository.getApartments(anyInt(), anyInt(), Matchers.any(SortingType.class), Matchers.any(SortingOrder.class))).thenReturn(apartments);
        //PowerMockito.when(ApartmentRepository.getNumberOfRecords()).thenReturn(10);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new ApartmentsController().doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetStatusDESCSortingTest() throws DBException, ServletException, IOException {
        PowerMockito.mockStatic(ApartmentRepository.class);
        List<Apartment> apartments = mock(List.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("order")).thenReturn("desc");
        when(request.getParameter("sort")).thenReturn("status_name");
//        PowerMockito.when(ApartmentRepository.getApartments(anyInt(), anyInt(), Matchers.any(SortingType.class), Matchers.any(SortingOrder.class))).thenReturn(apartments);
//        PowerMockito.when(ApartmentRepository.getNumberOfRecords()).thenReturn(10);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new ApartmentsController().doGet(request, response);

        verify(rd).forward(request, response);


    }
}
