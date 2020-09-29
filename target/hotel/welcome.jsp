<%@ page import="com.slobodianiuk.hotel.db.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 26.09.2020
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Title</title>
</head>
<body>
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
<h1>
    <% User user = (User) session.getAttribute("user");%>
    <%= "Hi, " + user.getFirstName()%>
</h1>
</body>
</html>
