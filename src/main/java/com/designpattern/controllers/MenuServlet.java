package com.designpattern.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.designpattern.dao.RestaurantDAO;          // ✅ use the interface
import com.designpattern.DAoImpl.MenuDAOImpl;
import com.designpattern.DAoImpl.RestaurantDAOImpl; // ✅ implementation
import com.designpattern.model.Menu;
import com.designpattern.model.Restaurant;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private MenuDAOImpl menuDAOImpl;
    private RestaurantDAO restaurantDAO;   // ✅ declare using interface

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            menuDAOImpl = new MenuDAOImpl(conn);
            restaurantDAO = new RestaurantDAOImpl(conn); // ✅ initialize
            System.out.println("✅ DB connection established in MenuServlet");
        } catch (Exception e) {
            throw new ServletException("DB connection failed", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // handle POST requests (forms, actions)
    }
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String restaurantIdParam = req.getParameter("restaurantId");
        String categoryParam = req.getParameter("category");

        List<Menu> menuItems = null;

        try {
            if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
                int restaurantId = Integer.parseInt(restaurantIdParam);
                menuItems = menuDAOImpl.getAllMenuByRestaurant(restaurantId);
                
             // ✅ Store restaurantId in session for checkout.jsp and placeOrder
                req.getSession().setAttribute("restaurantId", restaurantId);
                System.out.println("✅ Stored restaurantId in session: " + restaurantId);

                // ✅ Fetch restaurant name
                Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
                if (restaurant != null) {
                    req.setAttribute("pageTitle", restaurant.getName() + " Menu");
                } else {
                    req.setAttribute("pageTitle", "Restaurant Menu");
                }

            } else if (categoryParam != null && !categoryParam.isEmpty()) {
                menuItems = menuDAOImpl.getAllMenuByCategory(categoryParam);
                req.setAttribute("pageTitle", "Browse Category: " + categoryParam);

            } else {
                // ✅ Handle "All View Menu"
                menuItems = menuDAOImpl.getAllMenu();
                req.setAttribute("pageTitle", "All Restaurants Menu");
            }

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid restaurantId format");
            return;
        }

        req.setAttribute("menuItems", menuItems);

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }
}
