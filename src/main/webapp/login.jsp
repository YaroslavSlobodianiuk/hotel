<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 25.09.2020
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="login_page_title"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <table>
        <tr>
            <td>${message}</td>
            <td></td>
        </tr>
        <tr>
            <td><fmt:message key="login_page_login"/></td>
            <td>
                <input type="text" name="login">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="login_page_password"/></td>
            <td>
                <input type="password" name="password">
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="<fmt:message key="login_page_log_in_btn"/>" name="submit"></td>
            <td>
                <a href="/register"><fmt:message key="login_page_registration_link"/></a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
