<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.Restaurant"%>
<%@ page import="com.designpattern.model.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.Event"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Home - Delicious Food Delivered Fast</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="css/home.css">

<style>
/* GLOBAL */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Poppins', sans-serif;
}

:root {
	--primary: #E67E22;
	--primary-dark: #D96A0C;
	--cream: #FAF7F2;
	--white: #fff;
	--text: #2C3E50;
}

body {
	background: var(--cream);
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
	color: var(--primary);
}

.nav-links {
	display: flex;
	gap: 30px;
}

.nav-links a {
	text-decoration: none;
	color: var(--text);
	font-weight: 500;
	font-size: 18px;
	transition: .3s;
	padding: 6px 10px;
	border-radius: 6px;
}

.nav-links a:hover, .nav-links a.active {
	color: var(--primary);
	font-weight: 600;
	border-bottom: 2px solid var(--primary);
}

/* HERO */
.hero {
	margin: 0 auto;
	width: 95%;
	border-radius: 20px;
	overflow: hidden;
	height: 85vh;
	position: relative;
	display: flex;
	align-items: center;
	justify-content: center;
	text-align: center;
	background: #000;
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
}

.hero-content {
	position: relative;
	z-index: 2;
	color: white;
	max-width: 800px;
}

.hero-content h1 {
	font-size: 60px;
	line-height: 1.2;
}

.hero-content span {
	color: var(--primary);
}

.hero-content p {
	margin-top: 20px;
	font-size: 18px;
	color: #f0f0f0;
}

.hero-btn {
	margin-top: 30px;
}

.btn {
	padding: 15px 40px;
	border: none;
	border-radius: 50px;
	background: var(--primary);
	color: white;
	font-size: 18px;
	font-weight: 600;
	cursor: pointer;
	transition: .3s;
	text-decoration: none;
}

.btn:hover {
	transform: scale(1.05);
	background: var(--primary-dark);
}

/* COMMON SECTION STYLES */
.restaurants {
	width: 95%;
	margin: 0 auto;
	padding: 60px 0 0 0;
}

.section-title {
	text-align: center;
	font-size: 28px;
	color: var(--primary);
	margin-bottom: 20px;
}

/* CATEGORY CARDS */
.category-container {
	display: grid;
	grid-template-columns: repeat(4, 1fr); /* 3 cards per row */
	gap: 35px;
	width: 100%;
	padding: 20px 0;
}

.category-card {
	background: #fff;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	transition: transform 0.3s ease;
	cursor: pointer;
}

.category-card:hover {
	transform: scale(1.03);
}

.category-card img {
	width: 100%;
	height: 250px;
	object-fit: cover;
	border-bottom: 1px solid #eee;
}

.category-info {
	text-align: center;
	padding: 10px;
}

.category-info h3 {
	color: var(--text);
	font-size: 18px;
	font-weight: 600;
}

/* POPULAR RESTAURANTS */
.popular-restaurant-container {
	display: grid;
	grid-template-columns: repeat(3, 1fr); /* 3 cards per row */
	gap: 35px;
	width: 100%;
	padding: 20px 0;
}

.popular-restaurant-card {
	background: #fff;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	cursor: pointer;
}

