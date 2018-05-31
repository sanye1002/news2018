<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
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
                    <li class="active"> ${pageTitle}</li>
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
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                                <span class="badge badge-sky badge-square">
                                     提示：点击编辑可查看详细信息
                                    </span>
                            </div>
                            <form class="search" method="get">
                                <div style="float:right;margin-right:2px;">
                                    <a href="/oa/user/add.html" target="_self" class="btn btn-success">添加用户</a>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <span>状态：</span>
                                            <label>
                                                <input type="checkbox" class="colored-blue" value="1"
                                                <#if status == 1>checked="checked"</#if>>
                                                <span class="text">正常</span>
                                            </label>
                                            <label>
                                                <input type="checkbox" value="0" class="inverted"
                                                <#if status == 0>checked="checked"</#if>>
                                                <span class="text">离职</span>
                                            </label>

                                        </div>


                                    </div>
                                </div>
                            </form>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>

                                        <th>头像</th>
                                        <th>姓名</th>
                                        <th>昵称</th>
                                        <th>电话</th>
                                        <th colspan="4">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getContent() as items>
                                <tr id="${items.getUserId()}" style="height: 50px;text-align: center">

                                    <td><img src="${items.getAvatar()!}" height="40px"></td>
                                    <td>${items.getName()!}</td>
                                    <td>${items.getNikeName()!}</td>
                                    <td>${items.getPhone()!}</td>
                                    <td>
                                        <a href="/oa/user/add.html?id=${items.getUserId()}"
                                           class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                    </td>
                                    <td>
                                        <a class="btn btn-danger btn-xs edit" onclick="dele(${items.getUserId()})"><i
                                                class="glyphicon glyphicon-trash"></i> 删除</a>
                                    </td>

                                </tr>
                                </#list>
                                    </tbody>
                                </table>
                            </div>

                            <#include "../common/page.ftl">
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
<script>
    $(function () {
        $("input[type=checkbox]").click(function () {
            var id = $(this).val();
            location = "/oa/user/list.html?status=" + id;

        })
    })

    function dele(id) {
        layer.confirm('客官确定要进行删除？', {
            btn: ['确定', '再想想'] //按钮
        }, function () {
            $.post(
                    "/oa/user/delete",
                    {userId: id},
                    function (res) {
                        if (res.code == 0) {

                            $("#" + id).remove();
                            layer.msg('已删除', {icon: 1});

                        }
                        if (res.code != 0) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                    }
            )

        });
    }
</script>
<script src="/layui/layui.js" charset="utf-8"></script>

</body>
</html>