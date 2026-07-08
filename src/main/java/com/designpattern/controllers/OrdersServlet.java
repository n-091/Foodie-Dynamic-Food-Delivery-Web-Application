package com.designpattern.controllers;

import com.designpattern.model.User;
import com.designpattern.model.Order;
import com.designpattern.DAoImpl.OrderDAOImpl;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

    private OrderDAOImpl orderDAO;
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            // Establish database connection
            conn = DBConnection.getConnection();
            orderDAO = new OrderDAOImpl(conn);

            System.out.println("✅ OrdersServlet initialized successfully.");

        } catch (SQLException e) {
            throw new ServletException("Failed to establish database connection.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // User not logged in
        if (currentUser == null) {

            // After login return back to My Orders
            session.setAttribute("redirectAfterLogin", "orders");

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Fetch all orders of the logged-in user
        List<Order> orders = orderDAO.getOrdersByUser(currentUser.getUserId());

        // Send data to JSP
        request.setAttribute("ordersList", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("orders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ OrdersServlet DB connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}