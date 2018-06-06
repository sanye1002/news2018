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
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            个人奖励记录
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>文章标题</th>
                                        <th>文章类型</th>
                                        <th>奖励积分</th>
                                        <th>时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getArticleTitle()!}</td>
                                            <td>${mm.getArticleType()!}</td>
                                            <td>
                                                ${mm.getRewardIntegral()!}
                                            </td>
                                            <td>
                                                ${mm.getCreateTime()!}
                                            </td>
                                            <td>
                                               无操作
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
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    function tiXian(password,id) {
        layer.prompt({title: '输入登录密码，并确认', formType: 1}, function(pass, index){
            if ($.md5(pass)!=password){
                layer.msg('密码错误！！！',{
                    time:1200
                });
            }else {
                layer.close(index);

                layer.prompt({title: '请输入提现金额，并确认', formType: 0}, function(text, index){

                    layer.close(index);
                    /* 提现present remark advanceSalary*/
                    $.post(
                            "/oa/reward/withdraw",
                            {
                                "withdrawSalary":text,"userId":id

                            },
                            function (res) {

                                if (res.code == 0){
                                    layer.msg('金额：'+text+',提现申请中！;',{
                                        time:1500
                                    });
                                }
                                if (res.code > 0){
                                    layer.msg(res.message,{
                                        time:1500
                                    });
                                }
                            }
                    )

                });
            }
        });
    }
</script>

</body>
</html>