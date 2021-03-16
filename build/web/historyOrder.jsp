<%-- 
    Document   : historyOrder
    Created on : Mar 8, 2021, 10:03:56 PM
    Author     : DELL INC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="./css/header.css">
        <title>ORDER HISTORY</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}"/>
        <div class="header">
            <a href="#" class="logo">Car Rental</a>
            <div class="header-right">
                <a style="color: red;">Welcome, ${user.fullName}</a>
                <a  href="loadCarDefault">Home</a>
                <c:if test="${user.role eq 'new'}">
                    <a href="UserVerifyServlet">Verify Email</a>
                </c:if>
                <c:if test="${user.role eq 'active'}">
                    <a href="viewcartPage">Renting</a>
                    <a class="active" href="#">History Renting</a>
                </c:if>
                <a href="logout">Log Out</a>
            </div>
        </div>
        <div style="margin-top: 40px"></div>


        <c:set var="list" value="${requestScope.LISTORDER}"/>
        <form action="loadOrderByDate">
            <div style="margin: auto; width: 30%;">
                <div class="input-group">
                    <input type="date" name="dateSearch" value="${param.dateSearch}" class="form-control" aria-label="Text input with dropdown button">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-primary">Search By Date</button> 
                    </div>
                </div>
            </div>
        </form>
        <div style="margin-top: 20px"></div>    
        <c:if test="${not empty list}">
            <h2 style="color: red;">${requestScope.ERR}</h2>
            <div style="margin-top: 10px"></div>    
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Renting ID</th>
                        <th scope="col">Email</th>
                        <th scope="col">Create Date</th>
                        <th scope="col">Discount ID</th>
                        <th scope="col">Total Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Delete</th>
                        <th scope="col">Detail</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                        <tr>
                    <form action="loadOrderDetail">
                        <input type="hidden" name="rentingID" value="${dto.rentingID}" />
                        <th scope="row">${counter.count}</th>
                        <td>${dto.rentingID}</td>
                        <td>${dto.userID}</td>
                        <td>
                            <input type="date" value="${dto.createDate}" disabled/>
                        </td>
                        <c:if test="${dto.discountID == null}">
                            <td>Don't Use Discount</td>
                        </c:if>
                        <c:if test="${dto.discountID != null}">
                            <td>${dto.discountID}</td>
                        </c:if>
                        <td>${dto.totalPrice}</td>
                        <c:if test="${dto.status eq true}"> 
                            <td>Active</td>    
                        </c:if>
                        <td>
                            <a href="deleteOrder?rentingID=${dto.rentingID}" class="btn btn-danger">Delete</a>
                        </td>
                        <td>
                            <button type="submit" class="btn btn-success">Show Details</button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </tbody>
    </table>   
</c:if>        
<c:if test="${ empty list}">
    <h2 style="color: red; text-align: center">
        Cannot Find Any Oder!!!
    </h2>
</c:if>


</body>
</html>
