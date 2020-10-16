package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.db.repo.UserRepository;
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
public class LoginControllerTest extends Mockito {

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
    UserRepository userRepository;

    @InjectMocks
    LoginController loginController;

    @Test
    public void doGetAdminTest() throws IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(1);

        loginController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void doGetUserTest() throws IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(2);

        loginController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/");
    }

    @Test
    public void notRegisteredUserTest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        loginController.doGet(request, response);

        verify(rd).forward(request, response);

    }
}
