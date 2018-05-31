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
                            ${pageTitle}
                            </div>
                            <div class="form-group">
                                <div class="text-right">
                                    <a href="/oa/role/index.html" class="btn btn-success"
                                       name="pullModel-add_area_row">添加角色</a>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>#ID</th>
                                        <th>名称</th>
                                        <th >描述</th>
                                        <th >级别</th>
                                        <th >权限</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getPageContent() as r>
                                <tr id="${r.id}" <#if r.id%2!=0>class="success"</#if>>
                                    <td>${r.id}</td>
                                    <td>${r.name}</td>
                                    <td>${r.description}</td>
                                    <td>${r.level}</td>
                                    <td>
                                       <#list r.getPermissionList() as per>
                                           ${per.name} &nbsp;
                                       </#list>

                                    </td>
                                    <td>
                                        <a href="/oa/role/index.html?id=${r.id}" target="_self" name="pullModel-row_edit_character" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                    <#--<a  onclick="dele(${r.id})"  class="btn btn-danger btn-xs edit"><i class="fa fa-edit"></i> 删除</a>-->
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
</body>
</html>