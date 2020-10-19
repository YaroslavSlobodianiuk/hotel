<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="apartment_page_title"/></title>
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-info mt-2">
        <h3>${apartment.title}</h3>
        <p><fmt:message key="apartment_page_capacity"/> ${apartment.roomCapacity}</p>
        <p><fmt:message key="apartment_page_category"/> ${apartment.category}</p>
        <p><fmt:message key="apartment_page_price"/> ${apartment.price}</p>
        <p><fmt:message key="apartment_page_status"/> ${apartment.status}</p>

        <a class="btn btn-warning" href="/booking"><fmt:message key="apartment_page_book_now_btn"/></a>
    </div>
</div>
</body>
</html>


