package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.TransactionsRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import com.slobodianiuk.hotel.staticVar.Variables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
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

import static org.junit.Assert.assertEquals;

public class AdminControllerTest extends Mockito {

    @Spy
    UserOrderRepository userOrderRepository = new UserOrderRepository();

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
    public void doGetTest() throws IOException, ServletException, DBException {

        List<UserOrderBean> orders = mock(List.class);
        User user = mock(User.class);
        UserOrderRepository userOrderRepository = mock(UserOrderRepository.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(1);
        when(userOrderRepository.getOrders()).thenReturn(orders);

        new AdminController().doGet(request, response);

        List<UserOrderBean> actualOrders = userOrderRepository.getOrders();
        assertEquals(orders, actualOrders);
        verify(rd).forward(request, response);

    }

    @Test
    public void shouldForwardToErrorPage() throws DBException, ServletException, IOException {

        User user = mock(User.class);
        UserOrderRepository userOrderRepository = mock(UserOrderRepository.class);


        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(1);
        when(userOrderRepository.getOrders()).thenThrow(DBException.class);

        new AdminController().doGet(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void testRedirectToLoginPage() throws ServletException, IOException {

        User user = mock(User.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getRoleId()).thenReturn(2);

        new AdminController().doGet(request, response);

        verify(response, times(3)).sendRedirect("/login");
    }

    @Test
    public void testDoPostWithNewAsParameter() throws IOException, ServletException, DBException {

        User user = mock(User.class);
        UserOrderRepository userOrderRepository = mock(UserOrderRepository.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("new");
        when(request.getParameter("id")).thenReturn(String.valueOf(2));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenReturn(true);

        new AdminController().doPost(request, response);


        Assert.assertTrue(userOrderRepository.updateStatusId(1, Variables.WAITING_FOR_APPROVE));
        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndNewAsParam() throws DBException, IOException, ServletException {
        User user = mock(User.class);
        AdminController adminController = spy(AdminController.class);
        UserOrderRepository orderRepository = mock(UserOrderRepository.class);

//        UserOrderRepository orderRepository = new UserOrderRepository();
//        UserOrderRepository spiedOrderRep = Mockito.spy(orderRepository);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("new");
        when(request.getParameter("id")).thenReturn(String.valueOf(3));
        doReturn(orderRepository).when(adminController).getUserOrderRepository();
        doThrow(new DBException()).when(orderRepository).updateStatusId(anyInt(), anyInt());
        //when(adminController.getUserOrderRepository()).thenReturn(orderRepository);
        //when(orderRepository.updateStatusId(anyInt(), anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new AdminController().doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void testDoPostWithApprovedAsParameter() throws IOException, ServletException, DBException {

        User user = mock(User.class);
        UserOrderRepository userOrderRepository = mock(UserOrderRepository.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("approved");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenReturn(true);

        new AdminController().doPost(request, response);

        Assert.assertTrue(userOrderRepository.updateStatusId(1, Variables.WAITING_FOR_PAYMENT));
        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndApprovedAsParam() throws DBException, IOException, ServletException {
        User user = mock(User.class);
        UserOrderRepository userOrderRepository = mock(UserOrderRepository.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("approved");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(userOrderRepository.updateStatusId(anyInt(), anyInt())).thenThrow(DBException.class);

        new AdminController().doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void testDoPostWithCancelAsParameter() throws IOException, ServletException, DBException {

        User user = mock(User.class);
        TransactionsRepository transactionsRepository = mock(TransactionsRepository.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("cancel");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(transactionsRepository.updateOrderStatusIdAndApartmentStatus(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        new AdminController().doPost(request, response);


        Assert.assertEquals(true, transactionsRepository.updateOrderStatusIdAndApartmentStatus(anyInt(), anyInt(), anyInt(), anyInt()));
        verify(response, times(3)).sendRedirect("/admin");
    }

    @Test
    public void shouldForwardToErrorPageWithPostReqAndCancelAsParam() throws DBException, IOException, ServletException {
        User user = mock(User.class);
        TransactionsRepository transactionsRepository = mock(TransactionsRepository.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("cancel");
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(1));
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        PowerMockito.when(transactionsRepository.updateOrderStatusIdAndApartmentStatus(anyInt(), anyInt(), anyInt(), anyInt())).thenThrow(DBException.class);

        new AdminController().doPost(request, response);

        verify(rd).forward(request, response);
    }

}
