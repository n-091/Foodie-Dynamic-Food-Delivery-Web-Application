package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.Menu;

public interface MenuDAO {

    void addMenu(Menu m);

    void updateMenu(Menu m);

    void deleteMenu(int id);

    Menu getMenu(int id);

    // =========================
    // GET MENU BY RESTAURANT ID
    // =========================
    List<Menu> getAllMenuByRestaurant(int restaurantId);

    // =========================
    // GET ALL MENU (all restaurants)
    // =========================
    List<Menu> getAllMenu();

    // =========================
    // GET MENU BY CATEGORY
    // =========================
    List<Menu> getAllMenuByCategory(String category);
}
