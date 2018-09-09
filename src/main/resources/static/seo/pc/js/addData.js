$(function () {
    $.post(
        "/pc/comment",
        {
            articleId: ,
            state: state,
            integral: text,
            type:type

        },
        function (data) {
            if (data.code == 0) {
                layer.msg(data.message);
                setTimeout(function () {
                    // location = "/oa/article/auditlist?type="+resultType
                    var url = window.location.pathname;
                    var search = window.location.search;
                    location = url + search
                }, 100)
            }
            if (data.code > 0) {
                layer.msg(data.message);
            }
        }
    )
})