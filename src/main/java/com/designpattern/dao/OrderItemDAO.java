package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.OrderItem;

/**
 * DAO Interface for Order Item operations
 * Handles CRUD operations for orderitem table
 */
public interface OrderItemDAO {

	// =========================
	// ADD SINGLE ORDER ITEM
	// =========================
	void addOrderItem(OrderItem item) ;
	
	void addOrderItem(int orderId, int menuId, int quantity, double itemTotal) ;

	// =========================
	// ADD MULTIPLE ORDER ITEMS (CART SUPPORT)
	// =========================
	void addOrderItems(List<OrderItem> items);

	// =========================
	// GET ALL ITEMS OF A SPECIFIC ORDER
	// =========================
	List<OrderItem> getItemsByOrderId(int orderId);

	// =========================
	// DELETE SINGLE ORDER ITEM
	// =========================
	void deleteOrderItem(int orderItemId);
}