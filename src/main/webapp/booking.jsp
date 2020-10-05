<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Clock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 04.10.2020
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="blocks/header.jsp"%>
    <style>
        label {
            display: block;
            font: 1rem 'Fira Sans', sans-serif;
        }

        input,
        label {
            margin: .4rem 0;
        }
    </style>
</head>
<body>
    <h1>${message}</h1>
    <form action="/booking" method="post">
        Capacity: <select id="capacity" name="capacity">
            <c:forEach items="${capacities}" var="capacities">
                <option value="${capacities.capacity}">${capacities.capacity}</option>
            </c:forEach>
        </select> <br>
        Category: <select id="category" name="category">
            <c:forEach items="${categories}" var="categories">
                <option value="${categories.categoryName}">${categories.categoryName}</option>
            </c:forEach>
        </select> <br>
        <%
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime currentTime = LocalDateTime.now(Clock.systemDefaultZone());
            LocalDateTime oneDayAfterCurrentTime = currentTime.plusDays(1);
            LocalDateTime oneYearAfterCurrentTime = currentTime.plusYears(1);
            LocalDateTime oneYearAndOneDayAfterCurrentTime = currentTime.plusYears(1).plusDays(1);

            String currentDay = currentTime.format(formatter);
            String oneDayAfterCurrentDay = oneDayAfterCurrentTime.format(formatter);
            String oneYearAfterCurrentDay = oneYearAfterCurrentTime.format(formatter);
            String oneYearAndOneDayAfterCurrentDay = oneYearAndOneDayAfterCurrentTime.format(formatter);

        %>
        <label for="start">From date:</label>
        <input type="date" id="start" name="trip-start"
               value="<%=currentDay%>"
               min="<%=currentDay%>" max="<%=oneYearAfterCurrentDay%>">
        <label for="start">To date:</label>

        <input type="date" id="finish" name="trip-finish"
               value="<%=oneDayAfterCurrentDay%>"
               min="<%=oneDayAfterCurrentDay%>" max="<%=oneYearAndOneDayAfterCurrentDay%>"> <br>
        <input type="submit" value="Book Now">
    </form>
</body>
</html>
