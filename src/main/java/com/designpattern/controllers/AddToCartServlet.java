package com.designpattern.controllers;

import com.designpattern.dao.CartDAO;
import com.designpattern.dao.CartItemDAO;
import com.designpattern.DAoImpl.CartDAOImpl;
import com.designpattern.DAoImpl.CartItemDAOImpl;
import com.designpattern.model.Cart;
import com.designpattern.model.CartItem;
import com.designpattern.utility.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            cartDAO = new CartDAOImpl(conn);
            cartItemDAO = new CartItemDAOImpl(conn);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize AddToCartServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        int menuId = Integer.parseInt(request.getParameter("menuId"));
        String itemName = request.getParameter("itemName");   // make sure you pass this in your form
        double price = Double.parseDouble(request.getParameter("price"));

        if (userId == null) {
            // ✅ Guest cart stored in session
            @SuppressWarnings("unchecked")
            List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");
            if (guestCart == null) {
                guestCart = new ArrayList<>();
            }

            // check if item already exists
            CartItem existing = guestCart.stream()
                    .filter(i -> i.getMenuId() == menuId)
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + 1);
            } else {
                CartItem item = new CartItem();
                item.setMenuId(menuId);
                item.setItemName(itemName);
                item.setPrice(price);
                item.setQuantity(1);
                guestCart.add(item);
            }

            session.setAttribute("guestCart", guestCart);

        } else {
            // ✅ Logged-in user cart stored in DB
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                cartDAO.addCart(cart);
                cart = cartDAO.getCartByUserId(userId);
            }

            CartItem existing = cartItemDAO.getItemByCartAndMenu(cart.getCartId(), menuId);
            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + 1);
                cartItemDAO.updateCartItem(existing);
            } else {
                CartItem item = new CartItem();
                item.setCartId(cart.getCartId());
                item.setMenuId(menuId);
                item.setItemName(itemName);
                item.setPrice(price);
                item.setQuantity(1);
                cartItemDAO.addCartItem(item);
            }
        }

        // ✅ Redirect to cart page
        response.sendRedirect("cart");
    }
}
