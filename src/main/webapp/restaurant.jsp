<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.Restaurant"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Restaurants</title>
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

/* Hero section */
.hero {
	margin: 20px auto 60px;
	border-radius: 20px;
	overflow: hidden;
	height: 380px;
	width: 95%;
	position: relative;
	display: flex;
	align-items: center;
	justify-content: flex-start;
	padding: 40px 60px;
	color: white;
	background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4));
}

.hero-video {
	position: absolute;
	top: 50%;
	left: 50%;
	min-width: 100%;
	min-height: 100%;
	transform: translate(-50%, -50%);
	object-fit: cover;
	z-index: 0;
	border-radius: 20px;
}

.overlay {
	position: absolute;
	inset: 0;
	background: rgba(0, 0, 0, 0.35);
	z-index: 1;
	border-radius: 20px;
}

.hero-content {
	position: relative;
	z-index: 2;
	max-width: 500px;
}

.hero-content h1 {
	font-size: 48px;
	margin-bottom: 15px;
	font-weight: 700;
	line-height: 1.2;
}

.hero-content p {
	font-size: 18px;
	margin-bottom: 20px;
	color: #f0f0f0;
}

.order-btn {
	background: #E67E22;
	color: white;
	padding: 14px 28px;
	border: none;
	border-radius: 40px;
	font-size: 16px;
	font-weight: 600;
	cursor: pointer;
	transition: background .3s ease;
}

.order-btn:hover {
	background: #cf5f17;
}
/* Section Title */
.restaurant-title {
	text-align: center;
	font-size: 32px;
	font-weight: 700;
	color: #ff7a00;
	margin-bottom: 30px;
}

/* Restaurant Section */
.restaurant-section {
	margin: 60px auto;
	width: 95%;
}

/* Restaurant Grid */
.restaurant-grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr); /* 3 per row desktop */
	gap: 40px;
	justify-content: center;
	padding: 0 30px;
}

/* Restaurant Card */
.restaurant-card {
	background: white;
	border-radius: 20px;
	overflow: hidden;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
	transition: transform .3s ease, box-shadow .3s ease;
	position: relative;
}

.restaurant-card:hover {
	transform: translateY(-8px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, .15);
}

/* Image Area */
.restaurant-img {
	width: 100%;
	height: 230px; /* same as menu cards */
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	background: #fff;
}

.restaurant-img img {
	width: 100%;
	height: 100%;
	object-fit: cover; /* fills evenly */
	object-position: center;
	transition: transform 0.4s ease;
}

.restaurant-card:hover img {
	transform: scale(1.05);
}

/* Content Area */
.restaurant-content {
	padding: 20px;
	text-align: left;
}

.restaurant-content h3 {
	font-size: 20px;
	font-weight: 600;
	color: #333;
	margin-bottom: 6px;
}

.restaurant-content p {
	font-size: 14px;
	color: #666;
	margin-bottom: 10px;
}

.price-rating {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 12px;
	font-size: 14px;
}

.price {
	font-weight: 600;
	color: #ff7a00;
}

.rating {
	color: #28a745;
	font-weight: 600;
}

.view-btn {
	width: 100%;
	padding: 10px;
	background: #ff7a00;
	color: white;
	border: none;
	border-radius: 10px;
	cursor: pointer;
	font-weight: 500;
	transition: .3s;
}

.view-btn:hover {
	background: #e06600;
}

/* Responsive */
@media ( max-width : 991px) {
	.restaurant-grid {
		grid-template-columns: repeat(2, 1fr);
	}
	.restaurant-img {
		height: 200px;
	}
}

@media ( max-width : 600px) {
	.restaurant-grid {
		grid-template-columns: 1fr;
	}
	.restaurant-img {
		height: 180px;
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
			<a href="cart">Cart</a><a href="orders">My Orders</a> <a
				href="profile.jsp">Profile</a> <a href="login">Login</a>
		</div>
	</nav>

	<section class="hero">
		<video autoplay muted loop playsinline class="hero-video">
			<source src="images/home-page.mp4" type="video/mp4">
		</video>
		<div class="overlay"></div>
		<div class="hero-content">
			<h1>
				Delicious Food,<br>Delivered Fast
			</h1>
			<p>Your favorite meals delivered hot and fresh to your doorstep.</p>
			<button class="order-btn"
				onclick="document.getElementById('restaurants').scrollIntoView({behavior:'smooth'});">
				ORDER NOW →</button>
		</div>
	</section>

	<!-- RESTAURANT SECTION -->
	<section class="restaurant-section" id="restaurants">
		<h2
			style="font-size: 32px; font-weight: 700; color: #ff7a00; margin-bottom: 30px;">
			Popular Restaurants</h2>

		<div class="restaurant-grid">
			<%
			List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
			if (restaurants != null && !restaurants.isEmpty()) {
				for (Restaurant r : restaurants) {
			%>
			<div class="restaurant-card">
				<div class="restaurant-img">
					<img src="<%=request.getContextPath()%>/<%=r.getImagePath()%>"
						alt="<%=r.getName()%>">
				</div>
				<div class="restaurant-content">
					<h3><%=r.getName()%></h3>
					<p><%=r.getCuisineType()%></p>
					<div class="price-rating">
						<div class="price">
							⭐
							<%=r.getRating()%></div>
						<div class="rating"><%=r.getDeliveryTime()%>
							min
						</div>
					</div>
					<!-- ✅ Proper form inside card -->
					<form action="menu" method="GET">
						<input type="hidden" name="restaurantId"
							value="<%=r.getRestaurantId()%>">
						<button type="submit" class="view-btn">View Menu</button>
					</form>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<p style="text-align: center; font-size: 18px; color: #555;">⚠️
				No restaurants available</p>
			<%
			}
			%>

		</div>
	</section>
</body>
</html>
