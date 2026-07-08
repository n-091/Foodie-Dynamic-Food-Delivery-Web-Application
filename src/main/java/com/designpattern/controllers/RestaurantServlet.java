package com.designpattern.controllers;

import com.designpattern.dao.RestaurantDAO;
import com.designpattern.DAoImpl.RestaurantDAOImpl;
import com.designpattern.model.Restaurant;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            restaurantDAO = new RestaurantDAOImpl(conn);
            System.out.println("✅ DB connection established in RestaurantServlet");
        } catch (Exception e) {
            throw new ServletException("DB connection failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String restaurantParam = request.getParameter("restaurantId");

        if (restaurantParam != null) {
            try {
                int restaurantId = Integer.parseInt(restaurantParam);
                HttpSession session = request.getSession();
                session.setAttribute("restaurantId", restaurantId);
                System.out.println("✅ Stored restaurantId in session: " + restaurantId);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid restaurantId parameter: " + restaurantParam);
            }
        }

        List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();

        if (restaurants == null || restaurants.isEmpty()) {
            System.out.println("⚠️ No restaurants fetched from DB!");
            request.setAttribute("pageTitle", "Our Delicious Menu");
        } else {
            System.out.println("✅ Fetched " + restaurants.size() + " restaurants from DB.");
            Restaurant firstRestaurant = restaurants.get(0);
            request.setAttribute("pageTitle", "Menu for " + firstRestaurant.getName());
        }

        request.setAttribute("restaurants", restaurants);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/restaurant.jsp");
        dispatcher.forward(request, response);
    }

}