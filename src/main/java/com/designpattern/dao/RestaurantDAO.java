package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.Restaurant;

public interface RestaurantDAO {

    void addRestaurant(Restaurant r);

    void updateRestaurant(Restaurant r);

    void deleteRestaurant(int id);

    Restaurant getRestaurant(int id);

    List<Restaurant> getAllRestaurants();
    
    Restaurant getRestaurantById(int id);
    
    
}