package com.designpattern.DAoImpl;

import com.designpattern.dao.RestaurantDAO;
import com.designpattern.model.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAOImpl implements RestaurantDAO {

    private Connection connection;

    public RestaurantDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRestaurant(Restaurant r) {
        String sql = "INSERT INTO restaurant (name, cuisineType, deliveryTime, address, adminUserId, rating, isActive) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getCuisineType());
            ps.setInt(3, r.getDeliveryTime());
            ps.setString(4, r.getAddress());
            ps.setInt(5, r.getAdminUserId());
            ps.setDouble(6, r.getRating());
            ps.setBoolean(7, r.isActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRestaurant(Restaurant r) {
        String sql = "UPDATE restaurant SET name=?, cuisineType=?, deliveryTime=?, address=?, adminUserId=?, rating=?, isActive=? WHERE restaurantId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getCuisineType());
            ps.setInt(3, r.getDeliveryTime());
            ps.setString(4, r.getAddress());
            ps.setInt(5, r.getAdminUserId());
            ps.setDouble(6, r.getRating());
            ps.setBoolean(7, r.isActive());
            ps.setInt(8, r.getRestaurantId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurant WHERE restaurantId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant getRestaurant(int id) {
        String sql = "SELECT * FROM restaurant WHERE restaurantId=?";
        Restaurant r = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r = extractRestaurant(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE isActive = TRUE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Restaurant r = new Restaurant();
                r.setRestaurantId(rs.getInt("RestaurantID"));
                r.setName(rs.getString("name"));
                r.setCuisineType(rs.getString("cuisineType"));
                r.setDeliveryTime(rs.getInt("deliveryTime"));
                r.setAddress(rs.getString("address"));
                r.setAdminUserId(rs.getInt("adminUserId"));
                r.setRating(rs.getDouble("rating"));
                r.setActive(rs.getBoolean("isActive"));
                r.setImagePath(rs.getString("image_path")); // ✅ now works
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    // Helper method
    private Restaurant extractRestaurant(ResultSet rs) throws SQLException {
        Restaurant r = new Restaurant();
        r.setRestaurantId(rs.getInt("restaurantId"));
        r.setName(rs.getString("name"));
        r.setCuisineType(rs.getString("cuisineType"));
        r.setDeliveryTime(rs.getInt("deliveryTime"));
        r.setAddress(rs.getString("address"));
        r.setAdminUserId(rs.getInt("adminUserId"));
        r.setRating(rs.getDouble("rating"));
        r.setActive(rs.getBoolean("isActive"));
        return r;
    }
    
    @Override
    public Restaurant getRestaurantById(int id) {
    	String sql = "SELECT * FROM restaurant WHERE restaurantId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Restaurant r = new Restaurant();
                r.setRestaurantId(rs.getInt("restaurantId"));
                r.setName(rs.getString("name"));
                r.setCuisineType(rs.getString("cuisineType"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("deliveryTime"));
                r.setImagePath(rs.getString("image_path"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
}
