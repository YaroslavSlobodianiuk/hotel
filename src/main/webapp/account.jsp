<%@ taglib uri="lib" prefix="ct"%>

<html>
<head>
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="account_page_title"/></title>
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
<h3><fmt:message key="account_page_orders"/></h3>
<table border="2px" >
    <tr>
        <td><fmt:message key="account_page_id"/></td>
        <td><fmt:message key="account_page_category"/></td>
        <td><fmt:message key="account_page_capacity"/></td>
        <td><fmt:message key="account_page_apartment_name"/></td>
        <td><fmt:message key="account_page_price"/></td>
        <td><fmt:message key="account_page_arrival"/></td>
        <td><fmt:message key="account_page_departure"/></td>
        <td><fmt:message key="account_page_order_status"/></td>
        <td><fmt:message key="account_page_comment"/></td>
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
                <c:when test="${order.orderStatus eq 'new'}"><td><fmt:message key="account_page_new_status"/></td></c:when>
                <c:when test="${order.orderStatus eq 'waiting for confirmation'}">
                    <td>
                        <form action="/me" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="waiting for confirmation">
                            <input type="submit" value="<fmt:message key="account_page_confirm_btn"/>">
                        </form>
                    </td>
                </c:when>
                <c:when test="${order.orderStatus eq 'confirmed'}"><td><fmt:message key="account_page_confirmed_status"/></td></c:when>
                <c:when test="${order.orderStatus eq 'waiting for payment'}">
                    <td>
                        <form action="/me" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                            <input type="hidden" name="action" value="waiting for payment">
                            <input type="submit" value="<fmt:message key="account_page_pay_btn"/>">
                        </form>
                    </td>
                    <p><fmt:message key="account_page_timer_text_begin"/> </p>
                    <p id="days"></p>
                    <p id="hours"></p>
                    <p id="mins"></p>
                    <p id="secs"></p>
                    <p><fmt:message key="account_page_timer_text_end"/></p>
                    <script>
                        var countDownDate = new Date(<ct:deadline order="${order}"/>).getTime();

                        var myfunc = setInterval(function() {

                            var now = new Date().getTime();
                            var timeleft = countDownDate - now;

                            var days = Math.floor(timeleft / (1000 * 60 * 60 * 24));
                            var hours = Math.floor((timeleft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                            var minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
                            var seconds = Math.floor((timeleft % (1000 * 60)) / 1000);

                            document.getElementById("days").innerHTML = days + "d ";
                            document.getElementById("hours").innerHTML = hours + "h ";
                            document.getElementById("mins").innerHTML = minutes + "m ";
                            document.getElementById("secs").innerHTML = seconds + "s ";

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

                            http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                            http.onload = function() {//Call a function when the state changes.
                                if(http.readyState == 4 && http.status == 200) {
                                    document.location.reload();
                                }
                            };
                            http.send(params);
                        }
                    </script>
                </c:when>
            </c:choose>
            <c:if test="${order.orderStatusId lt 5}">
                <td>
                    <form action="/me" method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="apartmentId" value="${order.apartmentId}">
                        <input type="hidden" name="action" value="declined">
                        <input type="submit" value="<fmt:message key="account_page_decline_btn"/>">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
