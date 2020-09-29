<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 25.09.2020
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <table>
        <tr>
            <td>${message}</td>
            <td></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td>
                <input type="text" name="login">
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type="password" name="password">
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Log in" name="submit"></td>
            <td>
                <a href="/register">Registration</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
