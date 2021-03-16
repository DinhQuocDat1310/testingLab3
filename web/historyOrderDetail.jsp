<%-- 
    Document   : historyOrderDetail
    Created on : Mar 8, 2021, 10:33:43 PM
    Author     : DELL INC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./css/searchForm.css">
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
                    <a href="loadOrder">History Renting</a>
                    <a class="active" href="#">History Renting Detail</a>
                </c:if>
                <a href="logout">Log Out</a>
            </div>
        </div>
        <div style="margin-top: 40px"></div>
        <c:set var="listDetail" value="${requestScope.LISTDETAIL}"/>
        <c:if test="${not empty listDetail}">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Renting ID</th>
                        <th scope="col">Car ID</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Unit Price</th>
                        <th scope="col">rental Date</th>
                        <th scope="col">return Date</th>
                        <th scope="col">Status</th>
                        <th scope="col">FeedBack</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${listDetail}" varStatus="counter">
                        <tr>

                    <form action="feedbackDetail">
                        <input type="hidden" name="rentingDetailID" value="${dto.rentingDetailID}" />
                        <input type="hidden" name="rentingID" value="${dto.rentingID}" />
                        <input type="hidden" name="userID" value="${user.email}" />
                        <th scope="row">${counter.count}</th>
                        <td>${dto.rentingID}</td>
                        <td>${dto.carID}</td>
                        <td>${dto.quantity}</td>
                        <td>${dto.price}</td>
                        <td>
                            <input type="date" value="${dto.rentalDate}" disabled/>
                        </td>
                        <td>
                            <input type="date" value="${dto.returnDate}" disabled/>
                        </td>
                        <c:if test="${dto.status eq true}"> 
                            <td>Active</td>    
                        </c:if>
                        <c:if test="${dto.status eq false}"> 
                            <td>InActive</td>    
                        </c:if>

                        <td>
                            <c:if test="${dto.isFeedBack eq false}"> 
                                <button type="button" class="btn btn-danger" >Cannot FeedBack</button>    
                            </c:if>
                            <c:if test="${dto.isFeedBack eq true}"> 
                                <c:if test="${dto.desriptionFeedBack != null}">
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalRespond${counter.count}">
                                        Responded
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade" id="modalRespond${counter.count}" tabindex="-1" role="dialog"
                                         aria-labelledby="modalRespond${counter.count}Title" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLongTitle"><b>Edit FeedBack</b></h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="formSearch">
                                                        <div class="container">
                                                            <label for="description"><b>Description: </b></label>
                                                            <input type="text" placeholder="Input Description..." value="${dto.desriptionFeedBack}" name="descriptionValueUpdate"  id="description" required>
                                                            <label for="rate"><b>Rating Star: </b></label>
                                                            <input type="number" min="0" max="10" value="${dto.star}" placeholder="Input Rating..." name="ratingValueUpdate"  id="rate" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                    <button type="submit" class="btn btn-primary" >Edit FeedBack</button>    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${dto.desriptionFeedBack == null}">
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalFeedBack${counter.count}">
                                        FeedBack
                                    </button>
                                    <div class="modal fade" id="modalFeedBack${counter.count}" tabindex="-1" role="dialog"
                                         aria-labelledby="modalFeedBack${counter.count}Title" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLongTitle"><b>Give FeedBack</b></h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="formSearch">
                                                        <div class="container">

                                                            <label for="description"><b>Description: </b></label>
                                                            <input type="text" placeholder="Input Description..." name="descriptionValue"  id="description" required>
                                                            <label for="rate"><b>Rating Star: </b></label>
                                                            <input type="number" min="0" max="10" placeholder="Input Rating..." name="ratingValue"  id="rate" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                    <button type="submit" class="btn btn-primary" >Send FeedBack</button>    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>  
                            </c:if>



                        </td>
                    </form>
                </tr>
            </c:forEach>
        </tbody>
    </table>   
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
