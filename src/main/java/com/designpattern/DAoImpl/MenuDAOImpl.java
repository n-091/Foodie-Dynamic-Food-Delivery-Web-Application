package com.designpattern.DAoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.MenuDAO;
import com.designpattern.model.Menu;

public class MenuDAOImpl implements MenuDAO {

    private Connection conn;

    public MenuDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public MenuDAOImpl() {
        // Default constructor
    }
    
    
    

    // =========================
    // ADD MENU
    // =========================
    @Override
    public void addMenu(Menu m) {
        String sql = "INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getRestaurantId());
            ps.setString(2, m.getItemName());
            ps.setString(3, m.getDescription());
            ps.setDouble(4, m.getPrice());
            ps.setBoolean(5, m.isAvailable());
            ps.setString(6, m.getImagePath());   // ✅ new field
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET MENU BY ID
    // =========================
    @Override
    public Menu getMenu(int id) {
        Menu m = null;
        String sql = "SELECT * FROM menu WHERE menuId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = extractMenu(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    // =========================
    // UPDATE MENU
    // =========================
    @Override
    public void updateMenu(Menu m) {
        String sql = "UPDATE menu SET restaurantId=?, itemName=?, description=?, price=?, isAvailable=?, image_path=? WHERE menuId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getRestaurantId());
            ps.setString(2, m.getItemName());
            ps.setString(3, m.getDescription());
            ps.setDouble(4, m.getPrice());
            ps.setBoolean(5, m.isAvailable());
            ps.setString(6, m.getImagePath());
            ps.setInt(7, m.getMenuId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DELETE MENU
    // =========================
    @Override
    public void deleteMenu(int id) {
        String sql = "DELETE FROM menu WHERE menuId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET MENU BY RESTAURANT ID
    // =========================
    @Override
    public List<Menu> getAllMenuByRestaurant(int restaurantId) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE restaurantId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractMenu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // GET ALL MENU (all restaurants)
    // =========================
    @Override
    public List<Menu> getAllMenu() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractMenu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // GET MENU BY CATEGORY
    // =========================
    @Override
    public List<Menu> getAllMenuByCategory(String category) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE category = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractMenu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    // =========================
    // Helper Method
    // =========================
    private Menu extractMenu(ResultSet rs) throws SQLException {
        Menu m = new Menu();
        m.setMenuId(rs.getInt("menuId"));
        m.setRestaurantId(rs.getInt("restaurantId"));
        m.setItemName(rs.getString("itemName"));
        m.setDescription(rs.getString("description"));
        m.setPrice(rs.getDouble("price"));
        m.setAvailable(rs.getBoolean("isAvailable"));
        m.setImagePath(rs.getString("image_path")); // ✅ new field
        return m;
    }
}
