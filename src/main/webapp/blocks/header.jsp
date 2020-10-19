<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <h5 class="my-0 mr-md-auto font-weight-normal menu"><a href="/"><fmt:message key="header_logo"/></a></h5>
    <div class="dropdown">
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
            <fmt:message key="header_language"/>
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="/language?locale=en">EN</a>
            <a class="dropdown-item" href="/language?locale=ru">RU</a>
        </div>
    </div>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="/apartments"><fmt:message key="header_apartments"/></a>
        <a class="p-2 text-dark" href="/booking"><fmt:message key="header_booking"/></a>
        <a class="p-2 text-dark" href="#"><fmt:message key="header_contacts"/></a>
        <c:if test="${empty user}">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/login"><fmt:message key="header_signIn"/></a>
        </c:if>
        <c:if test="${(not empty user) && (user.roleId == 2)}">
            <a class="p-2 text-dark" href="/me"><fmt:message key="header_account"/></a>
        </c:if>
        <c:if test="${not empty user}">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/logout"><fmt:message key="header_logOut"/></a>
        </c:if>
    </nav>
</div>
</body>
</html>
