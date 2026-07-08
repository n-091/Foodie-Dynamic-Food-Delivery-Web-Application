<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.CartItem"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Cart</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
body {
	background: #f8f8f8;
	font-family: 'Poppins', sans-serif;
	margin: 0;
	padding: 0;
}
/* NAVBAR */
.navbar {
	height: 80px;
	background: white;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 80px;
	box-shadow: 0 2px 15px rgba(0, 0, 0, 0.08);
	position: sticky;
	top: 0;
	z-index: 200;
	font-family: 'Poppins', sans-serif;
}

.logo {
	font-size: 32px;
	font-weight: 700;
	color: #E67E22; /* orange logo */
	letter-spacing: 0.5px;
}

.nav-links {
	display: flex;
	gap: 40px; /* consistent spacing between links */
}

.nav-links a {
	text-decoration: none;
	color: #2C3E50; /* dark text */
	font-weight: 500;
	font-size: 18px; /* consistent font size */
	transition: 0.3s ease;
	padding: 8px 12px; /* consistent padding for clickable area */
	border-radius: 6px;
}

/* ✅ Hover and Active both same orange style */
.nav-links a:hover, .nav-links a.active {
	color: #E67E22;
	font-weight: 600;
	border-bottom: 2px solid #E67E22;
}

/* Cart Layout */
.cart-section {
	margin: 60px auto;
	width: 95%;
}

.cart-grid {
	display: grid;
	grid-template-columns: 2fr 1fr;
	gap: 30px;
}

/* Cart Items */
.cart-items {
	background: white;
	border-radius: 20px;
	padding: 25px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
}

.cart-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
	transition: transform .3s ease, box-shadow .3s ease;
}

.cart-item:hover {
	transform: translateY(-5px);
	box-shadow: 0 8px 20px rgba(0, 0, 0, .12);
}

.continue-btn {
	margin-top: 15px;
	padding: 10px 20px;
	background-color: #F97316; /* Foodie orange */
	color: #fff;
	font-weight: 600;
	border: none;
	border-radius: 8px;
	text-decoration: none;
	cursor: pointer;
	transition: background-color 0.3s ease;
	width: 150px; /* 👈 fixed width */
	text-align: center; /* centers text inside */
}

.continue-btn:hover {
	background-color: #e56b00;
}

.item-info {
	display: flex;
	align-items: center;
	gap: 20px;
}

.item-img {
	width: 120px;
	height: 120px;
	overflow: hidden;
	border-radius: 15px;
}

.item-img img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	transition: transform .4s ease;
}

.cart-item:hover img {
	transform: scale(1.05);
}

.item-details h3 {
	font-size: 18px;
	margin-bottom: 5px;
	color: #333;
}

.item-details p {
	color: #666;
	font-size: 14px;
}

/* Item Actions */
.item-actions {
	display: flex;
	align-items: center;
	gap: 10px;
	margin-left: auto; /* ✅ pushes buttons to right */
}

.qty-btn, .remove-btn {
	background: #ff7a00;
	color: white;
	border: none;
	padding: 8px 14px;
	border-radius: 8px;
	cursor: pointer;
	font-weight: 500;
	transition: .3s;
}

.qty-btn:hover {
	background: #e06700;
}

.remove-btn {
	background: #ccc;
	color: #333;
}

.remove-btn:hover {
	background: #999;
	color: white;
}

/* Summary Box */
.cart-summary {
	background: white;
	border-radius: 20px;
	padding: 25px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
}

.cart-summary h2 {
	font-size: 22px;
	margin-bottom: 20px;
}

.summary-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 12px;
}

.summary-row.total {
	font-weight: 700;
	font-size: 18px;
}

.checkout-btn, .continue-btn {
	width: 100%;
	padding: 14px;
	border: none;
	border-radius: 12px;
	font-size: 16px;
	font-weight: 600;
	cursor: pointer;
	transition: .3s;
	margin-top: 15px;
}

.checkout-btn {
	background: #ff7a00;
	color: white;
}

.checkout-btn:hover {
	background: #e06700;
	transform: scale(1.05);
}

.continue-btn {
	background: #2C3E50;
	color: white;
}

