<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.designpattern.model.User"%>
<%
User user = (User) session.getAttribute("currentUser");
String profileImage = "https://randomuser.me/api/portraits/women/44.jpg"; // fixed profile picture
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | Profile</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap"
	rel="stylesheet">

<style>
body {
	background: #f8f8f8;
	font-family: 'Poppins', sans-serif;
	margin: 0;
	padding: 0;
	color: #333;
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

.nav-links a, .nav-links button {
	text-decoration: none;
	color: #2C3E50;
	font-weight: 500;
	font-size: 18px;
	transition: 0.3s ease;
	padding: 8px 12px;
	border-radius: 6px;
	background: none;
	border: none;
	cursor: pointer;
}

.nav-links a:hover, .nav-links a.active {
	color: #E67E22;
	font-weight: 600;
	border-bottom: 2px solid #E67E22;
}
/* Profile Layout */
.profile-wrapper {
	max-width: 900px;
	margin: 60px auto;
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 30px;
}
/* Profile Card */
.profile-card {
	background: white;
	border-radius: 20px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
	padding: 30px;
	text-align: center;
}

.profile-card img {
	width: 120px;
	height: 120px;
	border-radius: 50%;
	border: 4px solid #E67E22;
	object-fit: cover;
	margin-bottom: 15px;
}

.profile-card h2 {
	font-size: 22px;
	font-weight: 700;
	color: #333;
}

.profile-card p {
	font-size: 14px;
	color: #666;
}
/* Details Form */
.profile-details {
	background: white;
	border-radius: 20px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
	padding: 30px;
	display: flex;
	flex-direction: column;
	gap: 20px;
}

.profile-details label {
	font-weight: 600;
	color: #333;
}

.profile-details input {
	padding: 12px;
	border: 1px solid #ddd;
	border-radius: 10px;
	font-size: 15px;
	transition: .3s;
}

.profile-details input:focus {
	border-color: #E67E22;
	outline: none;
	box-shadow: 0 0 5px rgba(230, 126, 34, 0.4);
}
/* Buttons */
.update-btn {
	padding: 14px;
	background: #E67E22;
	color: white;
	border: none;
	border-radius: 12px;
	font-size: 16px;
	font-weight: 600;
	cursor: pointer;
	transition: .3s;
}

.update-btn:hover {
	background: #d35400;
	transform: scale(1.05);
}

.logout-btn {
	background: #c0392b;
}
/* Messages */
.message {
	text-align: center;
	font-weight: bold;
	margin-bottom: 20px;
}

.success {
	color: green;
}

.error {
	color: red;
}
/* Modal */
.modal {
	display: none;
	position: fixed;
	z-index: 999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6);
	justify-content: center;
	align-items: center;
}

.modal-content {
	background: white;
	padding: 30px;
	border-radius: 12px;
	width: 350px;
	text-align: center;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
	animation: fadeIn 0.3s ease;
}

.modal-content h3 {
	margin-bottom: 20px;
	color: #E67E22;
}

.modal-content label {
	font-weight: 600;
	margin-top: 10px;
	display: block;
}

.modal-content input {
	width: 100%;
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #ddd;
	border-radius: 8px;
}

@
keyframes fadeIn {
	from {opacity: 0;
	transform: scale(0.9);
}

to {
	opacity: 1;
	transform: scale(1);
}
}
</style>
</head>

