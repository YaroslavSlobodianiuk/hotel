package com.slobodianiuk.hotel.controllers;

import com.google.gson.Gson;
import com.slobodianiuk.hotel.db.entity.Category;
import com.slobodianiuk.hotel.db.entity.User;
import com.slobodianiuk.hotel.db.repo.ApartmentRepository;
import com.slobodianiuk.hotel.db.repo.CategoryRepository;
import com.slobodianiuk.hotel.exceptions.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CategoryRepository.class })
public class BookingControllerTest extends Mockito {

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
    public void doGetCategoryTest() throws DBException, ServletException, IOException {
        PowerMockito.mockStatic(CategoryRepository.class);
        User user = mock(User.class);
        List<Category> categories = mock(ArrayList.class);

        Gson gson = new Gson();
        Gson mock = mock(Gson.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("operation")).thenReturn("category");
//        PowerMockito.when(CategoryRepository.getCategories()).thenReturn(categories);
        when(mock.toJson(categories)).thenReturn("[{\"id\":1,\"categoryName\":\"econom\"},{\"id\":2,\"categoryName\":\"standard\"},{\"id\":3,\"categoryName\":\"luxe\"},{\"id\":4,\"categoryName\":\"deluxe\"}]");
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);

        new BookingController().doGet(request, response);

        verify(rd).forward(request, response);




    }
}
