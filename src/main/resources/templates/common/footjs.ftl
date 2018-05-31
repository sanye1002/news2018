<!--音乐-->
<audio id="notice-mp3" style="display: none;" controls="controls" autoplay">
   <source src="/mp3/notice2.mp3"/>
</audio>

<!--Basic Scripts-->
<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script src="/assets/js/layer/layer.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<!--Beyond Scripts-->
<script src="/assets/js/beyond.min.js"></script>
<script src="/assets/js/md5.js"></script>
<script src="/assets/js/toastr/toastr.js"></script>
<script src="/assets/js/select2/select2.js"></script>
<script src="/assets/js/laydate/laydate.js"></script>
<script src="/assets/js/editors/summernote/summernote.js"></script>
<script src="/assets/js/tagsinput/bootstrap-tagsinput.js"></script>

<!--此处补充js文件-->
<script src="/assets/js/fuelux/wizard/wizard-custom.js"></script>

<script type="text/javascript">
    $(function () {
        function LifeShow() {
            /* $.ajax({
             type          : 'get',
             async         : false,
             url           : 'http://api.k780.com:88/?app=ip.get&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
             dataType      : 'jsonp',
             jsonp         : 'callback',
             jsonpCallback : 'data',
             success       : function(data){
             if(data.success!='1'){
             alert(data.msgid+' '+data.msg);
             exit;
             }
             //遍历


             $("#address").html(data.result.att);
             }
             });*/
            $.ajax({
                type: 'get',
                async: false,
                url: 'http://api.k780.com:88/?app=weather.today&weaid=265&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
                dataType: 'jsonp',
                jsonp: 'callback',
                jsonpCallback: 'data',
                success: function (data) {
                    if (data.success != '1') {
                        alert(data.msgid + ' ' + data.msg);
                        exit;
                    }
                    //遍历天气
                    $("#address").html("中国 " + data.result.citynm);
                    $("#weather").html("温度:" + data.result.temperature_curr + "状况：" + data.result.weather + "风级：" + data.result.wind)
                    $("#currentTime").html(data.result.days);
                    $("#temperature").html(data.result.temperature);
                    $("#currentTimes").html(data.result.week);
                    $("#weekTime").html(data.result.week);
                }
            });
            /* $.ajax({
             type          : 'get',
             async         : false,
             url           : 'http://api.k780.com:88/?app=life.time&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
             dataType      : 'jsonp',
             jsonp         : 'callback',
             jsonpCallback : 'data',
             success       : function(data){
             if(data.success!='1'){
             alert(data.msgid+' '+data.msg);
             exit;
             }
             //遍历



             $("#currentTimes").html(data.result.week_2);
             $("#weekTime").html(data.result.week_4);
             }
             });*/
        }

        // LifeShow();

    });

    $(function () {
        function getUser() {
            $.post(
                    "/oa/user/info",
                    function (res) {
                        if (res.code == 0) {
                            $("#user-username-1").html("用户名称:" + res.data.user.name);
                            $("#user-username-2").html(res.data.user.name);
                            $("#user-phone").html("手机号码:" + res.data.user.phone);
                            var avatar = res.data.user.avatar;
                            if (avatar == null) {
                                avatar = "/layui/images/model.jpg";
                            }
                            //getNewNotice();
                            $("#user-avatar-1").attr('src', avatar);
                            $("#user-avatar-2").attr('src', avatar);
                        }
                    }
            )
        }

        getUser()

        function showMp3() {
            var player = $("#notice-mp3")[0]; /*jquery对象转换成js对象*/
            Notify('你有新的通知！', 'bottom-right', '3000', 'blue', 'fa-check', true);
            $("#notice-box").addClass("open");
            if (player.paused){ /*如果已经暂停*/
                player.play(); /*播放*/
                setTimeout(function () {
                    player.pause();
                },3000)
            }else {
                player.pause();/*暂停*/
            }
        }
        function getNewNotice() {
            $.post(
                    "/oa/notice/find/new/recording",
                    function (res) {
                        if (res.data.code == 0) {
                            var notice = eval(res.data.notice)
                            console.log(notice)
                            $("#notice-number").html(notice.length);
                            if (notice.length>0){
                                $("#notice-number").addClass("badge-darkorange");
                                $("#notice-number").addClass("graded");
                                showMp3()
                            }
                            for (var i in notice) {
                                $("#notice-list").append("<li>\n" +
                                        "                                    <a href=\"/oa/notice/system/read.html?id="+notice[i].id+"\">\n" +
                                        "                                        <img src=\"/layui/images/model.jpg\" class=\"message-avatar\"\n" +
                                        "                                             alt=\"Divyia Austin\">\n" +
                                        "                                        <div class=\"message\">\n" +
                                        "                                                <span class=\"message-sender\">"+notice[i].detail.userName+
                                        "                                                </span>\n" +
                                        "                                            <span class=\"message-time\">" +notice[i].detail.createTime+
                                        "                                                    " +
                                        "                                                </span>\n" +
                                        "                                            <span class=\"message-subject\">"+notice[i].detail.title +
                                        "                                                    " +
                                        "                                                </span>\n" +
                                        "                                        </div>\n" +
                                        "                                    </a>\n" +
                                        "                                </li>")
                            }
                        }else {
                            $("#notice-number").removeClass("badge-darkorange");
                            $("#notice-number").removeClass("graded");
                            $("#notice-number").html(0);
                        }
                    }
            )
        }

    })

</script>