<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
<body>
<!-- 导航 -->

<div class="login-container animated fadeInDown">
    <div class="loginbox bg-white" style="height: auto !important;">
        <br><br>
        <div class="loginbox-title">
            <i class="fa fa-sign-in"></i>
            <span>注册用户</span>
        </div>
        <br>
        <div class="loginbox-social">
            <div class="social-title">
                <i class="fa fa-quote-left"></i>

                <span>请在下方键入你的信息</span>

                <i class="fa fa-quote-right"></i>
            </div>
        </div>
        <form id="loginForm">

            <div class="loginbox-textbox">
                <input type="text" id="phone" name="phone" class="form-control" placeholder="手机" autocomplete="off"/>
            </div>
            <div class="loginbox-forgot">
                <a id="send-code">
                    <span>发送验证码到手机</span>
                </a>
            </div>
            <div style="display: none" id="box-code">
                <div class="loginbox-textbox">
                    <input type="password" id="code" name="code" class="form-control" placeholder="输入验证码"
                           autocomplete="off"/>
                </div>
                <div class="loginbox-submit text-right">
                    <button id="code-submit" type="button" class="btn btn-sky">
                        <i class="fa fa-sign-in"></i>
                        <span>确认验证码</span>
                    </button>
                </div>
            </div>
            <div style="display: none" id="box-password">
                <div class="loginbox-textbox">
                    <input type="text" id="name" name="password2" class="form-control" placeholder="真实姓名"
                           autocomplete="off"/>
                </div>
                <div class="loginbox-textbox">
                    <input type="text" id="nickname" name="password2" class="form-control" placeholder="昵称"
                           autocomplete="off"/>
                </div>
                <div class="loginbox-textbox">
                    <input type="password" id="password1" name="password1" class="form-control" placeholder="密码"
                           autocomplete="off"/>
                </div>
                <div class="loginbox-textbox">
                    <input type="password" id="password2" name="password2" class="form-control" placeholder="确认密码"
                           autocomplete="off"/>
                </div>
                <div class="loginbox-submit text-right">
                    <button id="submit" type="button" class="btn btn-sky">
                        <i class="fa fa-sign-in"></i>
                        <span>提交</span>
                    </button>
                </div>
            </div>

        </form>
        <br>
        <div class="loginbox-signup">
            <a href="/login.html" class="register-btn">
                <i class="fa fa-phone"></i>
                <span>退回登录</span>
            </a>
        </div>
        <br>
    </div>
</div>
<!-- 主体内容 -->


<#include "../common/footjs.ftl">
<script>
    $(function () {
        $("#send-code").click(function () {
            var phone = $("#phone").val();
            if (phone != "") {
                if (!(/^1[3456789]\d{9}$/.test(phone))) {
                    $("#phone").parent().addClass("has-error");
                    layer.msg("请输入正确的手机号！", {
                        time: 1000
                    });
                } else {
                    $("#phone").parent().removeClass("has-error");
                    $("#phone").parent().addClass("has-success");
                    //post请求
                    $.post(
                            "/checkPhone",
                            {
                                phone: phone,
                                type:0
                            }, function (res) {
                                if (res.code == 0) {
                                    $("#send-code").hide(100);
                                    $("#phone").attr("disabled", "disabled");
                                    $("#box-code").show(100)
                                    layer.msg(res.data.message, {
                                        time: 1000
                                    });
                                } else {

                                    $("#phone").parent().removeClass("has-success");
                                    $("#phone").parent().addClass("has-error");
                                    layer.msg(res.message, {
                                        time: 1000
                                    });
                                }

                            }
                    )


                }
            } else {
                $("#phone").parent().addClass("has-error");
                $("#box-code").hide(100)
                layer.msg("亲输入手机号码！", {
                    time: 1000
                });
            }


        });
        $("#code-submit").click(function () {
            var code = $("#code").val();
            if (code != "") {
                $.post(
                        "/checkCode",
                        {code: code},
                        function (res) {

                            if (res.code == 0) {
                                $("#code-submit").parent().hide(100);
                                $("#code").attr("disabled", "disabled");
                                $("#box-password").show(100)
                                $("#code").parent().addClass("has-success");
                                layer.msg(res.data.message, {
                                    time: 1500
                                });
                            } else {
                                $("#code").parent().removeClass("has-success");
                                $("#code").parent().addClass("has-error");
                                layer.msg(res.message, {
                                    time: 1500
                                });
                            }
                        }
                )
            } else {
                $("#code").parent().addClass("has-error");
                layer.msg("请输入验证码！", {
                    time: 1500
                });
            }
        })
    });
</script>
<script type="text/javascript">

    $(function () {

        $("#submit").click(function () {
            var phone = $("#phone").val();
            var password1 = $("#password1").val();
            var password2 = $("#password2").val();
            var name = $("#name").val();
            var nickname = $("#nickname").val();
            if (phone == "") {
                layer.msg("手机号码不能为空，请重新输入！", {
                    time: 1500
                });
            } else if (password1 == "") {
                layer.msg("密码不能为空，请重新输入！", {
                    time: 1500
                });
            }else if (password2 == "") {
                layer.msg("密码不能为空，请重新输入！", {
                    time: 1500
                });
            }else if (name == "") {
                $("#name").parent().addClass("has-error");
                layer.msg("真实姓名不能为空，请重新输入！", {
                    time: 1500
                });
            } else if (nickname == "") {
                $("#nickname").parent().addClass("has-error");
                layer.msg("昵称不能为空，请重新输入！", {
                    time: 1500
                });
            } else if (password1 != password2) {
                $("#password1").parent().addClass("has-error");
                $("#password2").parent().addClass("has-error");
                layer.msg("两次密码不同！", {
                    time: 1500
                });
            } else {
                var password = $.md5($("#password1").val())
                $.post(
                        "/register/user",
                        {
                            phone: phone,
                            name: name,
                            nikeName: name,
                            password: password
                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1500
                            });
                            if (res.code == 0) {
                                $("#name").parent().removeClass("has-error");
                                $("#nickname").parent().removeClass("has-error");
                                $("#password1").parent().removeClass("has-error");
                                $("#password2").parent().removeClass("has-error");
                                setTimeout(function () {
                                    location = "/login.html"
                                },500)
                            }else {
                                setTimeout(function () {
                                    location = "/register-user.html"
                                },1000)
                            }

                        }
                )
            }

        })

    })
</script>
</body>
</html>