<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
    <html>
<head>
    <script>
        function getSelectedOption() {

            const queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            document.getElementById('sort-select').options[urlParams.get('sort')].selected = true;
            document.getElementById('sort-order').options[urlParams.get('order')].selected = true;
        }

        function sort() {

            var e = document.getElementById('sort-select');
            var sort = e.options[e.selectedIndex].value;

            var el = document.getElementById('sort-order');
            var order = el.options[el.selectedIndex].value;
            window.location.replace('/apartments?sort=' + sort + '&order=' + order);
        }
    </script>
    <%@ include file="blocks/header.jsp" %>
    <title><fmt:message key="apartments_page_title"/></title>
</head>
<body onload="getSelectedOption()">
<div>
    <select id="sort-select" onchange="sort()">
        <option id="default" value="default"><fmt:message key="apartments_page_sort_default"/></option>
        <option id="price" value="price"><fmt:message key="apartments_page_sort_price"/></option>
        <option id="capacity" value="capacity"><fmt:message key="apartments_page_sort_capacity"/></option>
        <option id="category_name" value="category_name"><fmt:message key="apartments_page_sort_category"/></option>
        <option id="status_name" value="status_name"><fmt:message key="apartments_page_sort_status"/></option>
    </select>
</div>
<div>
    <select id="sort-order" onchange="sort()">
        <option id="asc" value="asc"><fmt:message key="apartments_page_sort_asc_order"/></option>
        <option id="desc" value="desc"><fmt:message key="apartments_page_sort_desc_order"/></option>
    </select>
</div>
<c:forEach items="${apartments}" var="apart">
    <div class="container mt-5">
        <div class="alert alert-info mt-2">
            <h3>${apart.title}</h3>
            <p><fmt:message key="apartments_page_capacity"/> ${apart.roomCapacity}</p>
            <p><fmt:message key="apartments_page_category"/> ${apart.category}</p>
            <p><fmt:message key="apartments_page_price"/> ${apart.price}</p>
            <p><fmt:message key="apartments_page_status"/> ${apart.status}</p>
            <form action="/apartments/${apart.id}" method="post">
                <input type="hidden" name="apartmentId" value="${apart.id}">
                <button type="submit" class="btn btn-warning"><fmt:message key="apartments_page_details"/></button>
            </form>
            <a class="btn btn-warning" href="/booking"><fmt:message key="apartments_page_book_now_btn"/></a>
        </div>
    </div>
</c:forEach>
<c:if test="${currentPage != 1}">
    <td><a href="${pageContext.request.contextPath}/apartments?page=${currentPage - 1}&sort=${sortingType}&order=${sortingOrder}"><fmt:message key="apartments_page_previous_page_btn"/></a></td>
</c:if>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" var="i" end="${numberOfPages}">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="${pageContext.request.contextPath}/apartments?page=${i}&sort=${sortingType}&order=${sortingOrder}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>
<c:if test="${currentPage lt numberOfPages}">
    <td><a href="${pageContext.request.contextPath}/apartments?page=${currentPage + 1}&sort=${sortingType}&order=${sortingOrder}"><fmt:message key="apartments_page_next_page_btn"/></a></td>
</c:if>
</body>
</html>