<body>
	<!-- NAVBAR -->
	<nav class="navbar">
		<span class="logo">Foodie</span>
		<div class="nav-links">
			<a href="home" class="active">Home</a> <a href="restaurants">Restaurants</a>
			<a href="cart">Cart</a> <a href="orders">My Orders</a> <a
				href="profile.jsp">Profile</a>
			<!-- Logout opens modal -->
			<button
				onclick="document.getElementById('logoutModal').style.display='flex'"
				class="logout-btn update-btn">Logout</button>
		</div>
	</nav>

	<!-- Profile Section -->
	<div class="profile-wrapper">
		<!-- Profile Card -->
		<div class="profile-card">
			<img src="<%=profileImage%>" alt="Profile Picture">
			<h2><%=(user != null ? user.getUserName() : "Guest User")%></h2>
			<p><%=(user != null ? user.getEmail() : "")%></p>
		</div>

		<div>
			<!-- Messages -->
			<% if (request.getAttribute("message") != null) { %>
			<p class="message success"><%=request.getAttribute("message")%></p>
			<% } %>
			<% if (request.getAttribute("error") != null) { %>
			<p class="message error"><%=request.getAttribute("error")%></p>
			<% } %>

			<!-- Update Form -->
			<form action="updateProfile" method="post" class="profile-details">
				<input type="hidden" name="action" value="update"> <input
					type="hidden" name="userId"
					value="<%=(user != null ? user.getUserId() : -1)%>"> <label>Name</label>
				<input type="text" name="name"
					value="<%=(user != null ? user.getUserName() : "")%>"> <label>Email</label>
				<input type="email" name="email"
					value="<%=(user != null ? user.getEmail() : "")%>"> <label>Phone</label>
				<input type="text" name="phone"
					value="<%=(user != null ? user.getPhoneNo() : "")%>"> <label>Address</label>
				<input type="text" name="address"
					value="<%=(user != null ? user.getAddress() : "")%>">
				<button type="submit" class="update-btn">Update Profile</button>
			</form>

			<!-- Change Password Modal Trigger -->
			<button
				onclick="document.getElementById('passwordModal').style.display='flex'"
				class="update-btn" style="margin-top: 20px;">Change
				Password</button>

			<!-- Delete Trigger -->
			<button type="button" class="update-btn"
				style="background: #c0392b; margin-top: 20px;"
				onclick="document.getElementById('deleteModal').style.display='flex'">
				Delete Profile</button>
		</div>
	</div>

	<!-- Delete Confirmation Modal -->
	<div id="deleteModal" class="modal">
		<div class="modal-content">
			<span
				style="float: right; font-size: 24px; cursor: pointer; color: #c0392b;"
				onclick="document.getElementById('deleteModal').style.display='none'">&times;</span>
			<h3 style="color: #c0392b;">Confirm Delete</h3>
			<p>Are you sure you want to delete your profile? This action
				cannot be undone.</p>
			<div
				style="margin-top: 20px; display: flex; gap: 10px; justify-content: center;">
				<!-- Actual delete form inside modal -->
				<form action="updateProfile" method="post">
					<input type="hidden" name="userId"
						value="<%=(user != null ? user.getUserId() : "")%>"> <input
						type="hidden" name="action" value="delete">
					<button type="submit" class="update-btn"
						style="background: #c0392b;">Yes, Delete</button>
				</form>
				<button type="button"
					onclick="document.getElementById('deleteModal').style.display='none'"
					style="padding: 12px; border: none; border-radius: 10px; background: #ccc; cursor: pointer;">
					Cancel</button>
			</div>
		</div>
	</div>

	<!-- Password Modal -->
	<div id="passwordModal" class="modal">
		<div class="modal-content">
			<span
				style="float: right; font-size: 24px; cursor: pointer; color: #c0392b;"
				onclick="document.getElementById('passwordModal').style.display='none'">&times;</span>
			<h3>Change Password</h3>
			<form action="changePassword" method="post">
				<input type="hidden" name="userId"
					value="<%=(user != null ? user.getUserId() : -1)%>"> <label>Current
					Password</label> <input type="password" name="oldPassword" required>
				<label>New Password</label> <input type="password"
					name="newPassword" required> <label>Confirm
					Password</label> <input type="password" name="confirmPassword" required>
				<button type="submit" class="update-btn"
					style="width: 100%; margin-top: 15px;">Update Password</button>
			</form>
		</div>
	</div>

	<!-- Logout Modal -->
	<div id="logoutModal" class="modal">
		<div class="modal-content">
			<span
				style="float: right; font-size: 24px; cursor: pointer; color: #c0392b;"
				onclick="document.getElementById('logoutModal').style.display='none'">&times;</span>
			<h3 style="color: #c0392b;">Confirm Logout</h3>
			<p>Are you sure you want to logout?</p>
			<div
				style="margin-top: 20px; display: flex; gap: 10px; justify-content: center;">
				<a href="logout" class="update-btn" style="background: #c0392b;">Yes,
					Logout</a>
				<button type="button"
					onclick="document.getElementById('logoutModal').style.display='none'"
					style="padding: 12px; border: none; border-radius: 10px; background: #ccc; cursor: pointer;">
					Cancel</button>
			</div>
		</div>
	</div>
</body>
</html>
