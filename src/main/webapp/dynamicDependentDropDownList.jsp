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
<body class="cyan">
<div class="container">
    <div class="drop-down-list card">
        <div class="center">
            <h5>Dependent Select Item</h5>
        </div>
        <div class="divider"></div>
        <form action="/booking" method="post">
            <div class="input-field">
                <select id="category" name="category">
                    <option>Select Category</option>
                </select>
            </div>
            <div class="input-field">
                <select id="capacity" name="capacity">
                    <option>Select Capacity</option>
                </select>
            </div>
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
                $('#category').append('<option>No categories</option>');
            },
            cache: false
        });


        $('#category').change(function () {
            $('#capacity').find('option').remove();
            $('#capacity').append('<option>Select Capacity</option>');

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
                    $('#capacity').append('<option>No capacities</option>');
                },
                cache: false
            });
        });
    });
</script>
</body>
</html>
