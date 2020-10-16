package com.slobodianiuk.hotel.controllers;

import com.google.gson.Gson;
import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.CategoryRepository;
import com.slobodianiuk.hotel.db.repo.RoomCapacityRepository;
import com.slobodianiuk.hotel.db.repo.UserOrderRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Before;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
    Gson gson;

    @Mock
    PrintWriter printWriter;

    @InjectMocks
    BookingController bookingController;

    @Test
    public void doGetCategoryTest() throws ServletException, IOException, DBException {


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("category");
        when(categoryRepository.getCategories()).thenReturn(categories);
        when(gson.toJson(categories)).thenReturn("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]"));
        //verify(response).getWriter().write("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]");

    }

    @Test
    public void doGetCapacityTest() throws IOException {

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("capacity");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }
}