.continue-btn:hover {
	background: #1a252f;
	transform: scale(1.05);
}
</style>
</head>
<body>

	<!-- NAVBAR -->
	<nav class="navbar">
		<div>
			<span class="logo">Foodie</span>
		</div>
		<div class="nav-links">
			<a href="home" class="active">Home</a> <a href="restaurants">Restaurants</a>
			<a href="cart">Cart</a> <a href="orders">My Orders</a> <a
				href="profile.jsp">Profile</a> <a href="login">Login</a>
		</div>
	</nav>

	<section class="cart-section">
		<div class="cart-grid">

			<!-- Cart Items -->
			<div class="cart-items">
				<%
				List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
				Integer userId = (Integer) session.getAttribute("userId");
				if (cartItems == null || cartItems.isEmpty()) {
				%>
				<div class="empty-cart">
					<p>Your cart is empty.</p>
					<a href="<%=request.getContextPath()%>/restaurants"
						class="continue-btn">Continue Shopping</a>
				</div>
				<%
				} else {
				double subtotal = 0;
				for (CartItem item : cartItems) {
					double price = item.getPrice();
					double itemTotal = price * item.getQuantity();
					subtotal += itemTotal;
				%>
				<div class="cart-item">
					<div class="item-info">
						<div class="item-img">
							<img
								src="<%=request.getContextPath()%>/<%=item.getImagePath() != null ? item.getImagePath() : "images/default-food.jpg"%>"
								alt="<%=item.getItemName()%>">
						</div>
						<div class="item-details">
							<h3><%=item.getItemName()%></h3>
							<p>
								₹<%=price%></p>
						</div>
					</div>

					<!-- ✅ Unified Item Actions -->
					<div class="item-actions">
						<form action="cartItem" method="post">
							<input type="hidden"
								name="<%=(userId != null) ? "cartItemId" : "menuId"%>"
								value="<%=(userId != null) ? item.getCartItemId() : item.getMenuId()%>">
							<input type="hidden" name="action" value="increase">
							<button class="qty-btn">+</button>
						</form>

						<span><%=item.getQuantity()%></span>

						<form action="cartItem" method="post">
							<input type="hidden"
								name="<%=(userId != null) ? "cartItemId" : "menuId"%>"
								value="<%=(userId != null) ? item.getCartItemId() : item.getMenuId()%>">
							<input type="hidden" name="action" value="decrease">
							<button class="qty-btn">-</button>
						</form>

						<form action="cartItem" method="post">
							<input type="hidden"
								name="<%=(userId != null) ? "cartItemId" : "menuId"%>"
								value="<%=(userId != null) ? item.getCartItemId() : item.getMenuId()%>">
							<input type="hidden" name="action" value="remove">
							<button class="remove-btn">Remove</button>
						</form>
					</div>
				</div>
				<%
				} // loop ends

				double delivery = (subtotal > 0) ? 40 : 0;
				double discount = (subtotal >= 299) ? 50 : 0;
				double total = subtotal + delivery - discount;

				session.setAttribute("subtotal", subtotal);
				session.setAttribute("delivery", delivery);
				session.setAttribute("discount", discount);
				session.setAttribute("total", total);

				int orderId = 1000 + (int) (Math.random() * 9000);
				session.setAttribute("orderId", "FD" + orderId);
				%>
			</div>

			<!-- Cart Summary -->
			<div class="cart-summary">
				<h2>Order Summary</h2>
				<div class="summary-row">
					<span>Subtotal</span><span>₹<%=subtotal%></span>
				</div>
				<div class="summary-row">
					<span>Delivery Fee</span><span>₹<%=delivery%></span>
				</div>
				<div class="summary-row">
					<span>Discount</span><span>-₹<%=discount%></span>
				</div>
				<div class="summary-row total">
					<span>Total</span><span>₹<%=total%></span>
				</div>
				<form action="checkout" method="post">
					<button class="checkout-btn">Proceed to Checkout</button>
				</form>
				<form action="restaurants" method="get">
					<button class="continue-btn">Continue Shopping</button>
				</form>
			</div>
			<%
			} // closes else
			%>
		</div>
	</section>

</body>
</html>