.popular-restaurant-card:hover {
	transform: scale(1.03);
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.popular-restaurant-card img {
	width: 100%;
	height: 190px;
	object-fit: cover;
	border-bottom: 1px solid #eee;
}

.popular-restaurant-info {
	padding: 15px;
	text-align: left;
	display: flex;
	flex-direction: column;
	gap: 6px;
}

.popular-restaurant-info h3 {
	font-size: 18px;
	font-weight: 700;
	color: var(--text);
}

.popular-restaurant-info p {
	font-size: 15px;
	color: #555;
	line-height: 1.4;
}

.popular-restaurant-info .rating {
	display: flex;
	align-items: center;
	gap: 6px;
	font-size: 15px;
	color: #27ae60;
	font-weight: 600;
}

/* RESPONSIVE */
@media ( max-width : 991px) {
	.category-container, .popular-restaurant-container {
		grid-template-columns: repeat(2, 1fr);
	}
}

@media ( max-width : 600px) {
	.category-container, .popular-restaurant-container {
		grid-template-columns: 1fr;
	}
}

/* ABOUT */
.about {
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 40px;
	width: 95%;
	margin: 60px auto;
}

.about img {
	width: 400px;
	border-radius: 20px;
}

.about-text {
	max-width: 500px;
}

.about-text h2 {
	color: var(--primary);
	margin-bottom: 15px;
}

.about-text p {
	color: #555;
	margin-bottom: 20px;
}

.about-text button {
	padding: 12px 25px;
	background: var(--primary);
	color: white;
	border: none;
	border-radius: 20px;
	cursor: pointer;
}

/* EVENTS */
.events {
	width: 95%;
	margin: 60px auto;
	text-align: center;
}

.event-grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 25px;
	margin-bottom: 20px;
}

.event-card {
	background: white;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
}

.event-card img {
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.event-card h3 {
	padding: 10px;
	color: var(--primary);
}

.event-card p {
	padding: 0 10px 15px;
	color: #555;
}

/* NEWSLETTER */
.newsletter {
	text-align: center;
	padding: 40px;
	background: white;
	margin: 60px auto;
	width: 95%;
	border-radius: 20px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .08);
}

.newsletter h2 {
	color: var(--primary);
	margin-bottom: 20px;
}

.newsletter input {
	padding: 12px;
	width: 300px;
	border: 1px solid #ccc;
	border-radius: 20px;
	margin-right: 10px;
}

.newsletter button {
	padding: 12px 25px;
	background: var(--primary);
	color: white;
	border: none;
	border-radius: 20px;
	cursor: pointer;
}

/* FOOTER */
.footer {
	background: #222;
	color: white;
	text-align: center;
	padding: 30px;
	margin-top: 60px;
}

/* RESPONSIVE */
@media ( max-width : 991px) {
	.restaurant-container {
		grid-template-columns: repeat(2, 1fr);
	}
	.about {
		flex-direction: column;
		text-align: center;
	}
	.event-grid {
		grid-template-columns: 1fr;
	}
}

#toast-container {
	position: fixed;
	bottom: 20px; /* move to bottom */
	left: 50%; /* center horizontally */
	transform: translateX(-50%);
	z-index: 9999;
}

.toast {
	background-color: #28a745; /* success green */
	color: #fff;
	padding: 14px 22px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.25);
	font-weight: 500;
	margin-top: 10px;
	opacity: 0;
	transform: translateY(20px); /* slide up */
	transition: all 0.4s ease;
}

.toast.show {
	opacity: 1;
	transform: translateY(0);
}

