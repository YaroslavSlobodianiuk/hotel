<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Clock" %><%--
  Created by IntelliJ IDEA.
  User: GUEST USER
  Date: 10.10.2020
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dependent Select Option</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <style type="text/css">
        body{
            background-size: cover;
        }
        .drop-down-list{
            margin: 150px auto;
            width: 50%;
            padding: 30px;
        }

    </style>
</head>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime currentTime = LocalDateTime.now(Clock.systemDefaultZone());
    LocalDateTime oneDayAfterCurrentTime = currentTime.plusDays(1);
    LocalDateTime oneYearAfterCurrentTime = currentTime.plusYears(1);
    LocalDateTime oneYearAndOneDayAfterCurrentTime = currentTime.plusYears(1).plusDays(1);
    String currentDay = currentTime.format(formatter);
    String oneDayAfterCurrentDay = oneDayAfterCurrentTime.format(formatter);
    String oneYearAfterCurrentDay = oneYearAfterCurrentTime.format(formatter);
    String oneYearAndOneDayAfterCurrentDay = oneYearAndOneDayAfterCurrentTime.format(formatter);
%>
<body class="cyan">
<div class="container">
    <div class="drop-down-list card">
        <div class="center">
            <h5>Application</h5>
        </div>
        <div class="divider"></div>
        <form>
            <div class="input-field">
                <select id="category">
                    <option>Select Category</option>
                </select>
            </div>
            <div class="input-field">
                <select id="capacity">
                    <option>Select Capacity</option>
                </select>
            </div>
            <div class="input-field">
                <select id="apartment">
                    <option>Select Apartment</option>
                </select>
            </div>
            <label for="start">From date:</label>
            <input type="date" id="start" name="trip-start"
                   value="<%=currentDay%>"
                   min="<%=currentDay%>" max="<%=oneYearAfterCurrentDay%>">
            <label for="start">To date:</label>

            <input type="date" id="finish" name="trip-finish"
                   value="<%=oneDayAfterCurrentDay%>"
                   min="<%=oneDayAfterCurrentDay%>" max="<%=oneYearAndOneDayAfterCurrentDay%>"> <br>
            Comment: <br>
            <input type="text" height="100" width="200" name="comment">
            <div class="center">
                <button class="btn">Submit</button>
            </div>
        </form>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.ajax({
            url: "/booking",
            method: "GET",
            data: {operation: 'category'},
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                let obj = $.parseJSON(data);
                $.each(obj, function (key, value) {
                    $('#category').append('<option value="' + value.id + '">' + value.categoryName + '</option>')
                });
                $('select').formSelect();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#category').append('<option>Category Unavailable</option>');
            },
            cache: false
        });


        $('#category').change(function () {
            $('#capacity').find('option').remove();
            $('#capacity').append('<option>Select Capacity</option>');
            $('#apartment').find('option').remove();
            $('#apartment').append('<option>Select Apartment</option>');

            let cid = $('#category').val();
            let data = {
                operation: "capacity",
                id: cid
            };

            $.ajax({
                url: "/booking",
                method: "GET",
                data: data,
                success: function (data, textStatus, jqXHR) {
                    console.log(data);
                    let obj = $.parseJSON(data);
                    $.each(obj, function (key, value) {
                        $('#capacity').append('<option value="' + value.id + '">' + value.capacity + '</option>')
                    });
                    $('select').formSelect();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#capacity').append('<option>Capacity Unavailable</option>');
                },
                cache: false
            });
        });

        $('#capacity').change(function () {
            $('#apartment').find('option').remove();
            $('#apartment').append('<option>Select Apartment</option>');

            let capacityId = $('#capacity').val();
            let categoryId = $('#category').val();
            let data = {
                operation: "apartment",
                capacityId: capacityId,
                categoryId: categoryId
            };

            $.ajax({
                url: "/booking",
                method: "GET",
                data: data,
                success: function (data, textStatus, jqXHR) {
                    console.log(data);
                    if (data.length !== 0) {
                        let obj = $.parseJSON(data);
                        $.each(obj, function (key, value) {
                            $('#apartment').append('<option value="' + value.id + '">' + value.title + '</option>')
                        });
                        $('select').formSelect();
                    } else {
                        $('#apartment').append('<option>No such free apartments</option>');
                        $('select').formSelect();
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#apartment').append('<option>Apartment Unavailable</option>');
                },
                cache: false
            });
        });

    });
</script>
</body>
</html>
