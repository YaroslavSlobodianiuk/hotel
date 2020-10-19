package com.slobodianiuk.hotel.controllers;

import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.CategoryRepository;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest extends Mockito {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    ApartmentRepository apartmentRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    RoomCapacityRepository roomCapacityRepository;
    @Mock
    UserOrderRepository userOrderRepository;
    @Mock
    List<Category> categories;
    @Mock
    User user;
    @Mock
    PrintWriter printWriter;


    @InjectMocks
    BookingController bookingController;

    @Test
    public void doGetCategoryTest() throws ServletException, IOException, DBException {
        bookingController = spy(BookingController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("category");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]"));
        //verify(response).getWriter().write("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]");

    }

    @Test
    public void doGetCapacityTest() throws IOException, ServletException {
        bookingController = spy(BookingController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("capacity");
        when(request.getParameter("id")).thenReturn(String.valueOf(3));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("[{\"id\":2,\"capacity\":2},{\"id\":3,\"capacity\":3}]"));
        //[{"id":2,"capacity":2},{"id":3,"capacity":3}]
    }

    @Test
    public void doGetApartmentTest() throws IOException, ServletException {
        bookingController = spy(BookingController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("apartment");
        when(request.getParameter("categoryId")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacityId")).thenReturn(String.valueOf(2));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("[{\"id\":4,\"title\":\"apartment4\"}]"));
    }

    @Test
    public void doGetEmptyApartmentTest() throws IOException, ServletException {
        bookingController = spy(BookingController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("apartment");
        when(request.getParameter("categoryId")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacityId")).thenReturn(String.valueOf(3));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertEquals("", stringWriter.toString());
    }

    @Test
    public void notRegisteredUserTest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void registeredUserNoOperationTest() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetCategoryShouldForwardToErrorPageTest() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("category");
        when(categoryRepository.getCategories()).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetCapacityShouldForwardToErrorPageTest() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("capacity");
        when(request.getParameter("id")).thenReturn(String.valueOf(3));
        when(roomCapacityRepository.getRoomCapacitiesByCategoryId(anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doGetApartmentShouldForwardToErrorPageTest() throws DBException, ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("apartment");
        when(request.getParameter("categoryId")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacityId")).thenReturn(String.valueOf(2));
        when(apartmentRepository.getFreeApartmentsByCategoryAndCapacity(anyInt(), anyInt())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doGet(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void doPostPositiveTest() throws ServletException, IOException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("category")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacity")).thenReturn(String.valueOf(2));
        when(request.getParameter("apartment")).thenReturn(String.valueOf(4));
        when(request.getParameter("comment")).thenReturn("comment");
        when(userOrderRepository.createOrder(anyInt(), anyInt(), anyInt(), anyInt(), any(Date.class), any(Date.class), anyString())).thenReturn(true);


        bookingController.doPost(request, response);

        verify(response).sendRedirect("/me");

    }

    @Test
    public void doPostPositiveWithRepositoryTest() throws ServletException, IOException, DBException {
        bookingController = spy(BookingController.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("category")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacity")).thenReturn(String.valueOf(2));
        when(request.getParameter("apartment")).thenReturn(String.valueOf(4));
        when(request.getParameter("comment")).thenReturn("comment");
        when(user.getId()).thenReturn(1);

        bookingController.doPost(request, response);

        verify(response).sendRedirect("/me");
    }

    @Test
    public void dateValidationTest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-29");
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);
    }

    @Test
    public void categoryIdValidationTest() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("category")).thenThrow(NumberFormatException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void capacityIdValidationTest() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("capacity")).thenThrow(NumberFormatException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void apartmentIdValidationTest() throws ServletException, IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("apartment")).thenThrow(NumberFormatException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void doPostNegativeTest() throws ServletException, IOException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("category")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacity")).thenReturn(String.valueOf(2));
        when(request.getParameter("apartment")).thenReturn(String.valueOf(4));
        when(request.getParameter("comment")).thenReturn("comment");
        when(userOrderRepository.createOrder(anyInt(), anyInt(), anyInt(), anyInt(), any(Date.class), any(Date.class), anyString())).thenReturn(false);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);

    }

    @Test
    public void doPostShouldThrowDBException() throws ServletException, IOException, DBException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("trip-start")).thenReturn("2020-10-30");
        when(request.getParameter("trip-finish")).thenReturn("2020-10-31");
        when(request.getParameter("category")).thenReturn(String.valueOf(3));
        when(request.getParameter("capacity")).thenReturn(String.valueOf(2));
        when(request.getParameter("apartment")).thenReturn(String.valueOf(4));
        when(request.getParameter("comment")).thenReturn("comment");
        when(userOrderRepository.createOrder(anyInt(), anyInt(), anyInt(), anyInt(), any(Date.class), any(Date.class), anyString())).thenThrow(DBException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        bookingController.doPost(request, response);

        verify(rd).forward(request, response);

    }































}
