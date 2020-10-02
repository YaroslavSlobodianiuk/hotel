<%@ page import="com.slobodianiuk.hotel.db.enums.CategoryEnum" %>
<%@ page import="java.awt.*" %>
<%@ page import="com.slobodianiuk.hotel.db.entity.Apartment" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 29.09.2020
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<% List<Apartment> apartments = (List<Apartment>) session.getAttribute("apartments");%>
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <h5 class="my-0 mr-md-auto font-weight-normal">Company name</h5>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="/apartments">Apartments</a>
        <a class="p-2 text-dark" href="#">Enterprise</a>
        <a class="p-2 text-dark" href="#">Support</a>
        <a class="p-2 text-dark" href="#">Pricing</a>
    </nav>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/logout">Sign out</a>
</div>
<c:forEach items="${apartments}" var="apart">
    <div class="container mt-5">
        <div class="alert alert-info mt-2">
            <h3>${apart.title}</h3>
            <p>Max guests: ${apart.roomCapacity}</p>
            <p>Category: ${apart.category}</p>
            <p>Price: ${apart.price}</p>
            <form action="/apartments/${apart.id}" method="post">
                <input type="hidden" name="details" value="${apart.id}">
                <button type="submit" class="btn btn-warning">Details...</button>
            </form>

            <form action="/apartments/${apart.id}/book" method="post">
                <button type="submit" class="btn btn-warning">Book Now</button>
            </form>
        </div>
    </div>
</c:forEach>
</body>
</html>