.toast.error {
	background-color: #dc3545; /* error red */
}
</style>
<!-- ✅ Correct closing tag -->
</head>
<body>
	<div id="toast-container"></div>

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
		<video autoplay muted loop playsinline class="hero-video">
			<source src="images/home-page.mp4" type="video/mp4">
		</video>
		<div class="overlay"></div>
		<div class="hero-content">
			<h1>
				Delicious Food <br> Delivered <span>Fast</span>
			</h1>
			<p>Discover restaurants near you and order your favorite meals
				with just a few clicks.</p>

			<div class="hero-btn">
				<a href="${pageContext.request.contextPath}/restaurants" class="btn">🍴
					Order Now</a>
			</div>



		</div>
	</section>

	<!-- BROWSE BY CATEGORY -->
	<section class="restaurants">
		<h2 class="section-title">Browse by Category</h2>
		<div class="category-container">
			<%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
            for (Category c : categories) {
        %>
			<div class="category-card"
				onclick="window.location.href='menu?category=<%=c.getName()%>'">
				<img src="<%=c.getImagePath()%>" alt="<%=c.getName()%>">
				<div class="category-info">
					<h3><%=c.getName()%></h3>
				</div>
			</div>
			<%
            }
        } else {
        %>
			<p style="text-align: center; font-size: 18px; color: #555;">⚠️
				No categories available</p>
			<%
        }
        %>
		</div>
	</section>

	<!-- POPULAR RESTAURANTS -->
	<section class="restaurants">
		<h2 class="section-title">Popular Restaurants</h2>
		<div class="popular-restaurant-container">
			<%
        List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
        if (restaurants != null && !restaurants.isEmpty()) {
            int count = 0;
            for (Restaurant r : restaurants) {
                if (count >= 8) break;
                String imageFile = (r.getImagePath() != null && !r.getImagePath().isEmpty())
                                   ? r.getImagePath()
                                   : "images/default-food.jpg";
        %>
			<div class="popular-restaurant-card"
				onclick="window.location.href='menu?restaurantId=<%=r.getRestaurantId()%>'">
				<img src="<%=request.getContextPath()%>/<%=imageFile%>"
					alt="<%=r.getName()%>">
				<div class="popular-restaurant-info">
					<h3><%=r.getName()%></h3>
					<p><%=r.getCuisineType()%></p>
					<p class="rating">
						⭐
						<%=r.getRating()%></p>
					<p><%=r.getDeliveryTime()%>
						min Delivery
					</p>
				</div>
			</div>
			<%
            count++;
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

	<!-- ABOUT FOODIE -->
	<section class="about">
		<img src="images/about/about-foodie.png" alt="About Foodie">
		<div class="about-text">
			<h2>Welcome to Foodie</h2>
			<p>Foodie connects you with the best restaurants nearby. Whether
				you crave spicy biryani, cheesy pizza, or sweet desserts, we deliver
				happiness straight to your doorstep.</p>

		</div>
	</section>


	<!-- Events -->
	<section class="events">
		<h2>Upcoming Events</h2>
		<div class="event-grid">
			<%
			List<Event> events = (List<Event>) request.getAttribute("events");
			if (events != null && !events.isEmpty()) {
				for (Event e : events) {
			%>
			<div class="event-card"
				onclick="window.location.href='restaurants?id=<%=e.getId()%>'">
				<img src="images/<%=e.getImagePath()%>" alt="<%=e.getTitle()%>">
				<h3><%=e.getTitle()%></h3>
				<p><%=e.getDescription()%></p>
			</div>
			<%
			}
			} else {
			%>
			<p>No events available</p>
			<%
			}
			%>
		</div>
	</section>


	<!-- NEWSLETTER -->
	<section class="newsletter">
		<h2>Get updates about new dishes and upcoming events</h2>
		<input type="email" placeholder="Enter your email">
		<button>Subscribe</button>
	</section>

	<!-- FOOTER -->
<footer class="footer">
    <h3>Foodie ❤️</h3>
    <p>Made With Love | Instant Food Delivery</p>
</footer>

<div id="toast-container"></div>

<script>
    function showToast(message, type = "success") {
        var toastContainer = document.getElementById("toast-container");
        var toast = document.createElement("div");
        toast.className = "toast " + type;
        toast.innerText = message;
        toastContainer.appendChild(toast);

        // Show animation
        setTimeout(() => toast.classList.add("show"), 100);

        // Hide after 3 seconds
        setTimeout(() => {
            toast.classList.remove("show");
            setTimeout(() => toast.remove(), 500);
        }, 3000);
    }

    // ✅ Only check for error in URL
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("error")) {
        showToast(urlParams.get("error"), "error");
    }
</script>

<% String msg = request.getParameter("message"); %>
<% if ("ProfileDeleted".equals(msg)) { %>
    <script>
        showToast("✅ Profile successfully deleted!", "success");
    </script>
<% } %>

</body>
</html>