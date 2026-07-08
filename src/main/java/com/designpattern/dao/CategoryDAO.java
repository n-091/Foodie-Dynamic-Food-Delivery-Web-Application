package com.designpattern.dao;

import com.designpattern.model.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();
}
