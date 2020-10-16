package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import com.slobodianiuk.hotel.staticVar.Variables;


import org.junit.Assert;
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

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest extends Mockito {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    User user;
    @Mock
    UserOrderRepository userOrderRepository;
    @Mock
    TransactionsRepository transactionsRepository;

    @InjectMocks
    AdminController adminController;


    @Test
    public void doGetTest() throws IOException, ServletException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(1);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        adminController.doGet(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void shouldForwardToErrorPage() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(1);
        when(userOrderRepository.getOrders()).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        adminController.doGet(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void testRedirectToLoginPage() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(2);

        adminController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/login");
    }

    @Test
    public void testDoPostWithNewAsParameter() throws IOException, ServletException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("new");
        when(request.getParameter("id")).thenReturn(String.valueOf(2));

        //adminController.doPost(request, response);

        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndNewAsParam() throws DBException, IOException, ServletException {


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("new");
        when(request.getParameter("id")).thenReturn(String.valueOf(3));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        adminController.doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void testDoPostWithApprovedAsParameter() throws IOException, ServletException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("approved");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenReturn(true);

        adminController.doPost(request, response);

        Assert.assertTrue(userOrderRepository.updateStatusId(1, Variables.WAITING_FOR_PAYMENT));
        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndApprovedAsParam() throws DBException, IOException, ServletException {


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("approved");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        adminController.doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void testDoPostWithCancelAsParameter() throws IOException, ServletException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("cancel");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(transactionsRepository.updateOrderStatusIdAndApartmentStatus(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        adminController.doPost(request, response);

        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndCancelAsParam() throws DBException, IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("cancel");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(transactionsRepository.updateOrderStatusIdAndApartmentStatus(anyInt(), anyInt(), anyInt(), anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        adminController.doPost(request, response);

        verify(rd).forward(request, response);
    }

}
