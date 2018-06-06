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
            <span>登录</span>
        </div>
        <br>
        <div class="loginbox-social">
            <div class="social-title">
                <i class="fa fa-quote-left"></i>

                <span>请在下方键入你的登录信息</span>

                <i class="fa fa-quote-right"></i>
            </div>
        </div>
        <form id="loginForm">

            <div class="loginbox-textbox">
                <input type="text" id="phone" name="phone" class="form-control" placeholder="手机" autocomplete="off"/>
            </div>
            <div class="loginbox-textbox">
                <input type="password" id="password" name="password" class="form-control" placeholder="密码"
                       autocomplete="off"/>
            </div>
            <div class="loginbox-textbox">
                <label>
                    <input type="checkbox" id="code" class="colored-danger">
                    <span class="text">登录安全事项</span>
                </label>
            </div>

            <div class="loginbox-forgot">
                <a href="/recover-password.html">
                    <span>忘记密码</span>
                    <i class="fa fa-question"></i>
                </a>
            </div>
            <div class="loginbox-submit text-right">
                <button id="submit" type="button" class="btn btn-sky">
                    <i class="fa fa-sign-in"></i>
                    <span >登录</span>
                </button>
            </div>
        </form>
        <br>
        <div class="loginbox-signup">
            <a href="/register-user.html" class="register-btn">
                <i class="fa fa-phone"></i>
                <span>通过手机注册</span>
            </a>
        </div>
        <br>
    </div>
</div>
<!-- 主体内容 -->


<#include "../common/footjs.ftl">
<script>

</script>
<script type="text/javascript">
    document.onkeydown = function (event) {
        var e = event || window.event;
        if (e && e.keyCode == 13) { //回车键的键值为13
            login(); //调用登录按钮的登录事件
        }
    };
    function login() {
        var phone = $("#phone").val();
        var password = $("#password").val();
        var code = $("#code:checked").val();


        if (phone == "") {
            layer.msg("手机号码不能为空，请重新输入！", {
                time: 1000
            });

        } else if (password == "") {
            layer.msg("密码不能为空，请重新输入！", {
                time: 1000
            });
            return false;
        } else if (code == undefined) {
            layer.msg("请勾选登录安全事项！", {
                time: 1000
            });
        } else {
            $.post(
                    "/account/sign-in",
                    {
                        phone: phone,
                        password: password
                    },
                    function (res) {
                        if (res.code==0){
                            layer.msg(res.data.message, {
                                time: 1000
                            });
                            if(res.data.code==0){
                                var url =  window.location.pathname;
                                var search = window.location.search;
                                setTimeout(function () {
                                    if (url=="/login.html"||url=="/login"){
                                        location="/oa/user/index.html"
                                    }else {
                                        location=url+search
                                    }
                                },500)
                            }
                        }else {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }

                    }
            )
        }
    }

    $(function () {
        $("#submit").click(function () {
            login()
        })
    })
</script>
</body>
</html>