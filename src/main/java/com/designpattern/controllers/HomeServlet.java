package com.designpattern.controllers;

import com.designpattern.dao.RestaurantDAO;
import com.designpattern.dao.CategoryDAO;
import com.designpattern.dao.EventsDAO;

import com.designpattern.DAoImpl.RestaurantDAOImpl;
import com.designpattern.DAoImpl.CategoryDAOImpl;
import com.designpattern.DAoImpl.EventsDAOImpl;

import com.designpattern.model.Restaurant;
import com.designpattern.model.Category;
import com.designpattern.model.Event;

import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private RestaurantDAO restaurantDAO;
    private CategoryDAO categoryDAO;
    private EventsDAO eventDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            restaurantDAO = new RestaurantDAOImpl(conn);
            categoryDAO = new CategoryDAOImpl(conn);
            eventDAO = new EventsDAOImpl(conn);
            System.out.println("✅ DB connection established in HomeServlet");
        } catch (Exception e) {
            throw new ServletException("DB connection failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Fetch restaurants, categories, events
        List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
        request.setAttribute("restaurants", restaurants);

        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        List<Event> events = eventDAO.getAllEvents();
        request.setAttribute("events", events);

        // Forward to home.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST by delegating to doGet
        doGet(request, response);
    }
}
