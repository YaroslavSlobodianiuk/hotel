package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Apartment;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentControllerTest extends Mockito {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    Apartment apartment;
    @Mock
    ApartmentRepository apartmentRepository;

    @InjectMocks
    ApartmentController apartmentController;

    @Test
    public void positiveDoPostTest() throws DBException, IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(apartmentRepository.getApartmentById(anyInt())).thenReturn(Optional.of(apartment));
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        apartmentController.doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void shouldReturn404DoPostEmptyOptionalTest() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(apartmentRepository.getApartmentById(anyInt())).thenReturn(Optional.empty());

        apartmentController.doPost(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void shouldReturn404DoPostNumberFormatExceptionTest() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenThrow(NumberFormatException.class);

        apartmentController.doPost(request, response);

        verify(response).setStatus(404);

    }

    @Test
    public void shouldForwardToErrorPageTest() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(apartmentRepository.getApartmentById(anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        apartmentController.doPost(request, response);

        verify(rd).forward(request, response);
    }
}
