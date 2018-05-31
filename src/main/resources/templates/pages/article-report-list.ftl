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
                        <div style="float:right;margin-right:2px;">

                            <div class="form-group">
                                <select id="selectType">
                                    <option value="1" <#if disposeState==1>selected</#if> >已处理 <span
                                            class="badge">${OnDispose}</span></option>
                                    <option value="0" <#if disposeState==0>selected</#if> >未处理 <span
                                            class="badge">${UnDispose}</span></option>
                                </select>
                            </div>
                        </div>

                        <div class="table-scrollable">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>标题</th>
                                    <th>举报类型</th>
                                    <th>举报备注</th>
                                    <th>举报时间</th>
                                    <th>举报人id</th>
                                    <th>内容与图片</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getId()}">
                                    <td>${p.getArticle().getTitle()}</td>
                                    <td>${p.getType()}</td>
                                    <td>${p.getInfo()}</td>
                                    <td>${p.getTime()}</td>
                                    <td>${p.getUid()}</td>
                                    <td>
                                        <#if p.getArticle().getTypeId()==2>
                                        <a class="btn btn-info btn-xs" onclick="showContent('${p.getArticle().getArticleId()}')"><i class="fa fa-pencil"></i>
                                            内容</a>
                                        </#if>
                                        <#if p.getArticle().getTypeId()==1>
                                            <a class="btn btn-info btn-xs" onclick="showContent('${p.getArticle().getArticleId()}')"><i class="fa fa-pencil"></i>
                                                内容</a>
                                            <a class="btn btn-info btn-xs" onclick="showImgs('${p.getArticle().getArticleId()}',${p.getArticle().getTypeId()})"><i class="fa fa-picture-o"></i>
                                                封面图</a>
                                        </#if>

                                        <#if p.getTypeId()==3>
                                            <a class="btn btn-info btn-xs" onclick="showVedio('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-caret-square-o-right"></i>
                                                视频</a>
                                        </#if>

                                    </td>
                                    <div id="content${p.getArticle().getArticleId()}" style="display: none">
                                        ${p.getArticle().getContent()}
                                    </div>
                                    <div id="video${p.getArticleId()}" style="display: none">
                                        <div style="display: flex;height:726px;align-items:center;justify-content: center">
                                            <div style="">
                                                <video  style="max-height: 700px" src="${p.getContent()}" controls="controls" >

                                                </video>
                                            </div>

                                        </div>
                                    </div>
                                    <td>
                                        <#if disposeState == 0>
                                            <a class="btn btn-danger btn-xs" onclick="reportDispose('${p.getArticle().getArticleId()}',${p.getDisposeState()},0,${p.getId()})"><i class="btn-label glyphicon glyphicon-ok"></i>
                                                不管</a>
                                            <a class="btn btn-danger btn-xs" onclick="reportDispose('${p.getArticle().getArticleId()}',${p.getDisposeState()},1,${p.getId()})"><i class="fa fa-times"></i>
                                                撤销</a>
                                        </#if>
                                        <#if disposeState == 1>
                                            <a class="btn btn-info btn-xs"><i class="btn-label glyphicon glyphicon-ok"></i>
                                                已处理</a>
                                        </#if>

                                    </td>

                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>


                        <div class="margin-top-30 text-align-right">
                            <div class="next">
                                <ul class="pagination">
                                    <li><a href="${url}?page=1&size=${size}&disposeState=${disposeState}">首页</a></li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage-1}&size=${size}&disposeState=${disposeState}">上一页</a></li>

                                            </#if>

                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li><a href="${url}?page=${index}&size=${size}&disposeState=${disposeState}">${index}</a></li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage+1}&size=${size}&disposeState=${disposeState}">下一页</a></li>
                                                </#if>
                                    <li>
                                        <a href="${url}?page=${pageContent.getTotalPages()}&disposeState=${disposeState}">尾页</a>
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

    //处理切换
    $("#selectType").change(function(){
        var disposeState = $(this).val()
        location = "/oa/article/reportlist?disposeState="+disposeState
    });

    //举报处理
    function reportDispose(articleId,disposeState,dispose,id){
        $.post(
            "/oa/article/reportdispose",
                {
                    articleId:articleId,
                    disposeState:disposeState,
                    dispose:dispose,
                    id:id
                },
                function (data){
                    if (data.code == 0) {
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = "/oa/article/reportlist?disposeState="+disposeState
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    function showContent(id) {
        //页面层
        var content = $("#content"+id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['1280px', '768px'], //宽高
            content: content
        });
    }

    //相册层
    function showImgs(id,type) {
        // console.log(id)
        $.getJSON('/layer/article?id='+id+'&type='+type, function(json){
            layer.photos({
                photos: json
                ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }

    function showVedio(id) {
        //页面层
        var video = $("#video"+id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['1280px', '768px'], //宽高
            content: video
        });
    }

</script>
</body>
</html>