<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title!'妙漫网创'}</title>
    <meta name="description" content="${description! '网创新闻,原创,妙漫网创'}"/>
    <meta name="keywords" content="${keywords!'全新的原创新闻网,网创,新闻,原创,妙漫网创,推荐,科技,娱乐,游戏,搞笑,汽车,军事,文史,社会,体育'}"/>
</head>
<body>
<div style="text-align: center">
    ${content!}
</div>
</body>
<!--Basic Scripts-->
<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script>
    function forDetailById() {
        location = "${url}"
    }
    forDetailById()
</script>
</html>