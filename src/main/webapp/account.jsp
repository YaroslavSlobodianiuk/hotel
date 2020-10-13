<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 12.10.2020
  Time: 2:13
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
    <%@ include file="blocks/header.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        p {
            display: inline;
            font-size: 20px;
            margin-top: 0px;
        }
    </style>
</head>
<body>
<h1>Orders</h1>
<table border="2px" >
    <tr>
        <td>id</td>
        <td>category</td>
        <td>capacity</td>
        <td>apartmentName</td>
        <td>price</td>
        <td>arrival</td>
        <td>departure</td>
        <td>orderStatus</td>
        <td>comment</td>
        <c:if test="${order.orderStatusId lt 5}">
            <td>action</td>
        </c:if>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.category}</td>
            <td>${order.capacity}</td>
            <td>${order.apartmentName}</td>
            <td>${order.price}</td>
            <td>${order.arrival}</td>
            <td>${order.departure}</td>
            <td>${order.orderStatus}</td>
            <td>${order.comment}</td>
            <c:choose>
                <c:when test="${order.orderStatus eq 'new'}"><td>new</td></c:when>
                <c:when test="${order.orderStatus eq 'waiting for approve'}">
                    <td>
                        <form action="/me" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="waiting for approve">
                            <input type="submit" value="Approve">
                        </form>
                    </td>
                </c:when>
                <c:when test="${order.orderStatus eq 'approved'}"><td>approved</td></c:when>
                <c:when test="${order.orderStatus eq 'waiting for payment'}">
                    <td>
                        <form action="/me" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="waiting for payment">
                            <input type="submit" value="Pay">
                        </form>
                    </td>
                    <p>You have time to pay your order</p><br>
                    <p id="days"></p>
                    <p id="hours"></p>
                    <p id="mins"></p>
                    <p id="secs"></p>
                    <h2 id="end"></h2>
                    <script>
                        // The data/time we want to countdown to
                        var countDownDate = new Date(${date}).getTime();

                        // Run myfunc every second
                        var myfunc = setInterval(function() {

                            var now = new Date().getTime();
                            var timeleft = countDownDate - now;

                            // Calculating the days, hours, minutes and seconds left
                            var days = Math.floor(timeleft / (1000 * 60 * 60 * 24));
                            var hours = Math.floor((timeleft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                            var minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
                            var seconds = Math.floor((timeleft % (1000 * 60)) / 1000);

                            // Result is output to the specific element
                            document.getElementById("days").innerHTML = days + "d ";
                            document.getElementById("hours").innerHTML = hours + "h ";
                            document.getElementById("mins").innerHTML = minutes + "m ";
                            document.getElementById("secs").innerHTML = seconds + "s ";

                            // Display the message when countdown is over
                            if (timeleft < 0) {
                                clearInterval(myfunc);
                                document.getElementById("days").innerHTML = "";
                                document.getElementById("hours").innerHTML = "";
                                document.getElementById("mins").innerHTML = "";
                                document.getElementById("secs").innerHTML = "";
                                sendRequest();
                            }
                        }, 1000);

                        function sendRequest() {
                            var http = new XMLHttpRequest();
                            var url = '/me';
                            var params = 'action=expired&id=${order.id}&apartmentId=${order.apartmentId}';
                            http.open('POST', url, true);

//Send the proper header information along with the request
                            http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                            http.onreadystatechange = function() {//Call a function when the state changes.
                                if(http.readyState == 4 && http.status == 200) {

                                }
                            };
                            http.send(params);
                        }
                    </script>
                </c:when>
                <c:when test="${order.orderStatus eq 'paid'}"><td>paid</td></c:when>
            </c:choose>
            <c:if test="${order.orderStatusId lt 5}">
                <td>
                    <form action="/me" method="post">
                        <input type="hidden" name="id" value="${order.id}">
                        <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                        <input type="hidden" name="action" value="declined">
                        <input type="submit" value="Decline">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
