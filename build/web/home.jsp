<%-- 
    Document   : home
    Created on : Feb 28, 2021, 8:31:23 PM
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
        <script src="./js/checkDate.js"></script>
        <title>Home Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}"/>
        <div class="header">
            <a href="#" class="logo">Car Rental</a>
            <div class="header-right">
                <a style="color: red;">Welcome, ${user.fullName}</a>
                <a class="active" href="loadCarDefault">Home</a>
                <c:if test="${user.role eq 'new'}">
                    <a href="UserVerifyServlet">Verify Email</a>
                </c:if>
                <c:if test="${user.role eq 'active'}">
                    <a href="viewcartPage">Renting</a>
                    <a href="loadOrder">History Renting</a>
                </c:if>

                <a href="logout">Log Out</a>
            </div>
        </div>
        <div style="margin-top: 25px;"></div>
        <div style="margin: auto; width: 80%;">
            <div class="row">
                <div class="col-3"></div>
                <div class="col-2">
                    <form action="searchByName">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalSearchByName">
                            Search By Name
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="modalSearchByName" tabindex="-1" role="dialog"
                             aria-labelledby="modalSearchByNameTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle"><b>Search By Name</b></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="formSearch">
                                            <div class="container">
                                                <label for="name"><b>Name: </b></label>
                                                <input type="text" placeholder="Enter Name..." name="searchName" value="${param.searchName}" id="name" required>

                                                <label for="dateRent"><b>Date Rent: </b></label>
                                                <input type="date" name="searchDateRent" value="${param.searchDateRent}" min="${sessionScope.DATERENTAL}" id="dateRent" required>

                                                <label for="dateReturn"><b>Date Return: </b></label>
                                                <input type="date"  name="searchDateReturn" value="${param.searchDateReturn}" id="dateReturn" required>
                                                <h4 id="errName" style="color: red;"></h4>
                                                <label for="quantity"><b>Quantity</b></label>
                                                <input type="number" placeholder="Enter Quantity..." min="1" name="searchQuantity" value="${param.searchQuantity}" id="quantity" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <div id="btnClickName">
                                            <button type="button" class="btn btn-secondary" onclick="mainName()">Check Date</button>    
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>


                <div class="col-2"></div>
                <div class="col-3">
                    <form action="searchByCategory">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#modalSearchByCategory">
                            Search By Category
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="modalSearchByCategory" tabindex="-1" role="dialog"
                             aria-labelledby="modalSearchByCategoryTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle"><b>Search By Category</b></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="formSearch">
                                            <div class="container">
                                                <c:set var="listCate" value="${sessionScope.LISTCATE}"/>
                                                <c:if test="${not empty listCate}">
                                                    <label for="category"><b>Category: </b></label>
                                                    <select name="searchCategory" id="category">
                                                        <c:forEach var="dto" items="${listCate}">
                                                            <c:if test="${param.categoryID eq dto}">
                                                                <option value="${dto}" selected="">${dto}</option>    
                                                            </c:if>
                                                            <c:if test="${param.categoryID != dto}">
                                                                <option value="${dto}">${dto}</option>    
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </c:if>
                                                <c:if test="${empty listCate}">
                                                    <label for="category"><b>Category: </b></label>
                                                    <input type="text" value="Category is maintenance. Comback Later...">
                                                </c:if>
                                                <label for="dateRent"><b>Date Rent: </b></label>
                                                <input type="date" name="searchDateRent" value="${param.searchDateRent}" min="${sessionScope.DATERENTAL}" id="dateRentt" required>

                                                <label for="dateReturn"><b>Date Return: </b></label>
                                                <input type="date"  name="searchDateReturn" value="${param.searchDateReturn}" id="dateReturnn" required>
                                                <h4 id="errCategory" style="color: red;"></h4>
                                                <h4 id="errCategory1" style="color: red;"></h4>
                                                <h4 id="errCategory2" style="color: red;"></h4>
                                                <label for="quantity"><b>Quantity</b></label>
                                                <input type="number" placeholder="Enter Quantity..." min="1" name="searchQuantity" value="${param.searchQuantity}" id="quantity" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <div id="btnClickCategory">
                                            <button type="button" class="btn btn-secondary" onclick="mainCategory()">Check Date</button>    
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div style="margin-top: 40px;"></div>
        <c:set var="list" value="${requestScope.LIST}"/>
        <c:if test="${not empty list}">

            <div style="margin: auto; width: 60%;">
                <h2 style="color: red;">${requestScope.STOCK}</h2>
                <div class="card-group">
                    <c:forEach var="dto" items="${list}">
                        <form action="checkDateValidOrder">
                            <input type="hidden" name="carID" value="${dto.carID}" />
                            <input type="hidden" name="carName" value="${dto.name}" />
                            <div class="card">
                                <img class="card-img-top" src="${dto.image}" alt="${dto.name}"
                                     height="50%" width="50%">
                                <div class="card-body">
                                    <h4 style="font-weight: bold;" class="card-title">${dto.name}</h4>
                                    <p class="card-text"><span style="font-weight: bold;">Color: </span> ${dto.color}</p>
                                    <p class="card-text"><span style="font-weight: bold;">Year: </span>${dto.year}</p>
                                    <p class="card-text"><span style="font-weight: bold;">Category: </span>${dto.category}</p>
                                    <p class="card-text"><span style="font-weight: bold;">Price: </span>${dto.price}$</p>
                                </div>
                                <c:if test="${dto.orderStatus eq false}">
                                    <c:if test="${param.carID == dto.carID}">
                                        <div class="card-footer">
                                            Start Date: <small class="text-muted"><input type="date" min="${sessionScope.DATERENTAL}" value="${param.dateRental}" name="dateRental" required></small>
                                        </div>
                                        <div class="card-footer">
                                            End Date: <small class="text-muted"><input type="date" value="${param.dateReturn}" name="dateReturn" required></small>
                                        </div>        
                                    </c:if>
                                    <c:if test="${param.carID != dto.carID}">
                                        <div class="card-footer">
                                            Start Date: <small class="text-muted"><input type="date" min="${sessionScope.DATERENTAL}" value="" name="dateRental" required></small>
                                        </div>
                                        <div class="card-footer">
                                            End Date: <small class="text-muted"><input type="date" min="" value="" name="dateReturn" required></small>
                                        </div>      
                                    </c:if>
                                </c:if>
                                <div class="card-footer">
                                    <c:if test="${param.carID == dto.carID}">
                                        <c:if test="${not empty requestScope.ErrorDate}">
                                            <p style="color: red;">${requestScope.ErrorDate}</p>
                                        </c:if>
                                        <c:if test="${not empty requestScope.IMPOSSIBLE}">
                                            <p style="color: red;">${requestScope.IMPOSSIBLE}</p>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${user.role eq 'active'}">


                                        <input type="hidden" name="actionSeacrh" value="${requestScope.actionSeacrh}" />
                                        <input type="hidden" name="pageIndex" value="${requestScope.PAGEINDEX}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchCategory" value="${param.searchCategory}" />
                                        <input type="hidden" name="searchDateRent" value="${param.searchDateRent}" />
                                        <input type="hidden" name="searchDateReturn" value="${param.searchDateReturn}" />
                                        <input type="hidden" name="searchQuantity" value="${param.searchQuantity}" />

                                        <button type="submit" class="btn btn-primary">Add To Cart</button>        
                                    </c:if>

                                </div>
                            </div>
                            <div style="margin-top:20px;"></div>
                        </form>
                    </c:forEach>

                </div>
            </div>

            <div style="margin-top: 30px;"></div>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:set var="pageIndex" value="${requestScope.PAGEINDEX}"/>
                    <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                    <c:url var="previousPage" value="${requestScope.actionSeacrh}">
                        <c:param name="pageIndex" value="${pageIndex-1}"/>
                    </c:url>
                    <c:if test="${pageIndex <= 1}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <c:if test="${pageIndex > 1}">
                        <li class="page-item">
                            <a class="page-link" href="${previousPage}">Previous</a>
                        </li>
                    </c:if>

                    <c:forEach var="page" begin="1" end="${totalPage}">
                        <c:url var="selectPage" value="${requestScope.actionSeacrh}">
                            <c:param name="pageIndex" value="${page}"/>
                        </c:url>
                        <c:if test="${pageIndex == page}">
                            <li class="page-item active">
                                <a class="page-link" href="${selectPage}">${page}</a>
                            </li>
                        </c:if>
                        <c:if test="${pageIndex != page}">
                            <li class="page-item">
                                <a class="page-link" href="${selectPage}">${page}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                    <c:url var="nextPage" value="${requestScope.actionSeacrh}">
                        <c:param name="pageIndex" value="${pageIndex+1}"/>
                    </c:url>
                    <c:if test="${pageIndex>=totalPage}">
                        <li class="page-item disabled">
                            <a class="page-link" href="${nextPage}" tabindex="-1">Next</a>
                        </li>
                    </c:if>
                    <c:if test="${pageIndex<totalPage}">
                        <li class="page-item">
                            <a class="page-link" href="${nextPage}">Next</a>
                        </li>
                    </c:if>

                </ul>
            </nav>
        </c:if>
            <c:if test="${empty list}">
                <h2 style="color: red; text-align: center;">Can not find the Car you are looking for in this shop.</h2>
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
