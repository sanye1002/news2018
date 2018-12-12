function cleanRedisCacheNews() {
    alert(123)
    $.get(
        "http://www.immnc.com/moon/article/redis/evict/banner",
        {
        },
        function (data) {
            alert(data.msg);
            if (data.code == 0) {

            } else {
                layer.msg(data.msg);
            }

        }
    )
}