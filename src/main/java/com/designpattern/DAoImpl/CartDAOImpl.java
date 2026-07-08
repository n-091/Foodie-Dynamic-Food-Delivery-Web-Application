package com.designpattern.DAoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.CartDAO;
import com.designpattern.model.Cart;

public class CartDAOImpl implements CartDAO {

    private Connection conn;

    public CartDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // =========================
    // ADD CART
    // =========================
    @Override
    public void addCart(Cart cart) {
        String sql = "INSERT INTO cart(user_id) VALUES(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cart.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET CART BY ID
    // =========================
    @Override
    public Cart getCart(int cartId) {
        Cart cart = null;
        String sql = "SELECT * FROM cart WHERE cartId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedAt(rs.getTimestamp("createdAt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    // =========================
    // GET CART BY USER ID
    // =========================
    @Override
    public Cart getCartByUserId(int userId) {
        Cart cart = null;
        String sql = "SELECT * FROM cart WHERE user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedAt(rs.getTimestamp("createdAt"));
                System.out.println("✅ Cart fetched for userId: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    // =========================
    // GET ALL CARTS
    // =========================
    @Override
    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM cart";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedAt(rs.getTimestamp("createdAt"));
                carts.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carts;
    }

    // =========================
    // DELETE CART
    // =========================
    @Override
    public void deleteCart(int cartId) {
        String sql = "DELETE FROM cart WHERE cartId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET OR CREATE CART ID
    // =========================
    @Override
    public int getOrCreateCartId(int userId) {
        Cart cart = getCartByUserId(userId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            addCart(newCart);
            cart = getCartByUserId(userId);
        }

        if (cart == null) {
            throw new RuntimeException("❌ Cart creation failed for userId: " + userId);
        }

        return cart.getCartId();
    }
}
