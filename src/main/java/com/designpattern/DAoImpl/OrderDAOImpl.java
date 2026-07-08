package com.designpattern.DAoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.designpattern.dao.OrderDAO;
import com.designpattern.model.CartItem;
import com.designpattern.model.Order;
import com.designpattern.model.OrderItem;

public class OrderDAOImpl implements OrderDAO {

	private Connection conn;

	public OrderDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// CREATE ORDER
	@Override
	public int createOrder(Order order) {

		int orderId = 0;

		String sql="INSERT INTO orders(user_id,restaurant_id,total_amount,status,payment_method,order_date)"
				+" VALUES(?,?,?,?,?,CURRENT_TIMESTAMP)";

		try(PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

			ps.setInt(1,order.getUserId());
			ps.setInt(2,order.getRestaurantId());
			ps.setDouble(3,order.getTotalAmount());
			ps.setString(4,order.getStatus());
			ps.setString(5,order.getPaymentMethod());

			ps.executeUpdate();

			ResultSet rs=ps.getGeneratedKeys();

			if(rs.next()){
				orderId=rs.getInt(1);
			}

			System.out.println("ORDER CREATED ID : "+orderId);

		}catch(Exception e){
			e.printStackTrace();
		}

		return orderId;
	}


	// GET ORDER BY ID
	@Override
	public Order getOrderById(int orderId){

		Order order=null;

		String sql="SELECT * FROM orders WHERE order_id=?";

		try(PreparedStatement ps=conn.prepareStatement(sql)){

			ps.setInt(1,orderId);

			ResultSet rs=ps.executeQuery();

			if(rs.next()){

				order=new Order();

				order.setOrderId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("user_id"));
				order.setRestaurantId(rs.getInt("restaurant_id"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setPaymentMethod(rs.getString("payment_method"));

				order.setItems(getOrderItems(orderId));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return order;
	}


	// ALL ORDERS
	@Override
	public List<Order> getAllOrders(){

		List<Order> list=new ArrayList<>();

		String sql="SELECT * FROM orders ORDER BY order_id DESC";

		try(PreparedStatement ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery()){

			while(rs.next()){

				Order o=new Order();

				o.setOrderId(rs.getInt("order_id"));
				o.setUserId(rs.getInt("user_id"));
				o.setRestaurantId(rs.getInt("restaurant_id"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setStatus(rs.getString("status"));
				o.setPaymentMethod(rs.getString("payment_method"));

				o.setItems(getOrderItems(o.getOrderId()));

				list.add(o);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}



	// ORDERS BY USER
	@Override
	public List<Order> getOrdersByUser(int userId){

		List<Order> list=new ArrayList<>();

		String sql="SELECT * FROM orders WHERE user_id=? ORDER BY order_id DESC";

		try(PreparedStatement ps=conn.prepareStatement(sql)){

			ps.setInt(1,userId);

			ResultSet rs=ps.executeQuery();

			while(rs.next()){

				Order o=new Order();

				o.setOrderId(rs.getInt("order_id"));
				o.setUserId(rs.getInt("user_id"));
				o.setRestaurantId(rs.getInt("restaurant_id"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setStatus(rs.getString("status"));
				o.setPaymentMethod(rs.getString("payment_method"));

				o.setItems(getOrderItems(o.getOrderId()));

				list.add(o);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}



	// ADD ORDER ITEM
	@Override
	public void addOrderItem(OrderItem item){

		String sql="INSERT INTO orderitem(OrderID,MenuID,Quantity,ItemTotal)"
				+" VALUES(?,?,?,?)";

		try(PreparedStatement ps=conn.prepareStatement(sql)){

			ps.setInt(1,item.getOrderId());
			ps.setInt(2,item.getMenuId());
			ps.setInt(3,item.getQuantity());
			ps.setDouble(4,item.getItemTotal());

			ps.executeUpdate();

			System.out.println("Item Added : MenuID "
					+item.getMenuId()
					+" Qty "
					+item.getQuantity());

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	// GET ITEMS
	private List<String> getOrderItems(int orderId){

		List<String> items=new ArrayList<>();

		String sql="SELECT m.itemName FROM orderitem oi "
				+"JOIN menu m ON oi.MenuID=m.MenuID "
				+"WHERE oi.OrderID=?";

		try(PreparedStatement ps=conn.prepareStatement(sql)){

			ps.setInt(1,orderId);

			ResultSet rs=ps.executeQuery();

			while(rs.next()){
				items.add(rs.getString("itemName"));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return items;
	}



	// DELETE ORDER
	@Override
	public void deleteOrder(int orderId){

		try{

			PreparedStatement ps1=
			conn.prepareStatement("DELETE FROM orderitem WHERE OrderID=?");

			ps1.setInt(1,orderId);
			ps1.executeUpdate();


			PreparedStatement ps2=
			conn.prepareStatement("DELETE FROM orders WHERE order_id=?");

			ps2.setInt(1,orderId);
			ps2.executeUpdate();

			System.out.println("ORDER DELETED : "+orderId);

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	// PLACE ORDER
	@Override
	public int placeOrder(int userId,int cartId,int restaurantId,String paymentMethod){

		CartItemDAOImpl cartDAO=new CartItemDAOImpl(conn);
		OrderItemDAOImpl itemDAO=new OrderItemDAOImpl(conn);

		List<CartItem> items=cartDAO.getCartItemsByUser(userId);

		double subtotal=items.stream()
				.mapToDouble(i->i.getPrice()*i.getQuantity())
				.sum();

		double total=subtotal+40-(subtotal>=299?50:0);


		Order order=new Order();

		order.setUserId(userId);
		order.setRestaurantId(restaurantId);
		order.setTotalAmount(total);
		order.setStatus("Confirmed");
		order.setPaymentMethod(paymentMethod);


		int orderId=createOrder(order);


		if(orderId>0){

			for(CartItem c:items){

				OrderItem oi=new OrderItem();

				oi.setOrderId(orderId);
				oi.setMenuId(c.getMenuId());
				oi.setQuantity(c.getQuantity());
				oi.setItemTotal(c.getPrice()*c.getQuantity());

				itemDAO.addOrderItem(oi);
			}


			cartDAO.deleteItemsByCartId(cartId);

			System.out.println("CART DELETED");
			System.out.println("ORDER SUCCESS ID : "+orderId);

		}
		else{
			System.out.println("ORDER FAILED");
		}


		return orderId;
	}



	@Override
	public void updateOrderTotal(int orderId,double amount){

		try{

			PreparedStatement ps=
			conn.prepareStatement(
			"UPDATE orders SET total_amount=? WHERE order_id=?");

			ps.setDouble(1,amount);
			ps.setInt(2,orderId);
			ps.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public void updateStatus(int orderId,String status){

		try{

			PreparedStatement ps=
			conn.prepareStatement(
			"UPDATE orders SET status=? WHERE order_id=?");

			ps.setString(1,status);
			ps.setInt(2,orderId);

			ps.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public List<Order> getOrdersByRestaurant(int restaurantId){

		List<Order> list=new ArrayList<>();

		String sql="SELECT * FROM orders WHERE restaurant_id=?";

		try(PreparedStatement ps=conn.prepareStatement(sql)){

			ps.setInt(1,restaurantId);

			ResultSet rs=ps.executeQuery();

			while(rs.next()){

				Order o=new Order();

				o.setOrderId(rs.getInt("order_id"));
				o.setRestaurantId(rs.getInt("restaurant_id"));
				o.setUserId(rs.getInt("user_id"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setStatus(rs.getString("status"));

				list.add(o);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}

}