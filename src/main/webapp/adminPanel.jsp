<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="admin_page_title"/></title>
</head>
<body>
    <h1><fmt:message key="admin_page__orders"/></h1>
    <table border="2px" >
            <tr>
                <td><fmt:message key="admin_page_id"/></td>
                <td><fmt:message key="admin_page_user_id"/></td>
                <td><fmt:message key="admin_page_username"/></td>
                <td><fmt:message key="admin_page_category_id"/></td>
                <td><fmt:message key="admin_page_category"/></td>
                <td><fmt:message key="admin_page_capacity"/></td>
                <td><fmt:message key="admin_page_apartment_id"/></td>
                <td><fmt:message key="admin_page_apartment_name"/></td>
                <td><fmt:message key="admin_page_price"/></td>
                <td><fmt:message key="admin_page_arrival"/></td>
                <td><fmt:message key="admin_page_departure"/></td>
                <td><fmt:message key="admin_page_order_status_id"/></td>
                <td><fmt:message key="admin_page_order_status"/></td>
                <td><fmt:message key="admin_page_comment"/></td>
            </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.userId}</td>
                <td>${order.username}</td>
                <td>${order.categoryId}</td>
                <td>${order.category}</td>
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
                                <input type="submit" value="<fmt:message key="admin_page_send_for_confirmation_btn"/>">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${order.orderStatus eq 'waiting for confirmation'}"><td><fmt:message key="admin_page_waiting_for_confirmation_status"/></td></c:when>
                    <c:when test="${order.orderStatus eq 'confirmed'}">
                        <td>
                            <form action="/admin" method="post">
                                <input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="action" value="confirmed">
                                <input type="submit" value="<fmt:message key="admin_page_send_for_payment_btn"/>">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${order.orderStatus eq 'waiting for payment'}"><td><fmt:message key="admin_page_waiting_for_payment_status"/></td></c:when>
                </c:choose>
                <td>
                    <c:if test="${order.orderStatusId lt 5}">
                        <form action="/admin" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="cancel">
                            <input type="submit" value="<fmt:message key="admin_page_cancel_btn"/>">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
