package com.designpattern.controllers;

import com.designpattern.DAoImpl.UserDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "❌ New passwords do not match.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        boolean success = userDAO.updatePassword(userId, oldPassword, newPassword);

        if (success) {
            request.setAttribute("message", "✅ Password updated successfully!");
        } else {
            request.setAttribute("error", "❌ Incorrect current password.");
        }

        // ✅ Forward back to same page
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
