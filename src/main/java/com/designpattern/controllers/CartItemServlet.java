package com.designpattern.controllers;

import com.designpattern.dao.CartItemDAO;
import com.designpattern.DAoImpl.CartItemDAOImpl;
import com.designpattern.model.CartItem;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cartItem")
public class CartItemServlet extends HttpServlet {

    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {
        try {
            cartItemDAO = new CartItemDAOImpl(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize CartItemDAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");

        try {
            final int menuId = request.getParameter("menuId") != null
                    ? Integer.parseInt(request.getParameter("menuId"))
                    : -1;

            if (userId == null) {
                // ✅ Guest cart stored in session
                @SuppressWarnings("unchecked")
                List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");
                if (guestCart == null) guestCart = new java.util.ArrayList<>();

                if ("add".equals(action)) {
                    CartItem existing = null;
                    for (CartItem i : guestCart) {
                        if (i.getMenuId() == menuId) {
                            existing = i;
                            break;
                        }
                    }

                    if (existing != null) {
                        existing.setQuantity(existing.getQuantity() + 1);
                    } else {
                        CartItem item = cartItemDAO.getMenuItem(menuId); // ✅ hydrates with imagePath
                        if (item != null) {
                            guestCart.add(item);
                            System.out.println("DEBUG GuestCart imagePath = " + item.getImagePath());
                        }
                    }

                } else if ("increase".equals(action)) {
                    for (CartItem item : guestCart) {
                        if (item.getMenuId() == menuId) {
                            item.setQuantity(item.getQuantity() + 1);
                            break;
                        }
                    }

                } else if ("decrease".equals(action)) {
                    for (CartItem item : guestCart) {
                        if (item.getMenuId() == menuId) {
                            if (item.getQuantity() > 1) {
                                item.setQuantity(item.getQuantity() - 1);
                            } else {
                                guestCart.remove(item);
                            }
                            break;
                        }
                    }

                } else if ("remove".equals(action)) {
                    guestCart.removeIf(i -> i.getMenuId() == menuId);
                }

                // ✅ Update session
                session.setAttribute("guestCart", guestCart);

            } else {
                // ✅ Logged-in user cart in DB
                if ("add".equals(action)) {
                    int cartId = cartItemDAO.getOrCreateCartId(userId);
                    CartItem item = cartItemDAO.getMenuItem(menuId);
                    if (item != null) {
                        item.setCartId(cartId);
                        cartItemDAO.addOrUpdateCartItem(item);
                    }

                } else if ("increase".equals(action)) {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    CartItem item = cartItemDAO.getCartItem(cartItemId);
                    if (item != null) {
                        cartItemDAO.updateQuantity(cartItemId, item.getQuantity() + 1);
                    }

                } else if ("decrease".equals(action)) {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    CartItem item = cartItemDAO.getCartItem(cartItemId);
                    if (item != null) {
                        if (item.getQuantity() > 1) {
                            cartItemDAO.updateQuantity(cartItemId, item.getQuantity() - 1);
                        } else {
                            cartItemDAO.deleteCartItem(cartItemId);
                        }
                    }

                } else if ("remove".equals(action)) {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    cartItemDAO.deleteCartItem(cartItemId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Always redirect to CartServlet
        response.sendRedirect("cart");
    }
}
