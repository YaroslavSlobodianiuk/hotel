<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 28.09.2020
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="blocks/header.jsp"%>
</head>
<body>
    <h1>Orders</h1>
    <table border="2px" >
            <tr>
                <td>id</td>
                <td>userId</td>
                <td>username</td>
                <td>categoryId</td>
                <td>category</td>
                <td>capacityId</td>
                <td>capacity</td>
                <td>apartmentId</td>
                <td>apartmentName</td>
                <td>price</td>
                <td>arrival</td>
                <td>departure</td>
                <td>orderStatusId</td>
                <td>orderStatus</td>
                <td>comment</td>
                <td>action</td>

            </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.userId}</td>
                <td>${order.username}</td>
                <td>${order.categoryId}</td>
                <td>${order.category}</td>
                <td>${order.capacityId}</td>
                <td>${order.capacity}</td>
                <td>${order.apartmentId}</td>
                <td>${order.apartmentName}</td>
                <td>${order.price}</td>
                <td>${order.arrival}</td>
                <td>${order.departure}</td>
                <td>${order.orderStatusId}</td>
                <td>${order.orderStatus}</td>
                <td>${order.comment}</td>
                <c:choose>
                    <c:when test="${order.orderStatus eq 'new'}">
                        <td>
                            <form action="/admin" method="post">
                                <input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="action" value="new">
                                <input type="submit" value="Send for approval">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${order.orderStatus eq 'waiting for approve'}"><td>waiting for approve</td></c:when>
                    <c:when test="${order.orderStatus eq 'approved'}">
                        <td>
                            <form action="/admin" method="post">
                                <input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="action" value="approved">
                                <input type="submit" value="Send for payment">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${order.orderStatus eq 'waiting for payment'}"><td>waiting for payment</td></c:when>
                </c:choose>
                <td>
                    <c:if test="${order.orderStatusId lt 5}">
                        <form action="/admin" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="cancel">
                            <input type="submit" value="Cancel">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
