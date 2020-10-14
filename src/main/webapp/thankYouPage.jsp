<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 11.10.2020
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>Thank you for your application.</h3>
    <h2><a href="/">Return back to main page</a></h2>
</body>
</html>

select * from user_orders inner join order_statuses on user_orders.order_status_id = order_statuses.id" +
"inner join apartments on user_orders.apartment_id = apartments.id" +
"inner join categories on user_orders.category_id = categories.id" +
"inner join room_capacity on user_orders.room_capacity_id = room_capacity.id" +
"inner join users on user_orders.user_id = users.id" +
"where users.id = (?);