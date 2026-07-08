<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.Menu"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Menu</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap"
	rel="stylesheet">
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

/* HERO */
.hero {
	margin: 20px;
	border-radius: 20px;
	overflow: hidden;
	height: 300px;
	background: linear-gradient(rgba(255, 119, 0, 0.35),
		rgba(255, 119, 0, 0.35)), url("images/New folder/banner.png");
	background-size: cover;
	background-position: center;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: flex-start;
	padding: 50px 40px;
	color: #fff;
}

.hero h1 {
	font-size: 48px;
	font-weight: 700;
	margin-bottom: 10px;
}

.hero p {
	font-size: 18px;
	margin-bottom: 20px;
	max-width: 500px;
}

.search-bar {
	display: flex;
	width: 100%;
	max-width: 500px;
	background: #fff;
	border-radius: 40px;
	overflow: hidden;
}

.search-bar input {
	flex: 1;
	padding: 12px 18px;
	border: none;
	outline: none;
	font-size: 16px;
}

.search-bar button {
	background: #ff7a00;
	color: #fff;
	border: none;
	padding: 0 20px;
	cursor: pointer;
	font-size: 16px;
}
/* MENU GRID */
.menu-title {
	text-align: center;
	font-size: 32px;
	font-weight: 700;
	color: #ff7a00;
	margin-bottom: 30px;
}

.menu-section {
	margin: 60px auto; /* adds space above and below */
	width: 95%; /* keeps cards slightly inset from page edges */
}

.menu-grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 40px; /* increases space between cards */
	justify-content: center; /* centers cards horizontally */
	padding: 0 30px; /* adds left-right margin inside section */
}

.menu-card {
	background: white;
	border-radius: 20px;
	overflow: hidden;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
	transition: transform .3s ease, box-shadow .3s ease;
	position: relative;
}

.menu-card:hover {
	transform: translateY(-8px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, .15);
}

@media ( min-width : 1200px) {
	.menu-section {
		width: 90%; /* more breathing room */
	}
	.menu-grid {
		gap: 50px; /* extra space between cards */
	}
}

/* IMAGE AREA */
.menu-img {
	width: 100%;
	height: 230px; /* fixed height for all cards */
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	background: #fff;
}

.menu-img img {
	width: 100%;
	height: 100%;
	object-fit: cover; /* ✅ fills card evenly */
	object-position: center; /* centers image focus */
	transition: transform 0.4s ease;
}

.menu-card:hover img {
	transform: scale(1.05);
}

/* CONTENT AREA */
.menu-content {
	padding: 20px;
	text-align: left;
}

.menu-content h3 {
	font-size: 20px;
	font-weight: 600;
	color: #333;
	margin-bottom: 6px;
}

.menu-content p {
	font-size: 14px;
	color: #666;
	margin-bottom: 10px;
}

.price-rating {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 12px;
}

.price {
	font-weight: 600;
	color: #ff7a00;
	font-size: 18px;
}

.rating {
	color: #28a745;
	font-weight: 600;
}

/* BUTTON */
.add-btn {
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

.add-btn:hover {
	background: #e06600;
}

/* RESPONSIVE */
@media ( max-width : 991px) {
	.menu-grid {
		grid-template-columns: repeat(2, 1fr);
	}
	.menu-img {
		height: 200px;
	}
}

@media ( max-width : 600px) {
	.menu-grid {
		grid-template-columns: 1fr;
	}
	.menu-img {
		height: 180px;
	}
}

/* FEATURES */
.features {
	margin: 50px 20px;
	background: #ff7a00;
	border-radius: 15px;
	padding: 25px;
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	color: white;
}

.feature {
	text-align: center;
}

.feature i {
	font-size: 28px;
	margin-right: 10px;
}

.feature span {
	font-size: 20px;
}

@media ( max-width :991px) {
	.menu-grid {
		grid-template-columns: repeat(2, 1fr);
	}
	.features {
		grid-template-columns: repeat(2, 1fr);
		gap: 20px;
	}
}

@media ( max-width :768px) {
	.menu-grid {
		grid-template-columns: 1fr;
	}
	.features {
		grid-template-columns: 1fr;
	}
}
</style>
</head>
<body>
	<%
    List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
    String pageTitle = (String) request.getAttribute("pageTitle");
%>

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

	<!-- HERO -->
	<section class="hero">
		
	</section>

	<!-- MENU ITEMS -->
	<section class="menu-section">
		<h2 class="menu-title">
			<%= pageTitle != null ? pageTitle : "Our Delicious Menu" %>
		</h2>

		<div class="menu-grid">
			<%
        if (menuItems != null && !menuItems.isEmpty()) {
            for (Menu m : menuItems) {
        %>
			<div class="menu-card">
				<div class="menu-img">
					<img
						src="<%= m.getImagePath() != null ? m.getImagePath() : "images/default-food.jpg" %>"
						alt="<%= m.getItemName() %>">
				</div>
				<div class="menu-content">
					<h3><%= m.getItemName() %></h3>
					<p><%= m.getDescription() %></p>
					<div class="price-rating">
						<div class="price">
							₹<%= m.getPrice() %></div>
						<div class="rating"><%= m.isAvailable() ? "⭐ Available" : "❌ Not Available" %></div>
					</div>
					<!-- ✅ Corrected Add to Cart form -->
					<form action="addToCart" method="POST">
						<input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
						<input type="hidden" name="itemName"
							value="<%= m.getItemName() %>"> <input type="hidden"
							name="price" value="<%= m.getPrice() %>"> <input
							type="hidden" name="imagePath" value="<%= m.getImagePath() %>">

						<!-- Pass restaurantId -->
						<input type="hidden" name="restaurantId"
							value="<%= session.getAttribute("restaurantId") %>">

						<button type="submit" class="add-btn">Add to Cart</button>
					</form>
				</div>
			</div>
			<%
            }
        } else {
        %>
			<p style="text-align: center; font-size: 18px; color: #555;">No
				menu items available.</p>
			<%
        }
        %>
		</div>
	</section>

	<!-- FEATURES -->
	<section class="features">
		<div class="feature">
			<i class="fas fa-motorcycle"></i><span> Fast Delivery</span>
		</div>
		<div class="feature">
			<i class="fas fa-award"></i><span> Best Quality</span>
		</div>
		<div class="feature">
			<i class="fas fa-tags"></i><span> Great Offers</span>
		</div>
		<div class="feature">
			<i class="fas fa-headset"></i><span> 24/7 Support</span>
		</div>
	</section>

</body>
</html>