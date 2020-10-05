<%@ page import="com.slobodianiuk.hotel.db.enums.CategoryEnum" %>
<%@ page import="java.awt.*" %>
<%@ page import="com.slobodianiuk.hotel.db.entity.Apartment" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 29.09.2020
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
<head>
    <script>
        // window.addEventListener('DOMContentLoaded', (event) => {
        //     const queryString = window.location.search;
        //
        //     console.log(queryString);
        //     const urlParams = new URLSearchParams(queryString);
        //     document.getElementById('sort-select').options[urlParams.get('selected')].selected = true;
        //     console.log(urlParams.get('selected'));
        // });
        function getSelectedOption() {
            // if (localStorage.getItem('selectedItem')) {
            //     // var id = localStorage.getItem('sort-select').id;
            //     // document.getElementById(id).selected = true;
            //     document.getElementById('sort-select').options[localStorage.getItem('selectedItem')].selected = true;
            //     //$("#sort-select option").eq(localStorage.getItem('sort-select')).prop('selected', true);
            // }

            // if (localStorage.getItem('selectedItem')) {
            //     document.getElementById('sort-select').options[localStorage.getItem('selectedItem')].selected = true;
            // }
            const queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            document.getElementById('sort-select').options[urlParams.get('sort')].selected = true;
        }

        // function setSelectedItem(value) {
        //     console.assert(true,"selectedItem");
        //     localStorage.setItem('selectedItem', value);
        //
        // }
        function sort(value) {


            // localStorage.setItem('selectedItem', value);
            // window.location.replace("/apartments?sort=" + value);
            window.location.replace("/apartments?sort=" + value);





            // $("#sort-select").on('change', function() {
            //     localStorage.setItem('sort-select', $('option:selected', this).index());
            // });
            // window.location.replace("/apartments?sort="+value);

            //y
            //
            //
            // history.pushState()
            // var selectEl = document.getElementById("sort-select");
            // var id = selectEl.options[selectEl.selectedIndex].id;
            // document.getElementById(id).selected = true;

        }
    </script>
    <title>Title</title>
    <%@ include file="blocks/header.jsp" %>
</head>
<body onload="getSelectedOption()">
<div>
    <select id="sort-select" name="sort-select" onchange="sort(value)">
        <option value="default" selected>Select a sort type:</option>
        <option id="price" value="price">price</option>
        <option id="capacity" value="capacity">number of places</option>
    </select>
</div>
<%--<script>--%>
<%--    --%>

<%--    if (window.localStorage.getItem('selectedItem')) {--%>
<%--        document.getElementById('sort-select').options[window.localStorage.getItem('selectedItem')].selected = true;--%>
<%--    }--%>
<%--</script>--%>
<h1>Sorting: ${sortingType}</h1>
<c:forEach items="${apartments}" var="apart">
    <div class="container mt-5">
        <div class="alert alert-info mt-2">
            <h3>${apart.title}</h3>
            <p>Max guests: ${apart.roomCapacity}</p>
            <p>Category: ${apart.category}</p>
            <p>Price: ${apart.price}</p>
            <p>Status: ${apart.status}</p>
            <form action="/apartments/${apart.id}" method="post">
                <input type="hidden" name="details" value="${apart.id}">
                <button type="submit" class="btn btn-warning">Details...</button>
            </form>

            <form action="/apartments/${apart.id}/book" method="post">
                <button type="submit" class="btn btn-warning">Book Now</button>
            </form>
        </div>
    </div>
</c:forEach>
<c:if test="${currentPage != 1}">
    <td><a href="apartments?page=${currentPage - 1}&sort=${sortingType}">Previous</a></td>
</c:if>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" var="i" end="${numberOfPages}">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="/apartments?page=${i}&sort=${sortingType}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>
<c:if test="${currentPage lt numberOfPages}">
    <td><a href="apartments?page=${currentPage + 1}&sort=${sortingType}">Next</a></td>
<%--    <td><a href="apartments?page=${currentPage + 1}">Next</a></td>--%>
</c:if>
</body>
</html>
