package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;
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

@RunWith(MockitoJUnitRunner.class)
public class LanguageControllerTest extends Mockito {

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
    LanguageController languageController;

    @Test
    public void doGetChangeUserLanguageTest() throws DBException, IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("locale")).thenReturn("en");
        when(request.getParameter("locale")).thenReturn("ru");
        when(user.getLocaleName()).thenReturn("en");
        when(userRepository.updateUser(user)).thenReturn(true);

        languageController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/");
    }

    @Test
    public void doGetChangeUserLanguageWithRepositoryTest() throws DBException, IOException, ServletException {
        languageController = spy(LanguageController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("locale")).thenReturn("en");
        when(request.getParameter("locale")).thenReturn("ru");
        when(user.getLocaleName()).thenReturn("en");

        languageController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/");
    }

    @Test
    public void doGetChangeLanguageForUnregisteredUserTest() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(session.getAttribute("locale")).thenReturn("en");
        when(request.getParameter("locale")).thenReturn("ru");

        languageController.doGet(request, response);

        verify(response, times(3)).sendRedirect("/");
    }

    @Test
    public void shouldThrowDBExceptionAndForwardToErrorPageTest() throws DBException, IOException, ServletException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("locale")).thenReturn("en");
        when(request.getParameter("locale")).thenReturn("ru");
        when(user.getLocaleName()).thenReturn("en");
        when(userRepository.updateUser(user)).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        languageController.doGet(request, response);

        verify(rd).forward(request, response);
    }
}
