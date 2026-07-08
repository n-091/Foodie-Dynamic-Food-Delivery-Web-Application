package com.designpattern.DAoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.CartItemDAO;
import com.designpattern.model.CartItem;

public class CartItemDAOImpl implements CartItemDAO {

    private Connection conn;

    public CartItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // =========================
    // ADD CART ITEM
    // =========================
    @Override
    public void addCartItem(CartItem item) {
        String sql = "INSERT INTO cartitem(cartId, menuId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getMenuId());
            ps.setInt(3, item.getQuantity());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET CART ITEM BY ID
    // =========================
    @Override
    public CartItem getCartItem(int cartItemId) {
        CartItem item = null;
        String sql = "SELECT ci.cartItemId, ci.cartId, ci.menuId, ci.quantity, " +
                     "m.itemName, m.price, m.image_path, m.restaurantId " +
                     "FROM cartitem ci JOIN menu m ON ci.menuId = m.menuId " +
                     "WHERE ci.cartItemId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = new CartItem();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setCartId(rs.getInt("cartId"));
                item.setMenuId(rs.getInt("menuId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));
                item.setImagePath(rs.getString("image_path"));
                item.setRestaurantId(rs.getInt("restaurantId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // =========================
    // GET ITEMS BY CART ID
    // =========================
    @Override
    public List<CartItem> getItemsByCartId(int cartId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT ci.cartItemId, ci.cartId, ci.menuId, ci.quantity, " +
                     "m.itemName, m.price, m.image_path, m.restaurantId " +
                     "FROM cartitem ci " +
                     "JOIN menu m ON ci.menuId = m.MenuID " +
                     "WHERE ci.cartId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setCartId(rs.getInt("cartId"));
                item.setMenuId(rs.getInt("menuId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));
                item.setImagePath(rs.getString("image_path"));
                item.setRestaurantId(rs.getInt("restaurantId"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // =========================
    // UPDATE QUANTITY
    // =========================
    @Override
    public void updateQuantity(int cartItemId, int quantity) {
        String sql = "UPDATE cartitem SET quantity=? WHERE cartItemId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DELETE CART ITEM
    // =========================
    @Override
    public void deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM cartitem WHERE cartItemId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItemsByCartId(int cartId) {
        String sql = "DELETE FROM cartitem WHERE cartId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // HELPER METHODS
    // =========================
    @Override
    public int getOrCreateCartId(int userId) {
        int cartId = -1;
        try {
            String checkSql = "SELECT cartId FROM cart WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt("cartId");
            } else {
                String insertSql = "INSERT INTO cart(user_id) VALUES(?)";
                PreparedStatement ps2 = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, userId);
                ps2.executeUpdate();
                ResultSet keys = ps2.getGeneratedKeys();
                if (keys.next()) {
                    cartId = keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartId;
    }

    @Override
    public void addOrUpdateCartItem(CartItem item) {
        try {
            String checkSql = "SELECT cartItemId, quantity FROM cartitem WHERE cartId=? AND menuId=?";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getMenuId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cartItemId = rs.getInt("cartItemId");
                int currentQty = rs.getInt("quantity");
                updateQuantity(cartItemId, currentQty + item.getQuantity());
            } else {
                addCartItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // NEW METHODS FOR SERVLET
    // =========================
    @Override
    public CartItem getItemByCartAndMenu(int cartId, int menuId) {
        CartItem item = null;
        String sql = "SELECT ci.cartItemId, ci.cartId, ci.menuId, ci.quantity, " +
                     "m.itemName, m.price, m.image_path, m.restaurantId " +
                     "FROM cartitem ci JOIN menu m ON ci.menuId = m.menuId " +
                     "WHERE ci.cartId=? AND ci.menuId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, menuId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = new CartItem();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setCartId(rs.getInt("cartId"));
                item.setMenuId(rs.getInt("menuId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));
                item.setImagePath(rs.getString("image_path"));
                item.setRestaurantId(rs.getInt("restaurantId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void updateCartItem(CartItem item) {
        String sql = "UPDATE cartitem SET quantity=? WHERE cartItemId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getCartItemId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CartItem> getCartItemsByUser(int userId) {
        List<CartItem> cartItems = new ArrayList<>();

        String sql = "SELECT ci.cartItemId, ci.cartId, ci.menuId, ci.quantity, " +
                     "m.itemName, m.price, m.image_path, m.restaurantId " +
                     "FROM cartitem ci " +
                     "INNER JOIN cart c ON ci.cartId = c.cartId " +
                     "INNER JOIN menu m ON ci.menuId = m.menuId " +
                     "WHERE c.user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setCartId(rs.getInt("cartId"));
                item.setMenuId(rs.getInt("menuId"));
                item.setQuantity(rs.getInt("quantity"));

                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));
                item.setImagePath(rs.getString("image_path"));
                item.setRestaurantId(rs.getInt("restaurantId"));

                cartItems.add(item);
            }

            // Debugging output (optional)
            System.out.println("DEBUG: Cart size = " + cartItems.size());
            for (CartItem item : cartItems) {
                System.out.println(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public CartItem getMenuItem(int menuId) {
        CartItem item = null;
        String sql = "SELECT MenuID, itemName, price, image_path, restaurantId FROM menu WHERE MenuID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new CartItem();
                    item.setMenuId(rs.getInt("MenuID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setPrice(rs.getDouble("price"));
                    item.setImagePath(rs.getString("image_path")); // ✅ critical
                    item.setRestaurantId(rs.getInt("restaurantId"));
                    item.setQuantity(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

}