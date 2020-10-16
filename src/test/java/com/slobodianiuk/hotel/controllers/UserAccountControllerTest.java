package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.entity.UserOrder;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class UserAccountControllerTest extends Mockito {

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
    List<UserOrderBean> orders;
    @Mock
    UserOrderRepository userOrderRepository;
    @Mock
    TransactionsRepository transactionsRepository;

    @InjectMocks
    UserAccountController userAccountController;

    @Test
    public void doGetPositiveTest() throws ServletException, IOException, DBException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(userOrderRepository.getOrdersByUserId(anyInt())).thenReturn(orders);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        userAccountController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetPositiveWithRepositoryTest() throws ServletException, IOException, DBException {
        userAccountController = spy(UserAccountController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        userAccountController.doGet(request, response);

        verify(rd).forward(request, response);
    }
}
























