<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.designpattern.model.Order"%>
<%@ page import="com.designpattern.model.User"%>

<%
List<Order> orders=(List<Order>)request.getAttribute("ordersList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodie | My Orders</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">

<style>
*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:'Poppins',sans-serif;
}

body{
background:#f8f8f8;
color:#333;
}

/* NAVBAR */
.navbar{
height:80px;
background:white;
display:flex;
justify-content:space-between;
align-items:center;
padding:0 80px;
box-shadow:0 2px 15px rgba(0,0,0,.08);
}

.logo{
font-size:32px;
font-weight:700;
color:#E67E22;
}

.nav-links{
display:flex;
gap:35px;
}

.nav-links a{
text-decoration:none;
color:#2C3E50;
font-size:17px;
font-weight:500;
}

.nav-links a:hover,
.nav-links a.active{
color:#E67E22;
}

/* ORDERS */
.container{
width:900px;
margin:40px auto;
}

.container h2{
margin-bottom:20px;
}

/* CARD */
.order-card{
background:white;
border-radius:15px;
padding:20px;
margin-bottom:18px;
box-shadow:0 4px 15px rgba(0,0,0,.1);
}

.restaurant{
font-size:20px;
font-weight:600;
color:#E67E22;
margin-bottom:10px;
}

.details p{
margin:7px 0;
font-size:15px;
}

.status{
display:inline-block;
margin-top:10px;
padding:5px 15px;
border-radius:20px;
background:#e8f5e9;
color:green;
font-weight:600;
}

.btn{
margin-top:15px;
padding:10px 20px;
border:none;
background:#F97316;
color:white;
border-radius:8px;
font-weight:600;
cursor:pointer;
}

.btn:hover{
background:#e06600;
}

</style>
</head>

<body>

<nav class="navbar">
<div class="logo">Foodie</div>

<div class="nav-links">
<a href="home">Home</a>
<a href="restaurants">Restaurants</a>
<a href="cart">Cart</a>
<a href="orders" class="active">My Orders</a>
<a href="profile.jsp">Profile</a>
<a href="login">Login</a>
</div>

</nav>


<div class="container">

<h2>My Orders</h2>


<%
if(orders!=null && !orders.isEmpty()){

for(Order o:orders){
%>


<div class="order-card">

<div class="restaurant">
<%=o.getRestaurantName()!=null?o.getRestaurantName():"Restaurant #"+o.getRestaurantId()%>
</div>

<div class="details">

<p>
<b>Order ID:</b> #<%=o.getOrderId()%>
</p>


<p>
<b>Items:</b>
<%=o.getItemsSummary()!=null&&!o.getItemsSummary().isEmpty()?o.getItemsSummary():"Food Items"%>
</p>


<p>
<b>Payment:</b> <%=o.getPaymentMethod()%>
</p>


<p>
<b>Total:</b> ₹<%=o.getTotalAmount()%>
</p>


<p>
<b>Status:</b>
<span class="status">
<%=o.getStatus()%>
</span>
</p>


</div>
</div>


<%
}

}else{
%>


<div class="order-card">
<h3>No Orders Yet</h3>
<p>You haven't placed any orders.</p>

<form action="restaurants" method="get">
<button class="btn">Order Now</button>
</form>

</div>


<%
}
%>


</div>

</body>
</html>