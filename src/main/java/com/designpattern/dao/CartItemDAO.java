package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.CartItem;

public interface CartItemDAO {
    void addCartItem(CartItem item);
    CartItem getCartItem(int cartItemId);
    List<CartItem> getItemsByCartId(int cartId);
    void updateQuantity(int cartItemId, int quantity);
    void deleteCartItem(int cartItemId);
    void deleteItemsByCartId(int cartId);

    CartItem getItemByCartAndMenu(int cartId, int menuId);
    void updateCartItem(CartItem item);

    int getOrCreateCartId(int userId);
    void addOrUpdateCartItem(CartItem item);

    List<CartItem> getCartItemsByUser(int userId);

    // ✅ New method for guest + logged-in unified flow
    CartItem getMenuItem(int menuId);
}
