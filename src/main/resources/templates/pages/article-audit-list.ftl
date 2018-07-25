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
                                    <div class="checkbox" id="state">
                                        <span>结果：</span>
                                        <label>
                                            <input type="checkbox" class="colored-blue" value="0"
                                                   <#if state==0>checked</#if>>
                                            <span class="text">未通过</span>
                                        </label>
                                        <label>
                                            <input type="checkbox" class="colored-blue" value="1"
                                                   <#if state==1>checked</#if>>
                                            <span class="text">通过</span>
                                        </label>
                                        <label>
                                            <input type="checkbox" value="2" class="colored-danger"
                                                   <#if state==2>checked</#if>>
                                            <span class="text">待审核</span>
                                        </label>
                                    </div>
                                </div>
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
                                        <option value="1" <#if state==1>selected</#if> >已通过 <span
                                                class="badge">${pass}</span></option>
                                        <option value="0" <#if state==0>selected</#if> >未通过 <span
                                                class="badge">${noPass}</span></option>
                                        <option value="2" <#if state==2>selected</#if> >待审核 <span
                                                class="badge">${noAudit}</span></option>
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
                                        <th>用户</th>
                                        <th>是否原创</th>
                                        <th>标题</th>
                                        <th>发布时间</th>
                                        <#if state!=2>
                                            <th>审核时间</th>
                                        </#if>
                                        <th>关键字</th>
                                        <th>类型</th>
                                        <th>分类</th>
                                        <th>内容与图片</th>
                                        <th>审核</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getArticleId()}">
                                    <td>${p.getUser().getNikeName()}</td>
                                    <td>
                                        <#if p.getOriginal()==0>非原创</#if>
                                        <#if p.getOriginal()==1>原创</#if>
                                    </td>
                                    <td>${p.getTitle()}</td>
                                    <td>${p.getTime()}</td>
                                    <#if state!=2>
                                        <td>
                                            ${p.getAuditTime()!}
                                        </td>
                                    </#if>
                                    <td><#list p.getKeywords() as key>【${key}】</#list></td>
                                    
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
                                        <a class="btn btn-info btn-xs"
                                           onclick="showImgs('${p.getArticleId()}',${p.getTypeId()})"><i
                                                class="fa fa-picture-o"></i>
                                            封面图</a>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if p.getState()==2>
                                            <a class="btn btn-success btn-xs"
                                               onclick="changeAudit('${p.getArticleId()}',1,${p.getTypeId()})"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i>
                                                通过</a>
                                            <a class="btn btn-danger btn-xs"
                                               onclick="changeAudit('${p.getArticleId()}',0,${p.getTypeId()})"><i
                                                    class="fa fa-times"></i>
                                                未通过</a>
                                        </#if>
                                        <#if p.getState()==1>
                                            <a class="btn btn-success btn-xs"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i>
                                                已通过</a>
                                        </#if>
                                        <#if p.getState()==0>
                                            <a class="btn btn-danger btn-xs"><i class="fa fa-times"></i>
                                                未通过</a>
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
                                            <a href="${url}?page=1&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">上一页</a>
                                                </li>

                                            </#if>

                                               <#if pageContent.getTotalPages() lte 5>
                                                   <#list 1..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="${url}?page=${index}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif currentPage lte 3>
                                                   <#list 1..5 as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="${url}?page=${index}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt 3 && currentPage lte pageContent.getTotalPages()-2>
                                                   <#list currentPage-2..currentPage+2 as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li><a href="${url}?page=${index}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt pageContent.getTotalPages()-2>
                                                   <#list pageContent.getTotalPages()-4..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li><a href="${url}?page=${index}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               </#if>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}&state=${state}&type=${type}&classifyId=${classifyId}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&state=${state}&type=${type}&classifyId=${classifyId}">尾页</a>
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


    //select切换页面展示
    var ALL = 0
    $("#selectType").change(function () {
        var resultType = $("#type input[type=checkbox]:checked").val()
        var state = $("#selectType").val()
        var classifyId = $("#classifySelectType").val();
        location = "/oa/article/auditlist?type=" + resultType + "&state=" + state + "&classifyId=" + classifyId
    });

    //分类切换
    $("#classifySelectType").change(function () {
        var resultType = $("#type input[type=checkbox]:checked").val()
        var state = $("#selectType").val()
        var classifyId = $("#classifySelectType").val();
        location = "/oa/article/auditlist?type=" + resultType + "&state=" + state + "&classifyId=" + classifyId
    });


    /*//审核提交
    function changeAudit1(articleId,state){
        var resultType = $("#type input[type=checkbox]:checked").val()
        $.post(
            "/oa/article/audit",
                {
                    articleId:articleId,
                    state:state
                },
                function (data){
                    if (data.code == 0) {
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        layer.msg(data.message);
                        setTimeout(function () {
                            // location = "/oa/article/auditlist?type="+resultType
                            location = url+search
                        }, 100)
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }*/

    //类型和审核状态选择
    $("input[type=checkbox]").click(function () {
        var classifyId = $("#classifySelectType").val();
        var resultStatus = null;
        var resultType = null;
        var result = $(this).parent().parent().attr('id');
        if (result != "") {
            if (result == "state") {
                resultStatus = $(this).val()
                resultType = $("#type input[type=checkbox]:checked").val()
            } else {
                resultType = $(this).val()
                resultStatus = $("#state input[type=checkbox]:checked").val()
            }
        }
        location = "/oa/article/auditlist.html?state=" + resultStatus + "&type=" + resultType + "&classifyId=" + classifyId

    })

    function showContent(id) {
        //页面层
        $.post(
                "/oa/article/content",
                {
                    articleId: id
                },
                function (data) {
                    if (data.code == 0) {
                        layer.open({
                            type: 1,
                            skin: 'layui-layer-lan', //加上边框
                            area: ['800px', '600px'], //宽高
                            content: data.data.content
                        });
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    function showVedio(id) {
        //页面层
        var video = $("#video" + id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['800px', '600px'], //宽高
            content: video
        });
    }

    function showImgs(id, type) {
        console.log(id)
        $.getJSON('/layer/article?id=' + id + '&type=' + type, function (json) {
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }

    function changeAudit(articleId, state,type) {
        var resultType = $("#type input[type=checkbox]:checked").val()
        if (state == 1) {
            layer.prompt({title: '请输入积分(整数)，并确认', formType: 0}, function (text) {
                if (parseInt(text) == text) {
                    $.post(
                            "/oa/article/audit",
                            {
                                articleId: articleId,
                                state: state,
                                integral: text,
                                type:type

                            },
                            function (data) {
                                if (data.code == 0) {
                                    layer.msg(data.message);
                                    setTimeout(function () {
                                        // location = "/oa/article/auditlist?type="+resultType
                                        var url = window.location.pathname;
                                        var search = window.location.search;
                                        location = url + search
                                    }, 100)
                                }
                                if (data.code > 0) {
                                    layer.msg(data.message);
                                }
                            }
                    )
                } else {
                    layer.msg("请输入整数")
                }


            });
        } else {
            $.post(
                    "/oa/article/audit",
                    {
                        articleId: articleId,
                        state: state,
                        integral: 0,
                        type:type

                    },
                    function (data) {
                        if (data.code == 0) {
                            layer.msg(data.message);
                            setTimeout(function () {
                                var url = window.location.pathname;
                                var search = window.location.search;
                                location = url + search
                            }, 100)
                        }
                        if (data.code > 0) {
                            layer.msg(data.message);
                        }
                    }
            )
        }


    }
</script>
</body>
</html>