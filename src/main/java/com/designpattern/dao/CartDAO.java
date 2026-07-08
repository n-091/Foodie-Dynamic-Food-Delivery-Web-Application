package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.Cart;

public interface CartDAO {

    void addCart(Cart cart);

    Cart getCart(int cartId);

    Cart getCartByUserId(int userId);

    void deleteCart(int cartId);
    
    List<Cart> getAllCarts();

    // ✅ New helper method to guarantee a cart exists
    int getOrCreateCartId(int userId);
}
