package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.Order;
import com.designpattern.model.OrderItem;

public interface OrderDAO {
    int createOrder(Order order);

    void addOrderItem(OrderItem orderItem);

    int placeOrder(int userId, int cartId, int restaurantId, String paymentMethod);

    Order getOrderById(int orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByUser(int userId);

    List<Order> getOrdersByRestaurant(int restaurantId);

    void updateOrderTotal(int orderId, double totalAmount);

    void updateStatus(int orderId, String status);

    void deleteOrder(int orderId);
}
