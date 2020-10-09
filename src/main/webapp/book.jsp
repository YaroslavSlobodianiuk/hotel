<%@ page import="java.util.List" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 02.10.2020
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>

<%
//    List<String> locales = (List<String>) request.getServletContext().getAttribute("locale");

    List<String> locales = null;
    ServletContext servletContext = request.getServletContext();
    String localesValue = servletContext.getInitParameter("locale");
    if (localesValue == null || localesValue.isEmpty()) {

    } else {

        locales = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(localesValue);
        while (st.hasMoreTokens()) {
            String localeName = st.nextToken();
            locales.add(localeName);
        }
        servletContext.setAttribute("locale", locales);
    }
%>
<body>
    <h1>Booking</h1>
    <h1>${category}</h1>
    <h1>${capacity}</h1>
</body>
</html>
