package com.designpattern.DAoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.OrderItemDAO;
import com.designpattern.model.CartItem;
import com.designpattern.model.OrderItem;
import com.designpattern.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

	private Connection conn;

	public OrderItemDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// =========================
	// ADD SINGLE ORDER ITEM
	// =========================
	@Override
	public void addOrderItem(OrderItem item) {

	    String sql =
	            "INSERT INTO orderitem (OrderID, MenuID, Quantity, ItemTotal) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, item.getOrderId());
	        ps.setInt(2, item.getMenuId());
	        ps.setInt(3, item.getQuantity());
	        ps.setDouble(4, item.getItemTotal());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// =========================
	// ADD MULTIPLE ORDER ITEMS
	// =========================
	@Override
	public void addOrderItems(List<OrderItem> items) {

		try {
			for (OrderItem item : items) {
				addOrderItem(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// =========================
	// GET ITEMS BY ORDER ID
	// =========================
	@Override
	public List<OrderItem> getItemsByOrderId(int orderId) {

	    List<OrderItem> list = new ArrayList<>();

	    String sql ="SELECT * FROM orderitem WHERE OrderID=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, orderId);

	        ResultSet rs = ps.executeQuery();


	        while (rs.next()) {

	            OrderItem item = new OrderItem();


	            item.setOrderItemId(
	                    rs.getInt("OrderItemID")
	            );


	            item.setOrderId(
	                    rs.getInt("OrderID")
	            );
	            item.setMenuId(
	                    rs.getInt("MenuID")
	            );
	            item.setQuantity(
	                    rs.getInt("Quantity")
	            );
	            item.setItemTotal(
	                    rs.getDouble("ItemTotal")
	            );
	            list.add(item);
	        }
	    } catch (SQLException e) {

	        e.printStackTrace();

	    }


	    return list;
	}
	// =========================
	// DELETE ORDER ITEM
	// =========================
	@Override
	public void deleteOrderItem(int orderItemId) {

		String sql = "DELETE FROM orderitem WHERE OrderItemID=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderItemId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addOrderItem(int orderId, int menuId, int quantity, double itemTotal) {
	    String sql =
	            "INSERT INTO orderitem (OrderID, MenuID, Quantity, ItemTotal) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, orderId);
	        ps.setInt(2, menuId);
	        ps.setInt(3, quantity);
	        ps.setDouble(4, itemTotal);
	        ps.executeUpdate();
	    } catch (SQLException e) {

	        e.printStackTrace();

	    }
	}
	
}