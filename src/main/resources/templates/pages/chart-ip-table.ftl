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

                            <div style="float:left;margin-left: 2px">
                                <div class="form-group">
                                    <label for="anchorUser">文章类型</label>
                                    <span class="input-icon icon-right">
                                        <#--<select id="e1" style="width:100%;">
                                            <option value="0" <#if article.getClassifyId()! == 0>selected</#if>>请选择</option>
                                                <#list classify as c>
                                                    <option value=${c.getId()} <#if article.getClassifyId()! == c.getId()>selected</#if>>
                                                            ${c.getClassify()}
                                                    </option>
                                                </#list>
                                        </select>-->
                                        <i class="glyphicon glyphicon-fire"></i>
                                    </span>
                                </div>
                            </div>

                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="classifySelectType">
                                        <option value="0" <#if onValue == 0>selected</#if>>address<span
                                                class="badge">&nbsp;${addressAll}</span></option>
                                    <#list addressKey as c>
                                        <option value="${c}" <#if c == onValue >selected</#if>>
                                            ${c}<span class="badge">${addressCount[c_index]}
                                        </span>
                                        </option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="classifySelectType">
                                        <option value="0" <#if onValue == 0>selected</#if>>browser<span
                                                class="badge">&nbsp;${browserAll}</span></option>
                                        <#list browserKey as c>
                                            <option value="${c}" <#if c ==onValue >selected</#if>>
                                                ${c}<span class="badge">${browserCount[c_index]}
                                            </span>
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="classifySelectType">
                                        <option value="0" <#if onValue == 0>selected</#if>>util<span
                                                class="badge">&nbsp;${utilAll}</span></option>
                                    <#list utilKey as c>
                                        <option value="${c}" <#if c ==onValue >selected</#if>>
                                            ${c}<span class="badge">${utilCount[c_index]}
                                        </span>
                                        </option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>ip</th>
                                        <th>时间</th>
                                        <th>浏览器</th>
                                        <th>地区</th>
                                        <th>访问工具</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list pageContent.getPageContent() as ipData>
                                    <tr id="${ipData.getId()}">
                                        <td>${ipData.getIp()}</td>
                                        <td>${ipData.getTime()}</td>
                                        <td>${ipData.getBrowser()}</td>
                                        <td>${ipData.getAddress()}</td>
                                        <td>${ipData.getUtil()}</td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>


                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&year=${year}&month=${month}&day=${day}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}&year=${year}&month=${month}&day=${day}">上一页</a>
                                                </li>

                                            </#if>

                                               <#if pageContent.getTotalPages() lte 5>
                                                   <#list 1..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li>
                            <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}">${index}</a>
                        </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif currentPage lte 3>
                                                   <#list 1..5 as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li>
                            <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}">${index}</a>
                        </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt 3 && currentPage lte pageContent.getTotalPages()-2>
                                                   <#list currentPage-2..currentPage+2 as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt pageContent.getTotalPages()-2>
                                                   <#list pageContent.getTotalPages()-4..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               </#if>


                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}&year=${year}&month=${month}&day=${day}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&year=${year}&month=${month}&day=${day}">尾页</a>
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

</script>
</body>
</html>