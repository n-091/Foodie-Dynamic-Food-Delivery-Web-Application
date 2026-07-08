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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAOImpl dao;

    @Override
    public void init() {
        dao = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Already logged in
        if (session != null && session.getAttribute("currentUser") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        email = email == null ? "" : email.trim();
        password = password == null ? "" : password.trim();

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Please enter Email and Password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User user = dao.getUserByEmailAndPassword(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            // Save redirect URL before session changes
            String redirect = (String) session.getAttribute("redirectAfterLogin");

            // Store logged-in user
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getUserId());

            // Redirect to previous page or Home
            if (redirect != null && !redirect.isEmpty()) {
                session.removeAttribute("redirectAfterLogin");
                response.sendRedirect(redirect);
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }

        } else {

            request.setAttribute("errorMessage", "Invalid Email or Password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}