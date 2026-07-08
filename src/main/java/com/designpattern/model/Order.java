package com.designpattern.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	private int orderId;
	private int userId;
	private int restaurantId;
	private String restaurantName;   // ✅ new field
	private List<String> items;      // ✅ new field
	private double totalAmount;
	private String status;
	private String paymentMethod;
	private Timestamp orderDate;

	// Getters & Setters
	public int getOrderId() { return orderId; }
	public void setOrderId(int orderId) { this.orderId = orderId; }

	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }

	public int getRestaurantId() { return restaurantId; }
	public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

	public String getRestaurantName() { return restaurantName; }
	public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

	public List<String> getItems() { return items; }
	public void setItems(List<String> items) { this.items = items; }

	public double getTotalAmount() { return totalAmount; }
	public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getPaymentMethod() { return paymentMethod; }
	public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

	public Timestamp getOrderDate() { return orderDate; }
	public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

	// ✅ Helper method for JSP
	public String getItemsSummary() {
		if (items == null || items.isEmpty()) return "No items";
		return String.join(", ", items);
	}
}
