package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    public void doGetChangeUserLanguageTest() {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("locale")).thenReturn("en");

    }
}
