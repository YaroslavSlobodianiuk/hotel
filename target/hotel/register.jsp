<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 25.09.2020
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
</head>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
    <table>
        <tr>
            <td></td>
            <td>${message}</td>
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
            <td>Confirm Password:</td>
            <td>
                <input type="password" name="password_confirmation">
            </td>
        </tr>
        <tr>
            <td>First Name</td>
            <td>
                <input type="text" name="first_name">
            </td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td>
                <input type="text" name="last_name">
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Register" name="register"></td>
            <td>
                <a href="login.jsp">Log in</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
