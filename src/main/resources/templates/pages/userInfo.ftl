<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
<link href="/layui/css/layui.css" rel="stylesheet"/>
<body>
<!-- 导航 -->

<#include "../common/nav.ftl">

<!-- 主体内容 -->
<div class="main-container container-fluid">
    <!-- 页面内容 -->
    <div class="page-container">
        <!-- 侧边栏导航 -->
       <#include "../common/sider.ftl">
        <!-- /侧边栏导航 -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- 页面面包屑 -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="#">主页</a>
                    </li>
                    <li class="active">${pageTitle}</li>
                </ul>
            </div>
            <!-- /页面面包屑 -->
            <!-- Page Header -->
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                    ${pageTitle}
                    </h1>
                </div>
                <!--Header Buttons-->
                <div class="header-buttons">
                    <a class="sidebar-toggler" href="#">
                        <i class="fa fa-arrows-h"></i>
                    </a>
                    <a class="refresh" id="refresh-toggler" href="">
                        <i class="glyphicon glyphicon-refresh"></i>
                    </a>
                    <a class="fullscreen" id="fullscreen-toggler" href="#">
                        <i class="glyphicon glyphicon-fullscreen"></i>
                    </a>
                </div>
                <!--Header Buttons End-->
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body">
                <h5 class="row-title before-green"><i class="fa fa-tags green"></i>资料修改</h5>
                <div class="row">
                    <div class="col-lg-6 col-sm-6 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">修改密码</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form class="form-horizontal form-bordered" role="form" id="passwordForm">
                                        <input type="text" id="" name="type" value="alterPassword" hidden>
                                        <div class="form-group">
                                            <label for="oldPassword"
                                                   class="col-sm-2 control-label no-padding-right">原密码</label>
                                            <div class="col-sm-10">
                                                <input type="password" class="form-control" name="oldPassword"
                                                       id="oldPassword" value="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="newPassword"
                                                   class="col-sm-2 control-label no-padding-right">新密码</label>
                                            <div class="col-sm-10">
                                                <input type="password" class="form-control" name="newPassword"
                                                       id="newPassword">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="newPasswordV" class="col-sm-2 control-label no-padding-right">确认密码</label>
                                            <div class="col-sm-10">
                                                <input type="password" class="form-control" name="resultPassword"
                                                       id="resultPassword">
                                            </div>
                                        </div>
                                        <div class="form-group text-align-right">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <a class="btn btn-labeled btn-palegreen" id="passwordSubmit">
                                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form class="form-horizontal form-bordered">
                                        <input type="text" name="type" value="alterInfo" hidden>
                                        <div class="form-group">
                                            <label for="userName"
                                                   class="col-sm-2 control-label no-padding-right">姓名</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="userName"
                                                       value="${userInfo.getName()!}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="nickname"
                                                   class="col-sm-2 control-label no-padding-right">昵称</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="userNickname"
                                                       value="${userInfo.getNikeName()!}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="userPhone"
                                                   class="col-sm-2 control-label no-padding-right">手机</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="userPhone"
                                                       value="${userInfo.getPhone()!}">
                                                <small>注意：手机必须真实,如果虚假信息导致拨款无法审核,后果自负！</small>
                                            </div>
                                        </div>
                                        <div class="form-group text-align-right">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <a class="btn btn-labeled btn-palegreen"
                                                   id="infoSubmit">
                                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-lg-6 col-sm-6 col-xs-12">
                        <h5 class="row-title before-red"><i class="fa fa-tags red"></i>头像更换</h5>
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">修改头像 图片不能超过2M 头像290*290</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form class="form-horizontal form-bordered" role="form" id="gravaForm"
                                          enctype="multipart/form-data">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label no-padding-right">当前头像</label>
                                            <div class="col-sm-10">
                                                <img id="nowAvatar" src="${userInfo.getAvatar()!}" alt="赶紧上传自己的头像吧"
                                                     width="80px" height="80px">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label no-padding-right">上传头像</label>
                                            <div class="control-group js_upFile2_box col-sm-10">
                                                <div class="btn-upload  ">
                                                    <button id="image-select"
                                                            class="btn-sm btn btn-info" type="button">选择
                                                    </button>
                                                    <button id="image-upload"
                                                            class="btn-sm btn btn-info" type="button">上传
                                                    </button>
                                                </div>
                                                <div class="upload_showBox js_upFile2_show">
                                                    <img src="/assets/img/upload/headimg.png" id="image-upload-box-two"
                                                         width="290px" height="290px">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group text-align-right">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <a href="javascript:void(0);" class="btn btn-labeled btn-info"
                                                   id="avatarSubmit">
                                                    <i class="btn-label glyphicon glyphicon-open"></i>头像保存
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-xs-12">
                        <h5 class="row-title before-red"><i class="fa fa-tags red"></i>必须上传真实信息！</h5>
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">银行信息</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form class="form-horizontal form-bordered" role="form" id="payForType">
                                        <input type="text" name="type" value="alterInfo" hidden>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label no-padding-right">支付宝账号</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="aliPay"
                                                       value="${userInfo.aliPay!}">
                                                <small>注意：支付宝账号作为拨款的必要凭证，请谨慎修改！</small>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="bankCardNumber" class="col-sm-2 control-label no-padding-right">开户银行</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="bankType"
                                                       value="${userInfo.bankType!}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="bankCardNumber" class="col-sm-2 control-label no-padding-right">开户姓名</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="bankUserName"
                                                       value="${userInfo.bankUserName!}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="bankCardNumber" class="col-sm-2 control-label no-padding-right">银行卡号</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="bankCardNumber"
                                                       value="${userInfo.bankCardNumber!}">
                                                <small>注意：银行卡号作为拨款的必要凭证，请谨慎修改！</small>
                                            </div>
                                        </div>


                                        <div class="form-group text-align-right">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <a href="javascript:void(0);" class="btn btn-labeled btn-palegreen"
                                                   id="payForTypeSubmit">
                                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    function exact() {
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if(!myreg.test("${userInfo.getPhone()}")){
            Notify('请输入正确的手机号码', 'bottom-right', '9000', 'danger', 'fa-warning', true);
            Notify('手机必须真实,如果虚假信息导致其后果自负', 'bottom-right', '9000', 'warning', 'fa-warning', true);
        }
        var aliPay = "${userInfo.getAliPay()!}";
        if(aliPay==""){
            Notify('请输入个人银行信息', 'bottom-right', '9000', 'danger', 'fa-warning', true);
            Notify('银行信息必须真实,如果虚假信息导致其后果自负', 'bottom-right', '9000', 'warning', 'fa-warning', true);
        }

    }
    exact()
    var flot = false;
    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;

        //选完文件后不自动上传
        upload.render({
            elem: '#image-select'
            , url: '/oa/upload/img/user'
            , auto: false
            //,multiple: true
            , bindAction: '#image-upload'
            , done: function (res) {
                if (res.code == 0) {
                    $("#image-upload-box-two").attr('src', res.data.src)
                    flot = true
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });

    });
    $(function () {
        $('#avatarSubmit').click(function () {
            var id = "${userInfo.getUserId()}"
            var avatar = $("#image-upload-box-two").attr("src");
            if (flot) {
                $.post(
                        "/oa/user/avatar/save",
                        {
                            id: id,
                            avatar: avatar
                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            if (res.code == 0) {
                                setTimeout(function () {
                                    location = "/oa/user/info.html";
                                }, 1000);
                            }
                        }
                )
            } else {
                layer.msg("请上传图片......");
            }
        })
    })
    $(function () {
        var check = false;
        $("#infoSubmit").click(function () {
            var name = $("#userName").val();
            var nickName = $("#userNickname").val();
            var phone = $("#userPhone").val();
            if (name == "") {
                layer.msg("姓名不能为空", {
                    time: 1000
                });
            } else if (phone == "") {
                layer.msg("手机号码不能为空", {
                    time: 1000
                });
            } else if (nickName == "") {
                layer.msg("昵称不能为空", {
                    time: 1000
                });
            }  else if (!check) {
                layer.msg("输入正确的手机号", {
                    time: 1000
                });
            } else {
                $.post(
                        "/oa/user/info/save",
                        {
                            name: name,
                            phone: phone,
                            nikeName: nickName
                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            if (res.code == 0) {
                                setTimeout(function () {
                                    location = "/oa/user/info.html";
                                }, 1000);
                            }
                        }
                )
            }

        })
        $("#userPhone").blur(function () {
            if ($("#userPhone").val() != "") {
                $.post(
                        "/oa/user/phone",
                        {
                            phone: $("#userPhone").val()
                        },
                        function (res) {

                            if (res.data.code != 0) {
                                flog = true
                                layer.msg(res.data.message, {
                                    time: 1000
                                });
                            }
                            if (res.data.code == 0) {
                                check = true
                                layer.msg(res.data.message, {
                                    time: 1000
                                });
                            }

                        }
                )
            }
        })
    })
    $(function () {
        $("#passwordSubmit").click(function () {
            var oldPassword = $("#oldPassword").val();
            var newPassword = $("#newPassword").val();
            var resultPassword = $("#resultPassword").val();
            if (oldPassword == "") {
                layer.msg("原密码不能为空！", {
                    time: 1000
                });
            } else if (newPassword == "") {
                layer.msg("新密码不能为空！", {
                    time: 1000
                });
            } else if (resultPassword == "") {
                layer.msg("请再次输入密码！", {
                    time: 1000
                });
            } else if (newPassword != resultPassword) {
                layer.msg("两次密码输入不同！", {
                    time: 1000
                });
            } else {
                $.post(
                        "/oa/user/password/save",
                        {
                            oldPassword:oldPassword,
                            newPassword:newPassword

                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            if (res.code == 0) {
                                setTimeout(function () {
                                    location = "/oa/user/info.html";
                                }, 1000);
                            }
                        }
                )
            }
        })
    })

    $(function () {
        $("#payForTypeSubmit").click(function () {
            var aliPay = $("#aliPay").val();
            var bankCardNumber = $("#bankCardNumber").val();
            var bankUserName = $("#bankUserName").val();
            var bankType = $("#bankType").val();
            if (aliPay == "") {
                layer.msg("支付宝账号不能为空！", {
                    time: 1000
                });
            } else if (bankCardNumber == "") {
                layer.msg("银行卡号不能为空！", {
                    time: 1000
                });
            } else if (bankUserName == "") {
                layer.msg("开户姓名不能为空！", {
                    time: 1000
                });
            } else if (bankType=="") {
                layer.msg("开户银行不能为空！", {
                    time: 1000
                });
            } else {
                $.post(
                        "/oa/user/bank/save",
                        {
                            aliPay:aliPay,
                            bankCardNumber:bankCardNumber,
                            bankUserName:bankUserName,
                            bankType:bankType

                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            if (res.code == 0) {
                                setTimeout(function () {
                                    location = "/oa/user/info.html";
                                }, 1000);
                            }
                        }
                )
            }
        })
    })
</script>
<script>

</script>


</body>
</html>