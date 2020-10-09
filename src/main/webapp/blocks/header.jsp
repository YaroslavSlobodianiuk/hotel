<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 02.10.2020
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <h5 class="my-0 mr-md-auto font-weight-normal menu"><fmt:message key="logo"/></h5>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="/apartments">Apartments</a>
        <a class="p-2 text-dark" href="/booking">Booking</a>
        <a class="p-2 text-dark" href="#">Contacts</a>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/login">Sign in</a>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/logout">Log out</a>
        <div class="dropdown">
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                Language
            </button>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="/language?locale=en">EN</a>
                <a class="dropdown-item" href="/language?locale=ru">RU</a>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
