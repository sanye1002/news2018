<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>陌陌公会</title>
</head>
<body style="margin: 0;padding: 0">
<iframe src="https://oauth.immomo.com/oauth/authorize?response_type=code&client_id=cab6b9b2bf8677a5&state=live-guildewurfwe9234" id="iframepage"  onload="changeFrameHeight()" width="" frameborder="0"></iframe>

</body>

</html>

<script type="text/javascript" language="javascript">
    function changeFrameHeight(){
        var ifm= document.getElementById("iframepage");
        ifm.height=document.documentElement.clientHeight;
        ifm.width=document.documentElement.clientWidth-300;
    }
    window.onresize=function(){
        changeFrameHeight();
    }
</script>