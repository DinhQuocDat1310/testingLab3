<%-- 
    Document   : viewcart
    Created on : Mar 3, 2021, 8:51:04 PM
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
        <title>Renting Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}"/>
        <div class="header">
            <a href="#" class="logo">Car Rental</a>
            <div class="header-right">
                <a style="color: red;">Welcome, ${user.fullName}</a>
                <a href="loadCarDefault">Home</a>
                <a class="active" href="#">Renting</a>
                <a href="loadOrder">History Renting</a>
                <a href="logout">Log Out</a>
            </div>
        </div>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}">
            <c:set var="result" value="${cart.cartCar}"/>
            <c:if test="${not empty result}">
                <div class="table-responsive">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Car Image</th>
                                <th scope="col">Car Name</th>

                                <th scope="col">Date Rental</th>
                                <th scope="col">Date Return</th>

                                <th scope="col">Quantity</th>
                                <th scope="col">Unit Price</th>
                                <th scope="col">Update</th>
                                <th scope="col">Remove</th>
                                <th scope="col">Total Unit Price</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${result}" varStatus="counter">
                            <form action="updateCarCart">

                                <tr>
                                    <th scope="row">${counter.count}</th>
                                    <td>
                                        <img src="${dto.value.image}"
                                             height="150px" width="200px">
                                    </td>
                                    <td>${dto.value.name}</td>
                                    <c:set var="dateRental" value="${dto.value.rentalDate}" />
                                    <td>
                                        <input type="date" min="${sessionScope.DATERENTAL}" value="${dto.value.rentalDate}" name="dateRental" required readonly>
                                    </td>
                                    <td>
                                        <c:if test="${param.carID eq dto.key}">
                                            <c:if test="${not empty requestScope.ErrorDate}">
                                                <p style="color: red;">${requestScope.ErrorDate}</p>    
                                            </c:if>
                                            <input type="date" min="" value="${dto.value.returnDate}" name="dateReturn" required readonly> 
                                        </c:if>
                                        <c:if test="${param.carID != dto.key}">
                                            <input type="date" min="" value="${dto.value.returnDate}" name="dateReturn" required readonly>
                                        </c:if>
                                        <c:set var="dateReturn" value="${dto.value.returnDate}" />
                                    </td>
                                    <td>
                                        <c:if test="${param.carID eq dto.key}">
                                            <c:if test="${not empty requestScope.STOCK}">
                                                <p style="color: red;">${requestScope.STOCK}</p>
                                            </c:if>
                                            <input type="number" name="quantity" value="${dto.value.quantity}" min="1" required>    
                                        </c:if>
                                        <c:if test="${param.carID != dto.key}">
                                            <input type="number" name="quantity" value="${dto.value.quantity}" min="1" required>    
                                        </c:if>
                                    </td>
                                    <td>${dto.value.price}$</td>
                                <input type="hidden" name="carID" value="${dto.key}" />
                                <td><button type="submit" class="btn btn-primary">Update</button></td>
                                <td>
                                    <a href="removeCarCart?carID=${dto.key}" class="btn btn-danger" 
                                       onclick="return confirm('Are you sure you want to delete this car?')" >Remove</a>
                                </td>
                                <c:set var="totalUnitPrice" value="${dto.value.quantity * dto.value.price}"/>
                                <c:if test="${dto.value.numberDay != 0}">
                                    <td>${totalUnitPrice}$ x ${dto.value.numberDay}days</td>
                                    <c:set var="totalAllCarPrice" value="${totalUnitPrice*dto.value.numberDay + totalAllCarPrice}"/>
                                </c:if>
                                <c:if test="${dto.value.numberDay == 0}">
                                    <td>${totalUnitPrice}$</td>
                                    <c:set var="totalAllCarPrice" value="${totalUnitPrice + totalAllCarPrice}"/>
                                </c:if>

                                </tr>



                            </form>
                        </c:forEach>
                        <tr>

                            <td colspan="8">
                                <form action="applyDiscount">
                                    <div class="input-group mb-3">
                                        <input type="text" class="form-control" name="discountID" placeholder="Input Voucher..." aria-label="Apply Discount"
                                               aria-describedby="basic-addon2" required>
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-primary" type="submit">Apply</button>
                                        </div>
                                    </div>
                                </form>
                            </td>
                            <td>
                                Total Price
                            </td>
                            <td>
                                ${totalAllCarPrice}$
                                <c:set var="pricePayable" value="${totalAllCarPrice}"/>


                            </td>
                        </tr>
                        <c:set var="discount" value="${sessionScope.DISCOUNT}"/>
                        <tr>
                            <td colspan="8" style="color:green; font-size: 16px;font-weight: bold;">${requestScope.STATUSDIS}</td>
                            <c:if test="${not empty discount}">
                                <td>
                                    <p>Discount</p>
                                    <p>Total Payable</p>
                                </td>
                                <td>
                                    <p>${discount.description}</p>
                                    <p>${totalAllCarPrice*discount.discountPercent}$</p>
                                    <c:set var="pricePayable" value="${totalAllCarPrice*discount.discountPercent}"/>
                                </td>
                            </c:if>
                        </tr>
                        <tr>
                            <td colspan="8"></td>
                            <td colspan="2">
                                <form action="checkDateRent">
                                    <input type="hidden" name="userOrder" value="${user.email}" />
                                    <input type="hidden" name="totalAllPricePayable" value="${pricePayable}" />
                                    <c:if test="${not empty discount}">
                                        <input type="hidden" name="discountID" value="${discount.discountID}" />
                                    </c:if>
                                    <button class="btn btn-success">Proceed to Checkout</button>
                                </form>

                            </td>

                        </tr>
                        </tbody>
                    </table>
                </div>
                <c:if test="${not empty requestScope.OUTSTOCK}">
                    <h2 style="color: red;">${requestScope.OUTSTOCK}</h2>
                </c:if>
            </c:if>
            <c:if test="${empty result}">
                <c:if test="${not empty requestScope.OUTSTOCK}">
                    <h2 style="color: red;">${requestScope.OUTSTOCK}</h2>
                </c:if>
                <h2 style="font-weight: bold;margin-top: 20px">Your Cart is empty!!!</h2>
            </c:if>
        </c:if>
        <c:if test="${empty cart}">
            <h2 style="font-weight: bold;margin-top: 20px; text-align: center;">You Don't buy Anything before</h2>
        </c:if> 

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
                integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
                integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
                integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
    </body>
</html>
