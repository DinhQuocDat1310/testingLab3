<%-- 
    Document   : login
    Created on : Feb 28, 2021, 7:39:05 PM
    Author     : DELL INC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./css/header.css">
        <link rel="stylesheet" href="./css/registration.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <title>Login Page</title>
    </head>
    <body>

        <div class="header">
            <a href="#" class="logo">Car Rental</a>
            <div class="header-right">
                <a class="active" href="#">Login</a>
                <a href="registerPage">Register</a>
            </div>
        </div>

        <form action="login" method="POST">
            <div class="container">
                <div style="text-align: center;">
                    <h1>Login</h1>
                </div>
                <hr>

                <c:if test="${not empty requestScope.FAILEMAIL}">
                    <label for="email"><b>Email</b></label>
                    <input style="border: 2px red solid;" type="email" placeholder="Enter Email" name="email" id="email" required>
                    <h2 style="color: red;margin-top: -10px;">${requestScope.FAILEMAIL}</h2>
                </c:if>

                <c:if test="${empty requestScope.FAILEMAIL}">
                    <label for="email"><b>Email</b></label>
                    <input type="email" placeholder="Enter Email" name="email" value="${param.email}" id="email" required>
                </c:if>

                <c:if test="${not empty requestScope.FAILPASS}">
                    <label for="psw"><b>Password</b></label>
                    <input  style="border: 2px red solid;" type="password" placeholder="Enter Password" name="password" id="psw" required>
                    <h2 style="color: red;margin-top: -10px;">${requestScope.FAILPASS}</h2>
                </c:if>
                <c:if test="${empty requestScope.FAILPASS}">
                    <label for="psw"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" id="psw" required>
                </c:if>
                <div class="g-recaptcha"
                     data-sitekey="6LctIm0aAAAAAKgCY10SMh7obU6RerkX3RHwVgrW">
                </div>
                <c:if test="${not empty requestScope.CAPTCHA}">
                    <p style="color: red;">${requestScope.CAPTCHA}</p>
                </c:if>

                <hr>
                <button type="submit" class="loginbtn">Login</button>
            </div>

            <div class="container signin">
                <p>Register an account? <a href="registerPage">Register</a>.</p>
            </div>
        </form>

    </body>
</html>