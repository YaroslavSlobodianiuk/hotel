<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 29.09.2020
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
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
            document.getElementById('sort-order').options[urlParams.get('order')].selected = true;
        }

        // function setSelectedItem(value) {
        //     console.assert(true,"selectedItem");
        //     localStorage.setItem('selectedItem', value);
        //
        // }
        function sort() {


            // localStorage.setItem('selectedItem', value);
            // window.location.replace("/apartments?sort=" + value);
            var e = document.getElementById('sort-select');
            var sort = e.options[e.selectedIndex].value;

            var el = document.getElementById('sort-order');
            var order = el.options[el.selectedIndex].value;
            window.location.replace('/apartments?sort=' + sort + '&order=' + order);





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
    <select id="sort-select" onchange="sort()">
        <option id="default" value="default">default</option>
        <option id="price" value="price">price</option>
        <option id="capacity" value="capacity">number of places</option>
        <option id="category_name" value="category_name">category</option>
        <option id="status_name" value="status_name">status</option>
    </select>
</div>
<div>
    <select id="sort-order" onchange="sort()">
        <option id="asc" value="asc">asc</option>
        <option id="desc" value="desc">desc</option>
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
    <td><a href="${pageContext.request.contextPath}/apartments?page=${currentPage - 1}&sort=${sortingType}&order=${sortingOrder}">Previous</a></td>
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
    <td><a href="${pageContext.request.contextPath}/apartments?page=${currentPage + 1}&sort=${sortingType}&order=${sortingOrder}">Next</a></td>
<%--    <td><a href="apartments?page=${currentPage + 1}">Next</a></td>--%>
</c:if>
</body>
</html>
