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
                            <#--<div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="selectType">
                                        <option value="1" <#if disposeState==1>selected</#if> >已处理 <span
                                                class="badge">${OnDispose}</span></option>
                                        <option value="0" <#if disposeState==0>selected</#if> >未处理 <span
                                                class="badge">${UnDispose}</span></option>
                                    </select>
                                </div>
                            </div>-->

                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>申请人</th>
                                        <th>昵称</th>
                                        <th>原因</th>
                                        <th>申请时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getId()}">
                                    <td>${p.getName()!}</td>
                                    <td>${p.getNikeName()!}</td>
                                    <td>${p.getReason()}</td>
                                    <td>${p.getTime()}</td>
                                    <td>

                                        <a class="btn btn-danger btn-xs"
                                           onclick="audit(2,${p.getId()})"><i
                                                class="btn-label glyphicon glyphicon-ok"></i>
                                            通过</a>
                                        <a class="btn btn-danger btn-xs"
                                           onclick="audit(3,${p.getId()})"><i
                                                class="fa fa-times"></i>
                                            未通过</a>

                                    </td>

                                </tr>
                                </#list>
                                    </tbody>
                                </table>
                            </div>


                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}">上一页</a>
                                                </li>

                                            </#if>

                                               <#if pageContent.getTotalPages() lte 5>
                                                   <#list 1..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="${url}?page=${index}&size=${size}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif currentPage lte 3>
                                                   <#list 1..5 as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="${url}?page=${index}&size=${size}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt 3 && currentPage lte pageContent.getTotalPages()-2>
                                                   <#list currentPage-2..currentPage+2 as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt pageContent.getTotalPages()-2>
                                                   <#list pageContent.getTotalPages()-4..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               </#if>


                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}">尾页</a>
                                        </li>
                                    </ul>
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
<script>



    //举报处理
    function audit(auditState, id) {
        $.post(
                "/oa/author/audit",
                {
                    auditState: auditState,
                    id: id
                },
                function (data) {
                    if (data.code == 0) {
                        var url = window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = url + search
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }


</script>
</body>
</html>