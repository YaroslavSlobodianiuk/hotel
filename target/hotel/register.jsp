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
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="register_page_title"/></title>
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
            <td><fmt:message key="register_page_login"/></td>
            <td>
                <input type="text" name="login">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="register_page_password"/></td>
            <td>
                <input type="password" name="password">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="register_page_password_confirmation"/></td>
            <td>
                <input type="password" name="password_confirmation">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="register_page_first_name"/></td>
            <td>
                <input type="text" name="first_name">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="register_page_last_name"/></td>
            <td>
                <input type="text" name="last_name">
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="<fmt:message key="register_page_register_btn"/>" name="register"></td>
            <td>
                <a href="/login"><fmt:message key="register_page_log_in_link"/></a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
