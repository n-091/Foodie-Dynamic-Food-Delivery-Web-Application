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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            cartDAO = new CartDAOImpl(conn);
            cartItemDAO = new CartItemDAOImpl(conn);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize CartServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        try {
            List<CartItem> cartItems;
            double subtotal = 0.0, delivery = 0.0, discount = 0.0, total = 0.0;
            if (userId == null) {
                @SuppressWarnings("unchecked")
                List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");

                if (guestCart == null) {
                    guestCart = new ArrayList<>();
                } else {
                    // ✅ Rehydrate guest items with imagePath
                    List<CartItem> hydratedCart = new ArrayList<>();
                    for (CartItem gItem : guestCart) {
                        CartItem fullItem = cartItemDAO.getMenuItem(gItem.getMenuId());
                        if (fullItem != null) {
                            fullItem.setQuantity(gItem.getQuantity()); // keep quantity
                            hydratedCart.add(fullItem);
                        }
                    }
                    guestCart = hydratedCart;
                    session.setAttribute("guestCart", guestCart);
                }

                cartItems = guestCart;
            } else {
                // logged‑in flow unchanged
                Cart cart = cartDAO.getCartByUserId(userId);
                if (cart == null) {
                    cart = new Cart();
                    cart.setUserId(userId);
                    cartDAO.addCart(cart);
                    cart = cartDAO.getCartByUserId(userId);
                }
                cartItems = cartItemDAO.getItemsByCartId(cart.getCartId());
            }


            // ✅ Calculate totals (works for both guest & logged-in)
            if (cartItems != null && !cartItems.isEmpty()) {
                subtotal = cartItems.stream()
                                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                                    .sum();
                delivery = (subtotal > 0) ? 40.0 : 0.0;
                discount = (subtotal >= 299) ? 50.0 : 0.0;
                total = subtotal + delivery - discount;
            }

            // ✅ Set attributes for JSP
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("delivery", delivery);
            request.setAttribute("discount", discount);
            request.setAttribute("total", total);

            // ✅ Store totals in session (for checkout/success page)
            session.setAttribute("subtotal", subtotal);
            session.setAttribute("delivery", delivery);
            session.setAttribute("discount", discount);
            session.setAttribute("total", total);

            // ✅ Forward to cart.jsp
            request.getRequestDispatcher("cart.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
