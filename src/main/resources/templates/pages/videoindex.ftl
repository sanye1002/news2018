<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">

<link href="/layui/css/layui.css" rel="stylesheet"/>
<style>
    .layui-upload-img {
        width: 200px;
        height: 100%;
        margin: 0 10px 10px 0;
    }
</style>
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
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">视频发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="col-sm-12">
                                    <div class="form-title">基本内容</div>
                                    <div class="col-sm-6">

                                        <div class="form-group">
                                            <label>标题</label>
                                            <span class="input-icon icon-right">
                                                    <input id="article_title" type="text" value="${article.getTitle()!}" class="form-control">
                                                    <i class="fa fa-user"></i>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <label>关键字</label>
                                            <div>
                                                <input id="key-words" type="text" value="${article.getKeywords()!}" data-role="tagsinput"
                                                       placeholder="关键字" style="display: none;">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="anchorUser">文章类型</label>
                                            <span class="input-icon icon-right">
                                                <select id="e1" style="width:100%;">
                                                    <#list classify as c>
                                                        <option value=${c.getId()} <#if article.getClassifyId()! == c.getId()>selected</#if>>
                                                            ${c.getClassify()}
                                                        </option>
                                                    </#list>
                                                </select>
                                                <i class="glyphicon glyphicon-fire"></i>
                                                </span>
                                        </div>
                                        <div class="form-group">
                                            <label for="anchorUser">类目</label>
                                            <span class="input-icon icon-right">
                                                <select id="e" style="width:100%;">
                                                    <option value="3">视频</option>
                                                </select>

                                                </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 WA">
                                    <div class="form-title">封面图片</div>
                                    <div class="col-sm-12">
                                        <div class="form-title">封面图片<span>大小：360*265</span></div>
                                        <div class="layui-upload">
                                            <input id="imgId" value="" style="display: none">
                                            <button type="button" class="layui-btn" id="image-select">
                                                视频选择
                                            </button>
                                            <button type="button" class="layui-btn" id="image-upload">
                                                视频上传
                                            </button>
                                            <blockquote class="layui-elem-quote layui-quote-nm"
                                                        style="margin-top: 10px;">
                                                预览图：
                                                <div class="layui-upload-list" id="imgShow">
                                                    <#if article.getContent()! !="">
                                                        <video src="${article.getContent()}" controls="controls" class="layui-upload-img"></video>
                                                    </#if>
                                                    <#--<video src="/read/mp4/1527582639993/1527599861315.MP4" controls="controls"></video>-->
                                                </div>
                                            </blockquote>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 2">
                                        <div class="form-title">视频对应的描述</div>
                                        <div id="content-box">
                                            <span class="input-icon icon-right">
                                                <textarea rows="2" class="form-control videoDes"id="back-content"placeholder="请输入你的内容">${article.getDes()!}</textarea>
                                            </span>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-sm-12">
                                    <div style="float:right;margin-top:10px;">
                                        <button id="save" class="btn btn-warning">上传</button>
                                    </div>
                                    <div style="float:right;margin-top:10px;margin-right: 10px">

                                            <button id="saveDraft" class="btn btn-success">保存草稿</button>


                                    </div>
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
<script src="/layui/layui.js" charset="utf-8"></script>
<script>

    var DEFAULT = 3;
    var article = {
        title: "",
        type: DEFAULT,
        content: "${article.getContent()!}",
        keywords: "",
        classify: 1,
        article: "${article.getArticleId()!}",
        smallImg: " "
    }

    var index;
    var layedit;


    $(function () {



        $("#e").select2()
        //分类选择
        $("#e1").change(function () {
            article.classify = $(this).find("option:checked").val();
        });


        $("#e1").select2()
        $("input[type=radio]").click(function () {
            article.type = this.value;
        })
        layui.use('upload', function () {
            var $ = layui.jquery
                    , upload = layui.upload;

            //普通图片上传
            var uploadInst = upload.render({
                elem: '#image-select'
                , url: '/oa/upload/mp4/${user.getUserId()}'
                , auto: false
                , multiple: true
                , size: 5120
                , accept:"video"
                , acceptMime: 'video'
                , bindAction: '#image-upload'
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    console.log(obj)
                    obj.preview(function (index, file, result) {
                        console.log(result)
                    });
                }
                , done: function (res) {
                    //上传成功
                    if (res.code == 0) {
                        $('#imgShow').append('<video src="' + res.data.videoPath + '"  controls="controls" class="layui-upload-img">')
                        article.content = res.data.videoPath;
                        return layer.msg( res.data.message);
                    }

                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg(res.message);
                    }
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    var demoText = $('#demoText');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });
        });
        $("#save").click(function () {
            article.keyWords = $("#key-words").val();
            article.title = $("#article_title").val();
            var videoDes = $(".videoDes").val();
            if (article.title == "") {
                layer.msg("文章标题为填写...")
            } else {
                if (article.keyWords == "") {
                    layer.msg("请填写关键字...")
                } else {
                    if (article.smallImg == "") {
                        layer.msg("请上传封面图...")
                    } else {
                        if (article.content == "") {
                            layer.msg("请上传视频...")
                        } else {
                            //保存
                            $("#save").attr("disabled", true)
                            $.post(
                                    "/oa/article/save",
                                    {
                                        typeId: article.type,
                                        title: article.title,
                                        content: article.content,
                                        keywords: article.keyWords,
                                        classifyId: article.classify,
                                        draft: 1,
                                        articleId:article.article,
                                        des:videoDes,
                                        imgUrl:article.smallImg
                                    },
                                    function (data) {

                                        if (data.code == 0) {
                                            layer.msg(data.message);
                                            setTimeout(function () {
                                                location = "/oa/article/video/index"
                                            }, 2000)
                                        }
                                        if (data.code > 0) {
                                            $("#save").removeAttr("disabled")
                                            layer.msg(data.message);
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        })

        $("#saveDraft").click(function () {

            article.keyWords = $("#key-words").val();
            article.title = $("#article_title").val();
            var videoDes = $(".videoDes").val();

            //保存
            $("#saveDraft").attr("disabled", true);
            $.post(
                    "/oa/article/draft/save",
                    {
                        typeId: article.type,
                        title: article.title,
                        content: article.content,
                        keywords: article.keyWords,
                        classifyId: article.classify,
                        draft: 1,
                        articleId:article.article,
                        des:videoDes
                    },
                    function (data) {

                        if (data.code == 0) {
                            layer.msg(data.message);
                            setTimeout(function () {
                                location = "/oa/article/video/index"
                            }, 2000)
                        }
                        if (data.code > 0) {
                            $("#saveDraft").removeAttr("disabled")
                            layer.msg(data.message);
                        }
                    }
            )

        })
    })
</script>
</body>
</html>