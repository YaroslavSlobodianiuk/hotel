<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        p {
            display: inline;
            font-size: 40px;
            margin-top: 0px;
        }
    </style>
</head>
<body>
<body>
<p id="days"></p>
<p id="hours"></p>
<p id="mins"></p>
<p id="secs"></p>
<h2 id="end"></h2>
<script>
    // The data/time we want to countdown to
    var countDownDate = new Date(${date}).getTime();

    // Run myfunc every second
    var myfunc = setInterval(function() {

        var now = new Date().getTime();
        var timeleft = countDownDate - now;

        // Calculating the days, hours, minutes and seconds left
        var days = Math.floor(timeleft / (1000 * 60 * 60 * 24));
        var hours = Math.floor((timeleft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((timeleft % (1000 * 60)) / 1000);

        // Result is output to the specific element
        document.getElementById("days").innerHTML = days + "d ";
        document.getElementById("hours").innerHTML = hours + "h ";
        document.getElementById("mins").innerHTML = minutes + "m ";
        document.getElementById("secs").innerHTML = seconds + "s ";

        // Display the message when countdown is over
        if (timeleft < 0) {
            clearInterval(myfunc);
            document.getElementById("days").innerHTML = "";
            document.getElementById("hours").innerHTML = "";
            document.getElementById("mins").innerHTML = "";
            document.getElementById("secs").innerHTML = "";
            sendRequest();
            document.getElementById("end").innerHTML = "TIME UP!!";
        }
    }, 1000);

    function sendRequest() {
        var http = new XMLHttpRequest();
        var url = '/me';
        var params = 'action=expired&id=';
        http.open('POST', url, true);

//Send the proper header information along with the request
        http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        http.onreadystatechange = function() {//Call a function when the state changes.
            if(http.readyState == 4 && http.status == 200) {

            }
        }
        http.send(params);
    }
</script>

</body>
</html>