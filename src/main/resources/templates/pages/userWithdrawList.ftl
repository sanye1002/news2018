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
                                个人积分
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>用户名</th>
                                        <th>手机号码</th>
                                        <th>总积分</th>
                                        <th>剩余积分</th>
                                        <th>剩余可提现</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                        <tr id="${pointsReward.getId()}">
                                            <td>${pointsReward.getUsername()!}</td>
                                            <td>${pointsReward.getPhone()!}</td>
                                            <td>${pointsReward.getAllIntegral()!}</td>
                                            <td>${pointsReward.getLaveIntegral()!}</td>
                                            <td>${pointsReward.getLaveSalary()!}</td>
                                            <td>
                                                <a id="tiXian" onclick="tiXian('${user.getPassword()}',${pointsReward.getUserId()})" class="btn btn-sky btn-sm  btn-follow">
                                                    <i class="fa fa-arrow-circle-o-right"></i>
                                                    提现
                                                </a>
                                            </td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            个人提现记录
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>申请时间</th>
                                        <th>提现金额</th>
                                        <th>审核状态</th>
                                        <th>审核结果</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getCreateTime()!}</td>
                                            <td>${mm.getWithdrawSalary()!}</td>
                                            <td>
                                                <#if mm.getCheckStatus()==0>
                                                    未审核
                                                </#if>
                                                <#if mm.getCheckStatus()==1>
                                                    已审核
                                                </#if>
                                            </td>
                                            <td>
                                                <#if mm.getResultStatus()==0>
                                                    未通过
                                                </#if>
                                                <#if mm.getResultStatus()==1>
                                                    已拨款
                                                </#if>
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