<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Success - Foodie</title>

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
}

.logo {
	font-size: 32px;
	font-weight: 700;
	color: #E67E22;
	letter-spacing: 0.5px;
}

.nav-links {
	display: flex;
	gap: 40px;
}

.nav-links a {
	text-decoration: none;
	color: #2C3E50;
	font-weight: 500;
	font-size: 18px;
	padding: 8px 12px;
	border-radius: 6px;
	transition: .3s;
}

.nav-links a:hover,
.nav-links a.active {
	color: #E67E22;
	font-weight: 600;
	border-bottom: 2px solid #E67E22;
}


/* SUCCESS CARD */
.success-card {
	max-width: 600px;
	margin: 60px auto;
	background: #fff;
	border-radius: 15px;
	padding: 30px;
	text-align: center;
	box-shadow: 0 4px 15px rgba(0,0,0,.1);
	animation: fadeUp 1s ease;
}

.success-card h1 {
	color: #F97316;
	margin-bottom: 20px;
}

.success-card i {
	color: #28a745;
	margin-right: 10px;
}

.order-details {
	text-align: left;
	margin-top: 20px;
}

.order-details div {
	display: flex;
	justify-content: space-between;
	margin: 10px 0;
	font-size: 16px;
}

.btn-group {
	margin-top: 25px;
	display: flex;
	justify-content: center;
}

.btn-group button {
	padding: 12px 25px;
	background: #F97316;
	color: white;
	border: none;
	border-radius: 8px;
	font-size: 16px;
	font-weight: 600;
	cursor: pointer;
	transition: .3s;
}

.btn-group button:hover {
	background: #e06600;
	transform: scale(1.05);
}


/* ANIMATION */
@keyframes fadeUp {
	from {
		opacity:0;
		transform:translateY(20px);
	}
	to {
		opacity:1;
		transform:translateY(0);
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

		<a href="home" class="active">Home</a>
		<a href="restaurants">Restaurants</a>
		<a href="cart">Cart</a>
		<a href="orders">My Orders</a>
		<a href="profile.jsp">Profile</a>
		<a href="login">Login</a>

	</div>

</nav>



<!-- SUCCESS CARD -->

<div class="success-card">


<h1>
<i class="fas fa-check-circle"></i>
Order Placed Successfully!
</h1>


<p>
Thank you for ordering with Foodie.
</p>


<div class="order-details">


<%
Object orderId = session.getAttribute("orderId");
Object total = session.getAttribute("total");

if(orderId == null)
	orderId = "N/A";

if(total == null)
	total = "0";
%>


<div>
<span>Order ID:</span>
<span>#<%=orderId%></span>
</div>


<div>
<span>Total Amount:</span>
<span>₹<%=total%></span>
</div>


<div>
<span>Status:</span>
<span>Confirmed</span>
</div>


<div>
<span>Estimated Delivery:</span>
<span>30–40 mins</span>
</div>


</div>



<div class="btn-group">

<form action="home" method="get">

<button type="submit">
Back to Home
</button>

</form>

</div>


</div>


</body>
</html>