package com.designpattern.controllers;

import com.designpattern.dao.OrderDAO;
import com.designpattern.DAoImpl.OrderDAOImpl;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/paymentSuccess")
public class PaymentSuccessServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            orderDAO = new OrderDAOImpl(conn);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String status = req.getParameter("status");

        if ("success".equals(status)) {
            orderDAO.updateStatus(orderId, "Paid");
            session.setAttribute("orderStatus", "Paid");

            RequestDispatcher rd = req.getRequestDispatcher("order-success.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendRedirect("payment-failed.jsp");
        }
    }
}

