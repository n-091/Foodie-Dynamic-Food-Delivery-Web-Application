package com.designpattern.controllers;

import com.designpattern.model.User;
import com.designpattern.DAoImpl.UserDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/updateProfile")
public class ProfileServlet extends HttpServlet {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action"); // "update" or "delete"

        if ("delete".equalsIgnoreCase(action)) {
            deleteProfile(request, response);
        } else {
            updateProfile(request, response);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println("🔎 Attempting to update profile for userId: " + userId);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        System.out.println("➡️ New values - Name: " + name + ", Email: " + email +
                           ", Phone: " + phone + ", Address: " + address);

        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setUserName(name);
        updatedUser.setEmail(email);
        updatedUser.setPhoneNo(phone);
        updatedUser.setAddress(address);

        boolean success = userDAO.updateProfileDetails(updatedUser);

        HttpSession session = request.getSession();
        if (success) {
            User refreshedUser = userDAO.getUser(userId);
            session.setAttribute("currentUser", refreshedUser);
            session.setAttribute("userId", refreshedUser.getUserId());
            request.setAttribute("message", "✅ Profile updated successfully!");
            System.out.println("✅ Profile updated successfully for userId: " + userId);
        } else {
            request.setAttribute("error", "❌ Failed to update profile.");
            System.out.println("❌ Failed to update profile for userId: " + userId);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println("🔎 Attempting to delete userId: " + userId);

        boolean success = userDAO.deleteUser(userId);

        HttpSession session = request.getSession();
        if (success) {
            System.out.println("✅ User deleted successfully: " + userId);
            session.invalidate(); // clear session

            // ✅ Redirect safely without emoji in header
            response.sendRedirect("home?message=ProfileDeleted");
        } else {
            System.out.println("❌ Failed to delete user: " + userId);
            request.setAttribute("error", "❌ Failed to delete profile.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        }
    }
}
