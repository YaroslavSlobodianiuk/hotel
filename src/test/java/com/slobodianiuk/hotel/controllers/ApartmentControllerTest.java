package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApartmentRepository.class })
public class ApartmentControllerTest extends Mockito {

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
    public void positiveDoPostTest() throws DBException, IOException, ServletException {
        PowerMockito.mockStatic(ApartmentRepository.class);
        Apartment apartment = mock(Apartment.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
//        PowerMockito.when(ApartmentRepository.getApartmentById(anyInt())).thenReturn(Optional.of(apartment));
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new ApartmentController().doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void shouldReturn404DoPostEmptyOptionalTest() throws DBException, ServletException, IOException {
        PowerMockito.mockStatic(ApartmentRepository.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
//        PowerMockito.when(ApartmentRepository.getApartmentById(anyInt())).thenReturn(Optional.empty());
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);


        new ApartmentController().doPost(request, response);

        verify(response, times(3)).setStatus(404);
    }

    @Test
    public void shouldReturn404DoPostNumberFormatExceptionTest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenThrow(NumberFormatException.class);

        new ApartmentController().doPost(request, response);

        verify(response, times(3)).setStatus(404);

    }

    @Test
    public void shouldForwardToErrorPageTest() throws DBException, ServletException, IOException {
        PowerMockito.mockStatic(ApartmentRepository.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
//        PowerMockito.when(ApartmentRepository.getApartmentById(anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new ApartmentController().doPost(request, response);

        verify(rd).forward(request, response);
    }
}
