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
                                    <span class="input-icon icon-right" style="width: 80px">
                                        <select id="e1" style="width:100%;">
                                            <#list manyYear as y>
                                                <option value=${y} <#if year! == y>selected</#if>>
                                                    ${y}
                                                </option>
                                            </#list>
                                        </select>
                                    </span>
                                    <label for="anchorUser">年</label>
                                </div>
                            </div>
                            <div style="float:left;margin-left: 2px">
                                <div class="form-group">
                                    <span class="input-icon icon-right" style="width: 80px">
                                        <select id="e2" style="width:100%;">
                                            <#list 1..12 as m>
                                                <option value=${m} <#if month! == m>selected</#if>>
                                                    ${m}
                                                </option>
                                            </#list>
                                        </select>

                                    </span>
                                    <label for="anchorUser">月</label>
                                </div>
                            </div>
                            <div style="float:left;margin-left: 2px">
                                <div class="form-group">
                                    <span class="input-icon icon-right" style="width: 80px">
                                        <select id="e3" style="width:100%;" >
                                            <#list manyDay as d>
                                                <option value=${d} <#if day! == d>selected</#if>>
                                                    ${d}
                                                </option>
                                            </#list>
                                        </select>
                                    </span>
                                    <label for="anchorUser">日</label>
                                </div>
                            </div>

                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="address">
                                        <option value="0" <#if onValue == "0">selected</#if>>地区<span
                                                class="badge">&nbsp;${addressAll}</span></option>
                                    <#list addressKey as c>
                                        <option value="${c}" <#if c == onValue >selected</#if>>
                                            ${c}
                                            <span class="badge">&nbsp;${addressCount[c_index]}
                                        </span>
                                        </option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="browser">
                                        <option value="0" <#if onValue == "0">selected</#if>>浏览器内核<span
                                                class="badge">&nbsp;${browserAll}</span></option>
                                        <#list browserKey as c>
                                            <option value="${c}" <#if c ==onValue >selected</#if>>
                                                ${c}<span class="badge">&nbsp;${browserCount[c_index]}
                                            </span>
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="util">
                                        <option value="0" <#if onValue == "0">selected</#if>>访问工具<span
                                                class="badge">&nbsp;${utilAll}</span></option>
                                    <#list utilKey as c>
                                        <option value="${c}" <#if c == onValue >selected</#if>>
                                            ${c}<span class="badge">&nbsp;${utilCount[c_index]}
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
                                        <td>${ipData.getTime()!}</td>
                                        <td>${ipData.getBrowser()!}</td>
                                        <td>${ipData.getAddress()!}</td>
                                        <td>${ipData.getUtil()!}</td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>


                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">上一页</a>
                                                </li>

                                            </#if>

                                               <#if pageContent.getTotalPages() lte 5>
                                                   <#list 1..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li>
                            <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">${index}</a>
                        </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif currentPage lte 3>
                                                   <#list 1..5 as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li>
                            <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">${index}</a>
                        </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt 3 && currentPage lte pageContent.getTotalPages()-2>
                                                   <#list currentPage-2..currentPage+2 as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt pageContent.getTotalPages()-2>
                                                   <#list pageContent.getTotalPages()-4..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li>
                                    <a href="${url}?page=${index}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">${index}</a>
                                </li>
                                                       </#if>
                                                   </#list>
                                               </#if>


                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&year=${year}&month=${month}&day=${day}&onValue=${onValue}&table=${table}">尾页</a>
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
    $("#e1").select2()
    $("#e2").select2()
    $("#e3").select2()


    $(function () {

        var year = $("#e1").find("option:checked").val();
        var month = $("#e2").find("option:checked").val();
        var day = $("#e3").find("option:checked").val();

        //月份切换改变天数
        $("#e2").change(function () {
            month = $(this).find("option:checked").val();
            year = $("#e1").find("option:checked").val();
            var curMonthDays = new Date(year, month, 0).getDate();
            var opt = $("#e3").find("option")
            for (var o=0;o<opt.length;o++){
                opt.eq(o).remove()
            }
            for (var i=0;i<curMonthDays;i++){
                var op = '<option value="'+(i+1)+'">'+(i+1)+'</option>'
                $("#e3").append(op)
            }
        });

        $("#e3").change(function () {
            day = $(this).find("option:checked").val();
            var onValue = "${onValue}"
            location = "/oa/chart/ip/list?onValue="+onValue+"&year="+year+"&month="+month+"&day="+day
        })



        $("#address").change(function () {
             var onValue = $(this).find("option:checked").val();
             location = "/oa/chart/ip/list?onValue="+onValue+"&table=address&year="+year+"&month="+month+"&day="+day
        })
        $("#browser").change(function () {
            var onValue = $(this).find("option:checked").val();
            location = "/oa/chart/ip/list?onValue="+onValue+"&table=browser&year="+year+"&month="+month+"&day="+day
        })
        $("#util").change(function () {
            var onValue = $(this).find("option:checked").val();
            location = "/oa/chart/ip/list?onValue="+onValue+"&table=util&year="+year+"&month="+month+"&day="+day
        })


    })
</script>
</body>
</html>