<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <%@ include file="blocks/header.jsp"%>
    <title><fmt:message key="not_registered_user_page_title"/></title>
</head>
<body>
    <h5><fmt:message key="not_registered_user_page_hint"/></h5>
    <h6><a href="/register"><fmt:message key="not_registered_user_page_register_link"/></a></h6>
    <h6><a href="/"><fmt:message key="not_registered_user_page_main_page_link"/></a></h6>
</body>
</html>
