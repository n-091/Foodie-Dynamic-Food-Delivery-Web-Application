package com.designpattern.DAoImpl;

import com.designpattern.dao.CategoryDAO;
import com.designpattern.model.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAOImpl implements CategoryDAO {
    private Connection conn;

    public CategoryDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, image_path FROM categories";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setImagePath(rs.getString("image_path"));
                categories.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}

