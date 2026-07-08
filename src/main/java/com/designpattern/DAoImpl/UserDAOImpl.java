package com.designpattern.DAoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.UserDAO;
import com.designpattern.model.User;
import com.designpattern.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private Connection con;

    public UserDAOImpl() {
        try {
            con = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addUser(User u) {
        String query = "INSERT INTO user(user_name,email,password,address,phone_no,role) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, u.getUserName());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getAddress());
            pstmt.setString(5, u.getPhoneNo());
            pstmt.setString(6, u.getRole());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ User Added Successfully: " + u.getEmail());
                return true;
            } else {
                System.out.println("⚠️ User Add Failed: " + u.getEmail());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Full update (requires all fields)
    @Override
    public boolean updateUser(User u) {
        String query = "UPDATE user SET user_name=?, email=?, password=?, address=?, phone_no=?, role=? WHERE user_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, u.getUserName());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getAddress());
            pstmt.setString(5, u.getPhoneNo());
            pstmt.setString(6, u.getRole());
            pstmt.setInt(7, u.getUserId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ User Updated Successfully: " + u.getUserId());
                return true;
            } else {
                System.out.println("⚠️ User Not Found for Update: " + u.getUserId());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Profile update (only editable fields)
    @Override
    public boolean updateProfileDetails(User u) {
        String query = "UPDATE user SET user_name=?, email=?, address=?, phone_no=? WHERE user_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, u.getUserName());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getAddress());
            pstmt.setString(4, u.getPhoneNo());
            pstmt.setInt(5, u.getUserId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Profile Updated Successfully: " + u.getUserId());
                return true;
            } else {
                System.out.println("⚠️ User Not Found for Profile Update: " + u.getUserId());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  
    @Override
    public User getUser(int id) {
        String query = "SELECT * FROM user WHERE user_id=?";
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                    System.out.println("ℹ️ User Retrieved: " + user.getEmail());
                } else {
                    System.out.println("⚠️ User Not Found: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                userList.add(mapResultSetToUser(rs));
            }
            System.out.println("ℹ️ Total Users Retrieved: " + userList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM user WHERE email=? AND password=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);

                    // ✅ Update login_date to NOW
                    String updateLoginDate = "UPDATE user SET login_date = CURRENT_TIMESTAMP WHERE user_id=?";
                    try (PreparedStatement updateStmt = con.prepareStatement(updateLoginDate)) {
                        updateStmt.setInt(1, user.getUserId());
                        updateStmt.executeUpdate();
                    }

                    System.out.println("✅ Login Success for: " + email);
                    return user;
                } else {
                    System.out.println("⚠️ Login Failed for: " + email);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email=?";
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                    System.out.println("ℹ️ User Retrieved by Email: " + email);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNo(rs.getString("phone_no"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setLoginDate(rs.getTimestamp("login_date"));
        return user;
    }
    
    
    public boolean updatePassword(int userId, String oldPassword, String newPassword) {
        String query = "UPDATE user SET password=? WHERE user_id=? AND password=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            pstmt.setString(3, oldPassword);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteUser(int id) {
        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(
                "DELETE FROM cartitem WHERE cartId IN (SELECT cartId FROM cart WHERE user_id=?)")) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement("DELETE FROM cart WHERE user_id=?")) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            try (PreparedStatement ps3 = con.prepareStatement(
                "DELETE FROM orderitem WHERE orderId IN (SELECT orderId FROM orders WHERE user_id=?)")) {
                ps3.setInt(1, id);
                ps3.executeUpdate();
            }

            try (PreparedStatement ps4 = con.prepareStatement("DELETE FROM orders WHERE user_id=?")) {
                ps4.setInt(1, id);
                ps4.executeUpdate();
            }

            try (PreparedStatement ps5 = con.prepareStatement("DELETE FROM user WHERE user_id=?")) {
                ps5.setInt(1, id);
                int rows = ps5.executeUpdate();
                con.commit();
                return rows > 0;
            }
        } catch (Exception e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

}
