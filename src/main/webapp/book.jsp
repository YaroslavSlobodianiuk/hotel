<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 02.10.2020
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Booking</h1>
    <h1>${capacity}</h1>
    <h1>${category}</h1>
    <h1>${from}</h1>
    <h1>${to}</h1>
    <c:forEach items="${locale}">
        <h1>${locale.toUpperCase()}</h1>
    </c:forEach>
<h1>${locale}</h1>
</body>
</html>
