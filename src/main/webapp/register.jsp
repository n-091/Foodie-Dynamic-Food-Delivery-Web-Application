<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Foodie | Register</title>
<style>
:root {
	--brand-orange: #e67e22;
	--input-bg: #fff5eb;
	--border-color: #fbd8b5;
	--text-dark: #333;
}

body {
	margin: 0;
	font-family: 'Segoe UI', sans-serif;
	background-color: #fffaf5;
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	position: relative;
}

/* Background Blob */
body::after {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: radial-gradient(circle at -5% 95%, var(--brand-orange) 180px,
		transparent 180px);
	z-index: 0;
}

.main-card {
	width: 90%;
	max-width: 900px;
	background: white;
	border-radius: 30px;
	display: flex;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	z-index: 2;
}

/* Floating Bubble Animation */
.bubble {
	position: absolute;
	background: rgba(230, 126, 34, 0.2);
	border-radius: 50%;
	pointer-events: none;
	z-index: 1;
	animation: fall linear infinite;
}

@
keyframes fall { 0% {
	transform: translateY(-10vh);
}

100
%
{
transform
:
translateY(
110vh
);
}
}

/* Marketing Side */
.marketing-side {
	flex: 1;
	padding: 50px;
	background: #fffdfb;
}

.brand-logo {
	color: var(--brand-orange);
	font-size: 2.5rem;
	font-weight: bold;
}

.marketing-side h1 {
	font-size: 2.5rem;
	line-height: 1.1;
	margin: 30px 0;
}

.feature-item {
	display: flex;
	align-items: center;
	gap: 15px;
	margin-bottom: 20px;
}

.icon {
	background: var(--input-bg);
	padding: 12px;
	border-radius: 50%;
	color: var(--brand-orange);
}

/* Form Side */
.form-side {
	flex: 1;
	padding: 50px;
	border-left: 1px solid #f0f0f0;
}

.form-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 15px;
}

input {
	width: 100%;
	padding: 15px;
	background: var(--input-bg);
	border: 1px solid var(--border-color);
	border-radius: 12px;
	box-sizing: border-box;
	margin-bottom: 15px;
	font-size: 1rem;
	color: var(--text-dark);
}

input:focus {
	outline: none;
	border-color: var(--brand-orange);
}

.btn-register {
	width: 100%;
	padding: 15px;
	background: var(--brand-orange);
	color: white;
	border: none;
	border-radius: 12px;
	font-weight: bold;
	cursor: pointer;
	transition: 0.3s;
}

.btn-register:hover {
	background: #d35400;
}

/* Inline error message */
.error-msg {
	color: #e74c3c;
	font-size: 0.9rem;
	margin-top: -10px;
	margin-bottom: 10px;
	display: block;
	text-align: left;
	animation: fadeIn 0.4s ease-in;
}

@
keyframes fadeIn {from { opacity:0;
	
}

to {
	opacity: 1;
}

}
@media ( max-width : 768px) {
	.main-card {
		flex-direction: column;
	}
	.marketing-side {
		display: none;
	}
}
</style>
</head>
<body>

	<div class="main-card">
		<div class="marketing-side">
			<div class="brand-logo">Foodie</div>
			<h1>
				Delicious food,<br>delivered fast
			</h1>
			<div class="feature-list">
				<div class="feature-item">
					<span class="icon">🛵</span> Fast Delivery
				</div>
				<div class="feature-item">
					<span class="icon">⭐</span> Best Restaurants
				</div>
				<div class="feature-item">
					<span class="icon">🏷️</span> Exciting Offers
				</div>
			</div>
		</div>

		<div class="form-side">
			<h2>Create your account</h2>

			<!-- Error Message -->
			<%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
			<div class="error-msg"
				style="background: #FFE8E8; color: #D63031; border: 1px solid #D63031; padding: 12px; border-radius: 10px; margin: 15px 0; text-align: center; font-weight: 600;">
				<%= error %>
			</div>
			<%
        }
    %>

			<form action="<%=request.getContextPath()%>/register" method="POST">

				<div class="form-grid">
					<input type="text" name="fullName" placeholder="Full Name" required>

					<input type="email" name="email" placeholder="Email Address"
						required>
				</div>

				<div class="form-grid">
					<input type="text" name="phone" placeholder="Phone Number" required>

					<input type="password" name="password" placeholder="Password"
						required>
				</div>

				<input type="password" name="confirmPassword"
					placeholder="Confirm Password" required> <input type="text"
					name="address" placeholder="Delivery Address" required> <input
					type="text" name="city" placeholder="City / Area" required>

				<label style="font-size: 14px; color: #666;"> <input
					type="checkbox" required style="width: auto; margin-right: 5px;">
					I agree to the Terms & Conditions
				</label> <br>
				<br>

				<button type="submit" class="btn-register">Register</button>

			</form>

			<div style="text-align: center; margin-top: 20px;">
				Already have an account? <a
					href="<%=request.getContextPath()%>/login"
					style="color: #E67E22; text-decoration: none; font-weight: bold;">
					Login </a>
			</div>

		</div>

		<script>
        function createBubbles() {
            const body = document.body;
            for (let i = 0; i < 10; i++) {
                let bubble = document.createElement('div');
                bubble.className = 'bubble';
                let size = Math.random() * 60 + 20;
                bubble.style.width = size + 'px';
                bubble.style.height = size + 'px';
                bubble.style.left = Math.random() * 100 + 'vw';
                bubble.style.animationDuration = (Math.random() * 5 + 5) + 's';
                bubble.style.animationDelay = Math.random() * 5 + 's';
                body.appendChild(bubble);
            }
        }
        createBubbles();
    </script>
</body>
</html>
