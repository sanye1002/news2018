<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KK公会</title>
</head>
<body style="margin: 0;padding: 0">
<iframe src="http://manage.kktv8.com:8081/family/login.jsp" id="iframepageKK" onload="changeFrameHeight()"  width="" frameborder="0"></iframe>

</body>

</html>

<script type="text/javascript" language="javascript">
    function changeFrameHeight(){
        var ifm= document.getElementById("iframepageKK");
        ifm.height=document.documentElement.clientHeight;
        ifm.width=document.documentElement.clientWidth-300;
    }
    window.onresize=function(){
        changeFrameHeight();
    }
</script>