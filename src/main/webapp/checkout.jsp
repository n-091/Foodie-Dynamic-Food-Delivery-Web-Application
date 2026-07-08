<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.CartItem"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Checkout</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Poppins', sans-serif;
}

body {
	background: #f8f8f8;
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

/* OFFER BANNER */
.offer-banner {
	margin: 20px;
	background: #fff3e0;
	border: 1px dashed #F97316;
	border-radius: 12px;
	padding: 15px;
	text-align: center;
	animation: fadeUp 1s ease;
}

.offer-banner i {
	color: #F97316;
	margin-right: 8px;
}

.offer-banner span {
	font-weight: 600;
	color: #e06600;
}

/* CHECKOUT GRID */
.checkout-section {
	margin: 30px;
	animation: fadeUp 1s ease;
}

.checkout-grid {
	display: grid;
	grid-template-columns: 2fr 1fr;
	gap: 25px;
}

.form-card {
	background: white;
	border-radius: 15px;
	padding: 20px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, .08);
	margin-bottom: 20px;
}

.form-card h2 {
	font-size: 22px;
	margin-bottom: 15px;
	color: #333;
}

.form-card label {
	display: block;
	margin-bottom: 8px;
	font-weight: 500;
}

.form-card input, .form-card select {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 8px;
	margin-bottom: 15px;
	transition: .3s;
}

.form-card input:focus {
	border-color: #F97316;
	outline: none;
}

.checkout-btn {
	width: 100%;
	padding: 12px;
	background: #F97316;
	color: #fff;
	border: none;
	border-radius: 10px;
	font-size: 16px;
	font-weight: 600;
	cursor: pointer;
	transition: .3s;
}

.checkout-btn:hover {
	background: #e06600;
	transform: scale(1.05);
}

/* SUMMARY */
.summary-card {
	background: white;
	border-radius: 15px;
	padding: 20px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, .08);
}

.summary-card h2 {
	font-size: 22px;
	margin-bottom: 15px;
	color: #333;
}

.summary-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 10px;
}

.summary-row.total {
	font-weight: 700;
	font-size: 18px;
	color: #F97316;
}

/* ANIMATIONS */
@
keyframes fadeUp {
	from {opacity: 0;
	transform: translateY(20px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
@media ( max-width :991px) {
	.checkout-grid {
		grid-template-columns: 1fr;
	}
	.navbar ul {
		flex-direction: column;
		gap: 10px;
	}
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

	<!-- Offer Banner -->
	<div class="offer-banner">
		<i class="fas fa-gift"></i> Use code <span>FOODIE50</span> and save
		₹50 on orders above ₹299.
	</div>

	<section class="checkout-section">
		<div class="checkout-grid">

			<!-- Checkout Form -->
			<div>
				<form action="${pageContext.request.contextPath}/checkout"
					method="post">

					<%
            Object restIdObj = session.getAttribute("restaurantId");
            String restIdVal = (restIdObj != null) ? restIdObj.toString() : "";
        %>

					<input type="hidden" name="restaurantId"
						value="${sessionScope.restaurantId}">

					<div class="form-card">
						<h2>Delivery Address</h2>

						<label>Full Name</label> <input type="text" name="name" required>

						<label>Phone Number</label> <input type="text" name="phone"
							required> <label>Delivery Address</label> <input
							type="text" name="address" required>
					</div>

					<div class="form-card">
						<h2>Payment Method</h2>

						<select name="paymentMethod" required>
							<option value="COD">Cash on Delivery</option>
							<option value="UPI">UPI</option>
							<option value="Card">Credit / Debit Card</option>
						</select>

						<button type="submit" class="checkout-btn">
							<i class="fas fa-check-circle"></i> Place Order
						</button>
					</div>

				</form>
			</div>
			<!-- Delivery Information -->
			<div class="summary-card">
				<h2>
					<i class="fas fa-truck"></i> Delivery Information
				</h2>
				<p>
					<strong>Estimated Delivery:</strong> 30 - 40 Minutes
				</p>
				<br>
				<p>
					<i class="fas fa-clock"></i> Orders are prepared fresh after
					confirmation.
				</p>
				<br>
				<p>
					<i class="fas fa-map-marker-alt"></i> Delivery partner will contact
					you before arrival.
				</p>
				<br>
				<p>
					<i class="fas fa-gift"></i> Use coupon <strong>FOODIE50</strong> on
					eligible orders.
				</p>
				<hr style="margin: 15px 0;">
				<p style="text-align: center; color: #F97316; font-weight: bold;">
					🍴 Delicious food is on its way!</p>
			</div>
		</div>
	</section>
</body>
</html>