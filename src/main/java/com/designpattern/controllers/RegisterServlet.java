package com.designpattern.controllers;

import java.io.IOException;

import com.designpattern.DAoImpl.UserDAOImpl;
import com.designpattern.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAOImpl dao;

    @Override
    public void init() throws ServletException {
        dao = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("fullName") != null
                ? request.getParameter("fullName").trim()
                : "";

        String email = request.getParameter("email") != null
                ? request.getParameter("email").trim()
                : "";

        String password = request.getParameter("password") != null
                ? request.getParameter("password").trim()
                : "";

        String confirmPassword = request.getParameter("confirmPassword") != null
                ? request.getParameter("confirmPassword").trim()
                : "";

        String phone = request.getParameter("phone") != null
                ? request.getParameter("phone").trim()
                : "";

        String address = request.getParameter("address") != null
                ? request.getParameter("address").trim()
                : "";

        String city = request.getParameter("city") != null
                ? request.getParameter("city").trim()
                : "";

        // Validate required fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()
                || phone.isEmpty()
                || address.isEmpty()
                || city.isEmpty()) {

            request.setAttribute("error", "Please fill in all required fields.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Password validation
        if (!password.equals(confirmPassword)) {

            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Email already exists
        if (dao.getUserByEmail(email) != null) {

            request.setAttribute("error",
                    "An account with this email already exists. Please login.");

            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Create User
        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setPassword(password); // Hash in production
        user.setPhoneNo(phone);
        user.setAddress(address + ", " + city);
        user.setRole("customer");

        boolean saved = dao.addUser(user);

        if (saved) {

            HttpSession session = request.getSession();
            session.setAttribute("message",
                    "Registration successful! Please login.");

            response.sendRedirect(request.getContextPath() + "/login");

        } else {

            request.setAttribute("error",
                    "Unable to register. Please try again later.");

            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}