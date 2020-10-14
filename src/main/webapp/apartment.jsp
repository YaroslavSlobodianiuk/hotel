<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="blocks/header.jsp"%>
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-info mt-2">
        <h3>${apartment.title}</h3>
        <p>Max guests: ${apartment.roomCapacity}</p>
        <p>Category: ${apartment.category}</p>
        <p>Price: ${apartment.price}</p>
        <p>Status: ${apartment.status}</p>

        <form action="/booking" method="post">
            <button type="submit" class="btn btn-warning">Book Now</button>
        </form>
    </div>
</div>
</body>
</html>


