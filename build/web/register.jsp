<%-- 
    Document   : register
    Created on : Feb 15, 2021, 9:23:39 PM
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
        <title>Register Page</title>
    </head>
    <body>
        <div class="header">
            <a href="#" class="logo">Car Rental</a>
            <div class="header-right">
                <a href="loginPage">Login</a>
                <a class="active" href="#">Register</a>
            </div>
        </div>

        <form action="register" method="POST">
            <div class="container">
                <div style="text-align: center;">
                    <h1>Register</h1>
                    <p>Please fill in this form to create an account.</p>
                </div>
                <hr>
                <c:set var="error" value="${requestScope.ERROR}"/>

                <c:if test="${not empty error.duplicateEmail}">
                    <label for="email"><b>Email</b></label>
                    <input style="border: 2px red solid;" type="email" placeholder="Enter Email" name="email" value="${param.email}" id="email" required>
                    <h2 style="color: red;margin-top: -10px;">${error.duplicateEmail}</h2>

                </c:if>
                <c:if test="${empty error.duplicateEmail}">
                    <label for="email"><b>Email</b></label>
                    <input type="email" placeholder="Enter Email" name="email" value="${param.email}" id="email" required>
                </c:if>

                <label for="phone"><b>Phone</b></label>
                <input type="number" placeholder="Enter Phone" value="${param.phone}" name="phone" id="phone" required>

                <label for="name"><b>Name</b></label>
                <input type="text" placeholder="Enter Name" name="name" value="${param.name}" id="name" required>

                <label for="address"><b>Address</b></label>
                <input type="text" placeholder="Enter Address" name="address" value="${param.address}" id="address" required>

                <c:if test="${not empty error.passwordNotEmpty}">
                    <h2 style="color: red;">Password Not empty!!!</h2>
                    <label for="psw"><b>Password</b></label>
                    <input style="border: 2px red solid;" type="password" placeholder="Enter Password" name="password" id="psw" required>

                    <label for="psw-repeat"><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="confirm" id="psw-repeat" required>
                </c:if>
                <c:if test="${empty error.passwordNotEmpty}">
                    <c:if test="${not empty error.passwordNotMatched}">
                        <label for="psw"><b>Password</b></label>
                        <input style="border: 2px red solid;" type="password" placeholder="Enter Password" name="password" id="psw" required>

                        <label for="psw-repeat"><b>Repeat Password</b></label>
                        <input style="border: 2px red solid;" type="password" placeholder="Repeat Password" name="confirm" id="psw-repeat" required>
                        <h2 style="color: red;margin-top: -10px;">${error.passwordNotMatched}</h2>

                    </c:if>
                    <c:if test="${empty error.passwordNotMatched}">

                        <label for="psw"><b>Password</b></label>
                        <input type="password" placeholder="Enter Password" name="password" id="psw" required>

                        <label for="psw-repeat"><b>Repeat Password</b></label>
                        <input type="password" placeholder="Repeat Password" name="confirm" id="psw-repeat" required>

                    </c:if>
                </c:if>

                <hr>
                <button type="submit" class="registerbtn">Register</button>
            </div>

            <div class="container signin">
                <p>Already have an account? <a href="loginPage">Sign in</a>.</p>
            </div>
        </form>

    </body>
</html>
