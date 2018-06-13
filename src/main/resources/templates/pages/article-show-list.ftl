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
                        <div style="float:right;margin-right:2px;">

                            <div class="form-group">
                                <select id="selectType">
                                    <option value="1" <#if showState==1>selected</#if> >已展示 <span
                                            class="badge">${showY}</span></option>
                                    <option value="0" <#if showState==0>selected</#if> >未展示 <span
                                            class="badge">${showN}</span></option>
                                </select>
                            </div>
                        </div>
                        <div class="table-scrollable">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>标题</th>
                                    <th>时间</th>
                                    <th>关键字</th>
                                    <th>类型</th>
                                    <th>分类</th>
                                    <th>审核状态</th>
                                    <th>内容与图片</th>
                                    <th>展示</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getArticleId()}">
                                    <td>${p.getTitle()}</td>
                                    <td>${p.getTime()}</td>
                                    <td><#list p.getKeywords() as key>【${key}】</#list></td>
                                    <div id="content${p.getArticleId()}" style="display: none">
                                            ${p.getContent()}
                                    </div>
                                    <div id="video${p.getArticleId()}" style="display: none">
                                        <div style="display: flex;height:726px;align-items:center;justify-content: center">
                                            <div style="">
                                                <video  style="max-height: 700px" src="${p.getContent()}" controls="controls" >

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
                                            <a class="btn btn-info btn-xs" onclick="showContent('${p.getArticleId()}')"><i class="fa fa-pencil"></i>
                                                内容</a>
                                            <a class="btn btn-info btn-xs" onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-picture-o"></i>
                                                封面图</a>
                                        </#if>
                                        <#if p.getTypeId()==2>
                                            <a class="btn btn-info btn-xs" onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-pencil"></i>
                                                内容</a>
                                        </#if>
                                        <#if p.getTypeId()==3>
                                            <a class="btn btn-info btn-xs" onclick="showVedio('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-caret-square-o-right"></i>
                                                视频</a>
                                        <a class="btn btn-info btn-xs" onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i class="fa fa-picture-o"></i>
                                            封面图</a>
                                        </#if>
                                    </td>
                                    <td id="show">
                                        <label>
                                            <input id="${p.getArticleId()}" class="checkbox-slider colored-blue"
                                                   type="checkbox"
                                                   <#if p.getShowState() ==1>checked</#if>>
                                            <span class="text"></span>
                                        </label>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>


                        <div class="margin-top-30 text-align-right">
                            <div class="next">
                                <ul class="pagination">
                                    <li><a href="${url}?page=1&size=${size}&type=${type}&showState=${showState}">首页</a></li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage-1}&size=${size}&type=${type}&showState=${showState}">上一页</a></li>

                                            </#if>

                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li><a href="${url}?page=${index}&size=${size}&type=${type}&showState=${showState}">${index}</a></li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage+1}&size=${size}&type=${type}&showState=${showState}">下一页</a></li>
                                                </#if>
                                    <li>
                                        <a href="${url}?page=${pageContent.getTotalPages()}&type=${type}&showState=${showState}">尾页</a>
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

    var carousel = {
        articleId: "",
        showState: ""
    }

    $("#show input[type=checkbox]").click(function () {
        //判断是否选中
        if ($(this).is(':checked')) {
            carousel.showState = 1
            carousel.articleId = $(this).attr("id")
            update()


        } else {
            carousel.articleId = $(this).attr("id")
            carousel.showState = 0
            update()
        }
    })
    function update() {
        var resultType = $("#type input[type=checkbox]:checked").val()
        var showF = 0
        if(carousel.showState == 0){
            showF = 1
        }else {
            showF = 0
        }
        $.post(
                "/oa/article/show",
                {
                    articleId: carousel.articleId,
                    showState: carousel.showState
                },
                function (data) {
                    if (data.code == 0) {
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            location = url+search
                            // location = "/oa/article/showlist?type=" +resultType+ "&showState=" + showF
                        }, 100)
                    } else {
                        layer.msg(data.message);
                    }

                }
        )
    }




    //展示与未展示切换
    $("#selectType").change(function(){
        var showState = $(this).val()
        var resultType = $("#type input[type=checkbox]:checked").val()
        location = "/oa/article/showlist?type="+resultType+"&showState="+showState
    });


    //类型切换
    $("#type input[type=checkbox]").click(function () {
        var showState = $("#selectType option:selected").val()
        var resultType = $(this).val()
        location = "/oa/article/showlist.html?state=" + STATE+"&type=" + resultType + "&showState=" +showState
    })


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