<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="lib" prefix="ct"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="booking_page_title"/></title>
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
        h6 {
            color: red;
        }
    </style>
</head>
<body class="cyan">
<div class="container">
    <div class="drop-down-list card">
        <div class="center">
            <h5><fmt:message key="booking_page_form_name"/></h5>
        </div>
        <div class="divider"></div>
        <form action="/booking" method="post">
            <div class="input-field">
                <select id="category" name="category">
                    <option><fmt:message key="booking_page_form_select_category"/></option>
                </select>
            </div>
            <h6>${categoryErrorMessage}</h6>
            <div class="input-field">
                <select id="capacity" name="capacity">
                    <option><fmt:message key="booking_page_form_select_capacity"/></option>
                </select>
            </div>
            <h6>${capacityErrorMessage}</h6>
            <div class="input-field">
                <select id="apartment" name="apartment">
                    <option><fmt:message key="booking_page_form_select_apartment"/></option>
                </select>
            </div>
            <h6>${appErrorMessage}</h6>
            <label for="start"><fmt:message key="booking_page_form_arrival_date"/></label>
            <input type="date" id="start" name="trip-start"
                   value="<ct:time type="days" period="0"/>"
                   min="<ct:time type="days" period="0"/>" max="<ct:time type="years" period="1"/>">
            <h6>${dateErrorMessage}</h6>
            <label for="start"><fmt:message key="booking_page_form_departure_date"/></label>
            <input type="date" id="finish" name="trip-finish"
                   value="<ct:time type="days" period="1"/>"
                   min="<ct:time type="days" period="1"/>" max="<ct:time type="yearsAndDays" period="1"/>">
            <label for="start"><fmt:message key="booking_page_form_comment"/></label>
            <input type="text" height="100" width="200" name="comment">
            <div class="center">
                <button class="btn"><fmt:message key="booking_page_form_checkout_btn"/></button>
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
                let obj = $.parseJSON(data);
                $.each(obj, function (key, value) {
                    $('#category').append('<option value="' + value.id + '">' + value.categoryName + '</option>')
                });
                $('select').formSelect();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#category').append('<option><fmt:message key="booking_page_form_category_error"/></option>');
            },
            cache: false
        });


        $('#category').change(function () {
            $('#capacity').find('option').remove();
            $('#capacity').append('<option><fmt:message key="booking_page_form_select_capacity"/></option>');
            $('#apartment').find('option').remove();
            $('#apartment').append('<option><fmt:message key="booking_page_form_select_apartment"/></option>');

            $('#apartment').formSelect('destroy');
            $('#apartment').formSelect();
            $('#capacity').formSelect('destroy');
            $('#capacity').formSelect();

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
                    let obj = $.parseJSON(data);
                    $.each(obj, function (key, value) {
                        $('#capacity').append('<option value="' + value.id + '">' + value.capacity + '</option>')
                    });
                    $('select').formSelect();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#capacity').append('<option><fmt:message key="booking_page_form_capacity_error"/></option>');
                },
                cache: false
            });
        });

        $('#capacity').change(function () {
            $('#apartment').find('option').remove();
            $('#apartment').append('<option><fmt:message key="booking_page_form_select_apartment"/></option>');

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
                    if (data.length !== 0) {
                        let obj = $.parseJSON(data);
                        $.each(obj, function (key, value) {
                            $('#apartment').append('<option value="' + value.id + '">' + value.title + '</option>')
                        });
                        $('select').formSelect();
                    } else {
                        $('#apartment').append('<option><fmt:message key="booking_page_form_no_apartment"/></option>');
                        $('select').formSelect();
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#apartment').append('<option><fmt:message key="booking_page_form_apartment_error"/></option>');
                },
                cache: false
            });
        });

    });
</script>
</body>
</html>