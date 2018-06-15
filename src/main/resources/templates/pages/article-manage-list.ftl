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
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <div class="checkbox" id="type">
                                        <span>类型：</span>
                                        <label>
                                            <input type="checkbox" class="colored-blue" value="1"
                                                   <#if type==1>checked</#if>>
                                            <span class="text">图文</span>
                                        </label>
                                        <label id="abc">
                                            <input type="checkbox" class="colored-blue" value="2"
                                                   <#if type==2>checked</#if>>
                                            <span class="text">多图</span>
                                        </label>
                                        <label id="abc">
                                            <input type="checkbox" class="colored-blue" value="3"
                                                   <#if type==3>checked</#if>>
                                            <span class="text">视频</span>
                                        </label>
                                        <label>
                                            <input type="checkbox" value="0" class="colored-danger"
                                                   <#if type==0>checked</#if>>
                                            <span class="text">全部</span>
                                        </label>

                                    </div>
                                </div>
                            </div>
                            <#if manageId == 1>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div class="checkbox" id="special">
                                            <span>板块：</span>
                                            <label>
                                                <input type="checkbox" class="colored-blue" value="2"
                                                   <#if position==2>checked</#if>>
                                                <span class="text">侧边栏</span>
                                            </label>
                                            <label id="abc">
                                                <input type="checkbox" class="colored-blue" value="1"
                                                   <#if position==1>checked</#if>>
                                                <span class="text">轮播图</span>
                                            </label>
                                            <label>
                                                <input type="checkbox" value="0" class="colored-danger"
                                                   <#if position==0>checked</#if>>
                                                <span class="text">全部</span>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="selectType">
                                        <option value="1" <#if manageId==1>selected</#if> >已安排 <span
                                                class="badge">${onNum}</span></option>
                                        <option value="0" <#if manageId==0>selected</#if> >未安排 <span
                                                class="badge">${passNum}</span></option>
                                    </select>
                                </div>
                            </div>
                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="classifySelectType">
                                        <option value="0" <#if classifyId == 0>selected</#if>>全部</option>
                                    <#list classify as c>
                                        <option value="${c.getId()}" <#if c.getId() ==classifyId >selected</#if>>
                                            ${c.getClassify()}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>标题</th>
                                        <th>发布时间</th>
                                        <th>审核通过时间</th>
                                        <th>关键字</th>
                                        <th>类型</th>
                                        <th>分类</th>
                                        <th>审核状态</th>
                                        <th>内容与图片</th>
                                        <th>管理</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getArticleId()}">
                                    <td>${p.getTitle()}</td>
                                    <td>${p.getTime()}</td>
                                    <td>${p.getAuditTime()}</td>
                                    <td><#list p.getKeywords() as key>【${key}】</#list></td>
                                    <div id="content${p.getArticleId()}" style="display: none">
                                        ${p.getContent()}
                                    </div>
                                    <div id="video${p.getArticleId()}" style="display: none">
                                        <div style="display: flex;height:726px;align-items:center;justify-content: center">
                                            <div style="">
                                                <video preload="none" style="max-height: 700px" src="${p.getContent()}"
                                                       controls="controls">

                                                </video>
                                            </div>

                                        </div>
                                    </div>
                                    <td>${p.getType()}</td>
                                    <td>${p.getClassify()}</td>
                                    <td>
                                        <#if p.getState()==1>
                                            已通过
                                        </#if>
                                    </td>
                                    <td>

                                        <#if p.getTypeId()==1>
                                            <a class="btn btn-info btn-xs" onclick="showContent('${p.getArticleId()}')"><i
                                                    class="fa fa-pencil"></i>
                                                内容</a>
                                            <a class="btn btn-info btn-xs"
                                               onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i
                                                    class="fa fa-picture-o"></i>
                                                封面图</a>
                                        </#if>
                                        <#if p.getTypeId()==2>
                                            <a class="btn btn-info btn-xs"
                                               onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i
                                                    class="fa fa-pencil"></i>
                                                内容</a>
                                        </#if>
                                        <#if p.getTypeId()==3>
                                            <a class="btn btn-info btn-xs"
                                               onclick="showVedio('${p.getArticleId()}',${p.getTypeId()})"><i
                                                    class="fa fa-caret-square-o-right"></i>
                                                视频</a>
                                        <a class="btn btn-info btn-xs" onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-picture-o"></i>
                                            封面图</a>
                                        </#if>
                                    </td>

                                    <td>
                                        <#if p.getManageId()==0>
                                            <a class="btn btn-success btn-xs"
                                               onclick="slideArticle('${p.getArticleId()}')"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i>
                                                上轮播</a>

                                                <a class="btn btn-success btn-xs"
                                                   onclick="recommentArticle('${p.getArticleId()}')"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i>
                                                    上侧边栏</a>

                                        </#if>
                                        <#if p.getManageId()==1>
                                            <#if p.getSlideState()==1>
                                            <a class="btn btn-success btn-xs">
                                                轮播图</a>
                                            </#if>
                                            <#if p.getRecommendState()==1>
                                                <a class="btn btn-success btn-xs">
                                                    侧边栏</a>
                                            </#if>
                                        <a class="btn btn-danger btn-xs"
                                           onclick="deleteManage('${p.getArticleId()}')"><i class="fa fa-times"></i>
                                            撤销</a>
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
                                        <li>
                                            <a href="${url}?page=1&size=${size}&type=${type}&manageId=${manageId}&classifyId=${classifyId}&position=${position}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}&type=${type}&manageId=${manageId}&classifyId=${classifyId}&position=${position}">上一页</a>
                                                </li>

                                            </#if>

                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li>
                                                            <a href="${url}?page=${index}&size=${size}&type=${type}&manageId=${manageId}&classifyId=${classifyId}&position=${position}">${index}</a>
                                                        </li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}&type=${type}&manageId=${manageId}&classifyId=${classifyId}&position=${position}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&type=${type}&manageId=${manageId}&classifyId=${classifyId}&position=${position}">尾页</a>
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
    var STATE = 1
    var NO = 0

    function deleteManage(articleId) {
        var resultType = $("#type input[type=checkbox]:checked").val()
        $.post(
                "/oa/article/managedelete",
                {
                    articleId: articleId,
                    manageId:100
                },
                function (data) {
                    if (data.code == 0) {
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = url+search
                            // location = "/oa/article/managelist?type=" + resultType + "&manageId=" + STATE
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    //安排与未安排切换
    $("#selectType").change(function () {
        var manageId = $(this).val()
        var resultType = $("#type input[type=checkbox]:checked").val()
        var classifyId = $("#classifySelectType").val();
        var position = 0
        location = "/oa/article/managelist?type=" + resultType + "&manageId=" + manageId+"&classifyId="+classifyId+"&position="+position
    });

    //分类切换
    $("#classifySelectType").change(function(){
        var manageId = $("#selectType").val()
        var resultType = $("#type input[type=checkbox]:checked").val()
        var classifyId = $("#classifySelectType").val();
        var position = $("#special input[type=checkbox]:checked").val()
        if(position==null){
            position = ${position}
        }
        location = "/oa/article/managelist?type="+resultType+"&manageId=" + manageId+"&classifyId="+classifyId+"&position="+position
    });

    //模块管理（上轮播图）
    function slideArticle(articleId) {
        var resultType = $("#type input[type=checkbox]:checked").val()
        $.post(
                "/oa/article/slide",
                {
                    articleId: articleId
                },
                function (data) {
                    if (data.code == 0) {
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = url+search
                            // location = "/oa/article/managelist?type=" + resultType + "&maganeId=" + NO
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    //模块管理（上侧边栏）
    function recommentArticle(articleId) {
        var resultType = $("#type input[type=checkbox]:checked").val()
        $.post(
                "/oa/article/recomment",
                {
                    articleId: articleId
                },
                function (data) {
                    if (data.code == 0) {
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = url+search
                            // location = "/oa/article/managelist?type=" + resultType + "&maganeId=" + NO
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    //类型切换
    $("#type input[type=checkbox]").click(function () {

        var classifyId = $("#classifySelectType").val();
        var manageId = $("#selectType option:selected").val()
        var resultType = $(this).val()
        var position = $("#special input[type=checkbox]:checked").val()
        if(position==null){
            position = ${position}
        }
        location = "/oa/article/managelist.html?state=" + STATE + "&type=" + resultType + "&manageId=" + manageId+
                "&classifyId="+classifyId+"&position="+position
    })

    //板块切换
    $("#special input[type=checkbox]").click(function () {

        var classifyId = $("#classifySelectType").val();
        var manageId = $("#selectType option:selected").val()
        var resultType = $("#type input[type=checkbox]:checked").val()
        var position = $(this).val()
        location = "/oa/article/managelist.html?state=" + STATE + "&type=" + resultType + "&manageId=" + manageId+
                "&classifyId="+classifyId+"&position="+position
    })


    function showContent(id) {
        //页面层
        var content = $("#content" + id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['1280px', '768px'], //宽高
            content: content
        });
    }

    //相册层
    function showImgs(id, type) {
        // console.log(id)
        $.getJSON('/layer/article?id=' + id + '&type=' + type, function (json) {
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }

    function showVedio(id) {
        //页面层
        var video = $("#video" + id).html();
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