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
                <div class="row">
                    <div class="col-lg-12 col-sm-6 col-xs-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div id="registration-form">
                                    <form role="form">
                                        <div class="form-title">基本信息</div>
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label>工号ID</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" disabled value="系统分配" class="form-control">
                                                    <i class="fa fa-user"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="name">姓名（不可修改）</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" name="name" id="name" class="form-control"
                                                           value="${userInfo.getName()!}"  <#if edit==1>disabled</#if>>
                                                    <i class="glyphicon glyphicon-fire"></i>
                                                </span>
                                            </div>

                                            <div class="form-group">
                                                <label for="status">状态</label>
                                                <span class="input-icon icon-right">
                                                    <select id="status" name="status" style="width:100%;">
                                                            <option value="1"
                                                                    <#if userInfo.getStatus()! == 1>selected</#if>>在职</option>
                                                            <option value="0"
                                                                    <#if userInfo.getStatus()! == 0>selected</#if>>离职</option>
                                                    </select>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="role">角色</label>
                                                <span class="input-icon icon-right">
                                                    <select id="role" name="role" style="width:100%;">
                                                        <#if edit ==0>
                                                         <#list roleList as role>
                                                            <option value="${role.getId()}">${role.getDescription()!}</option>
                                                         </#list>
                                                        <#else >
                                                            <#list roleList as role>
                                                            <option value="${role.getId()}"
                                                                    <#if userInfo.getRoleId() == role.getId()>selected</#if>>${role.getDescription()!}</option>
                                                            </#list>
                                                        </#if>

                                                    </select>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">

                                            <div class="form-group">
                                                <label for="phone">电话号码（必填，用于登录）</label>
                                                <span class="input-icon icon-right">
                                                    <input maxlength="11" name="phone" id="phone" type="number"
                                                           class="form-control" value="${userInfo.getPhone()!}">
                                                    <i class="glyphicon glyphicon-earphone"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="nickname">昵称</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" id="nickname" value="${userInfo.getNikeName()!}"
                                                           class="form-control">
                                                    <i class="fa fa-linux"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="password">密码（用于登录）</label>
                                                <span class="input-icon icon-right">
                                                    <input id="password" type="password" class="form-control"
                                                           value="${userInfo.getPassword()!}">
                                                    <i class="fa fa-unlock-alt"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="clear_float"></div>
                                <#--暂时不用上传身份证 要使用去掉最大的div-->


                                    <div class="form-group">
                                        <div class="text-right">
                                            <button class="btn btn-success" href="javascript:void(0);" id="submit"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i> 保存人员
                                            </button>
                                            <a class="btn btn-warning" href="/oa/user/list.html"><i
                                                    class="btn-label glyphicon glyphicon-th-list"></i> 返回列表
                                            </a>
                                        </div>
                                    </div>
                                    <div class="clear_float"></div>
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
<script type="text/javascript">

    var anchorUser = {
        id: "${userInfo.getUserId()!}",
        name: "",
        nickname: "",
        phone: "",
        password: "",
        status: "",
        roleId: ""
    }
    var flog = false
    $("#phone").blur(function () {
        if ($("#phone").val() != "") {
            $.post(
                    "/oa/personnel/phone",
                    {
                        phone: $("#phone").val()
                    },
                    function (res) {

                        if (res.data.code != 0) {
                            flog = true
                            layer.msg(res.data.message, {
                                time: 1000
                            });
                        }
                        if (res.data.code == 0) {
                            flog = false
                            layer.msg(res.data.message, {
                                time: 1000
                            });
                        }

                    }
            )
        }

    })
    $("#submit").click(function () {
        anchorUser.name = $("#name").val();
        anchorUser.phone = $("#phone").val();
        anchorUser.nickname = $("#nickname").val();
        var length = document.getElementById("password").value.length;
        if (length < 20) {

            anchorUser.password = $.md5($("#password").val());

        } else {

            anchorUser.password = $("#password").val();
        }

        anchorUser.status = $("#status").val();
        anchorUser.roleId = $("#role").val();
        if (anchorUser.name == "") {
            layer.msg("姓名不能为空", {
                time: 1000
            });
        } else if (anchorUser.phone == "") {
            layer.msg("手机号码不能为空", {
                time: 1000
            });
        } else if (anchorUser.password == "") {
            layer.msg("密码不能为空", {
                time: 1000
            });
        } else if (flog) {
            layer.msg("手机号码已经重复！", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/user/save",
                    {
                        userId: "${userInfo.getUserId()!}",
                        name: anchorUser.name,
                        nikeName: anchorUser.nickname,
                        phone: anchorUser.phone,
                        password: anchorUser.password,
                        status: anchorUser.status,
                        roleId: anchorUser.roleId
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location = "/oa/user/add.html";
                            }, 100);
                        }
                    }
            )
        }
        $("#save").removeAttr("disabled")


    })
</script>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-one'
            , url: '/upload/img/anchor'
            , auto: false
            //,multiple: true
            , bindAction: '#image-upload-btn-one'
            , done: function (res) {
                if (res.code == 0) {
                    $("#image-upload-box-one").attr('src', res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-two'
            , url: '/upload/img/anchor'
            , auto: false
            //,multiple: true
            , bindAction: '#image-upload-btn-two'
            , done: function (res) {
                if (res.code == 0) {
                    $("#image-upload-box-two").attr('src', res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-three'
            , url: '/upload/img/anchor'
            , auto: false
            //,multiple: true
            , bindAction: '#image-upload-btn-three'
            , done: function (res) {
                if (res.code == 0) {
                    $("#image-upload-box-three").attr('src', res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
    });
</script>
</body>
</html>