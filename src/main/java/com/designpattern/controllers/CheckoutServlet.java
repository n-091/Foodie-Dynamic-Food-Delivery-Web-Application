package com.designpattern.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.designpattern.DAoImpl.*;
import com.designpattern.model.*;
import com.designpattern.utility.DBConnection;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet{

	private CartDAOImpl cartDAO;
	private CartItemDAOImpl cartItemDAO;

	public void init() throws ServletException{
		try{
			Connection con=DBConnection.getConnection();
			cartDAO=new CartDAOImpl(con);
			cartItemDAO=new CartItemDAOImpl(con);
			System.out.println("CheckoutServlet Initialized");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{
		showCheckout(req,res);
	}

	protected void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{

		if(req.getParameter("paymentMethod")!=null)
			placeOrder(req,res);
		else
			showCheckout(req,res);
	}

	private void showCheckout(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{

		System.out.println("===== CHECKOUT PAGE OPENED =====");

		HttpSession session=req.getSession();

		Integer userId=(Integer)session.getAttribute("userId");

		System.out.println("User ID : "+userId);

		if(userId==null){
			res.sendRedirect("login");
			return;
		}

		Cart cart=cartDAO.getCartByUserId(userId);

		if(cart!=null){

			List<CartItem> items=
			cartItemDAO.getItemsByCartId(cart.getCartId());

			System.out.println("Cart ID : "+cart.getCartId());
			System.out.println("Cart Items : "+items.size());

			if(!items.isEmpty()){

				int restaurantId=items.get(0).getRestaurantId();

				session.setAttribute("restaurantId",restaurantId);

				System.out.println("Restaurant ID : "+restaurantId);
			}

			session.setAttribute("cartId",cart.getCartId());
			session.setAttribute("cartItems",items);
		}

		req.getRequestDispatcher("/checkout.jsp")
		.forward(req,res);
	}


	private void placeOrder(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{

		System.out.println("===== PLACE ORDER START =====");

		HttpSession session=req.getSession();

		Integer userId=(Integer)session.getAttribute("userId");
		Integer restaurantId=(Integer)session.getAttribute("restaurantId");
		Integer cartId=(Integer)session.getAttribute("cartId");

		String payment=req.getParameter("paymentMethod");

		System.out.println("User ID : "+userId);
		System.out.println("Restaurant ID : "+restaurantId);
		System.out.println("Cart ID : "+cartId);
		System.out.println("Payment : "+payment);

		try{

			Connection con=DBConnection.getConnection();

			CartItemDAOImpl cartDAO=new CartItemDAOImpl(con);
			OrderDAOImpl orderDAO=new OrderDAOImpl(con);
			OrderItemDAOImpl itemDAO=new OrderItemDAOImpl(con);

			List<CartItem> items=
			cartDAO.getItemsByCartId(cartId);

			System.out.println("Items Count : "+items.size());

			double total=0;

			for(CartItem c:items){

				total+=c.getPrice()*c.getQuantity();

				System.out.println(
				"Item Added : MenuID "
				+c.getMenuId()
				+" Qty "
				+c.getQuantity());
			}

			Order order=new Order();

			order.setUserId(userId);
			order.setRestaurantId(restaurantId);
			order.setPaymentMethod(payment);
			order.setStatus("Confirmed");
			order.setTotalAmount(total);

			int orderId=orderDAO.createOrder(order);

			System.out.println("Created Order ID : "+orderId);


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
				System.out.println("ORDER SUCCESS");

				session.setAttribute("orderId",orderId);
				session.setAttribute("total",total);

				res.sendRedirect(req.getContextPath()+"/order-success.jsp");

			}else{

				System.out.println("ORDER FAILED");

				req.setAttribute("errorMessage","Order Failed");

				req.getRequestDispatcher("/checkout.jsp")
				.forward(req,res);
			}

		}catch(Exception e){

			e.printStackTrace();

			req.setAttribute("errorMessage","Something went wrong");

			req.getRequestDispatcher("/checkout.jsp")
			.forward(req,res);
		}
	}
}