<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodie | Login</title>

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;
}

body{
    background:#FFF7F0;
    display:flex;
    justify-content:center;
    align-items:center;
    min-height:100vh;
    position:relative;
}

body::before{
    content:"";
    position:fixed;
    left:-280px;
    bottom:-180px;
    width:420px;
    height:420px;
    background:#E67E22;
    border-radius:50%;
    z-index:-1;
}

.login-wrapper{
    width:900px;
    max-width:95%;
    display:flex;
    background:#fff;
    border-radius:25px;
    overflow:hidden;
    box-shadow:0 10px 30px rgba(0,0,0,.15);
}

.image-side{
    flex:1;
    background:url('<%=request.getContextPath()%>/images/burger.jpg') center/cover no-repeat;
}

.form-side{
    flex:1;
    padding:50px 40px;
    display:flex;
    flex-direction:column;
    justify-content:center;
}

.brand-name{
    color:#E67E22;
    font-size:42px;
    font-weight:700;
    margin-bottom:5px;
}

.tagline{
    color:#444;
    margin-bottom:30px;
}

.login-header h2{
    font-size:32px;
    margin-bottom:5px;
}

.login-header p{
    color:#666;
    margin-bottom:25px;
}

.error-message{
    width:100%;
    padding:12px 16px;
    margin-bottom:18px;
    background:#FDECEC;
    color:#C62828;
    border-left:5px solid #E53935;
    border-radius:8px;
    font-size:15px;
    font-weight:600;
}

.input-group{
    margin-bottom:18px;
}

.input-group input{
    width:100%;
    padding:14px 18px;
    border:none;
    border-radius:30px;
    background:#FFF3E8;
    outline:none;
    font-size:15px;
    transition:.3s;
}

.input-group input:focus{
    box-shadow:0 0 5px rgba(230,126,34,.6);
}

.login-btn{
    width:100%;
    padding:14px;
    border:none;
    border-radius:30px;
    background:#E67E22;
    color:#fff;
    font-size:18px;
    font-weight:bold;
    cursor:pointer;
    transition:.3s;
}

.login-btn:hover{
    background:#cf6c18;
}

.footer-text{
    margin-top:20px;
    text-align:center;
    color:#555;
}

.footer-text a{
    color:#E67E22;
    text-decoration:none;
    font-weight:bold;
}

.footer-text a:hover{
    text-decoration:underline;
}

@media(max-width:900px){

.login-wrapper{
    flex-direction:column;
}

.image-side{
    height:250px;
}

}
</style>
</head>

<body>

<div class="login-wrapper">

    <div class="image-side"></div>

    <div class="form-side">

        <h1 class="brand-name">Foodie</h1>
        <p class="tagline">Delicious food, delivered fast ❤️</p>

        <div class="login-header">
            <h2>Login</h2>
            <p>Welcome Back!</p>
        </div>

        <%
            String errorMessage = (String)request.getAttribute("errorMessage");
            String email = (String)request.getAttribute("email");
        %>

        <% if(errorMessage != null){ %>

            <div class="error-message">
                ❌ <%= errorMessage %>
            </div>

        <% } %>

        <form action="<%=request.getContextPath()%>/login" method="post">

            <div class="input-group">
                <input
                    type="email"
                    name="email"
                    placeholder="Enter Email Address"
                    value="<%= email != null ? email : "" %>"
                    required>
            </div>

            <div class="input-group">
                <input
                    type="password"
                    name="password"
                    placeholder="Enter Password"
                    required>
            </div>

            <button type="submit" class="login-btn">
                Login
            </button>

        </form>

        <div class="footer-text">
            Don't have an account?
            <a href="<%=request.getContextPath()%>/register">
                Sign Up
            </a>
        </div>

    </div>

</div>

</body>
</html>