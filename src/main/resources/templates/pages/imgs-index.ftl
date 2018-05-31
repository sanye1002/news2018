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
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">多图发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="col-sm-12">
                                    <div class="form-title">基本内容</div>
                                    <div class="col-sm-6">

                                        <div class="form-group">
                                            <label>标题</label>
                                            <span class="input-icon icon-right">
                                                    <input id="article_title" type="text" class="form-control">
                                                    <i class="fa fa-user"></i>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <label>关键字</label>
                                            <div>
                                                <input id="key-words" type="text" value="" data-role="tagsinput"
                                                       placeholder="关键字" style="display: none;">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="anchorUser">文章类型</label>
                                            <span class="input-icon icon-right">
                                                <select id="e1" style="width:100%;">
                                                    <#--<#list classify as c>-->
                                                        <#--<option value="AL">${c.getClassify()}</option>-->
                                                    <#--</#list>-->
                                                </select>
                                                <i class="glyphicon glyphicon-fire"></i>
                                                </span>
                                        </div>
                                        <div class="form-group">
                                            <label for="anchorUser">类目</label>
                                            <span class="input-icon icon-right">
                                                <select id="e1" style="width:100%;">
                                                    <option value="AL">图文</option>
                                                    <option value="WA">多图</option>
                                                    <option value="WV">视频</option>
                                                </select>
                                                <i class="glyphicon glyphicon-fire"></i>
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
                                                多图片选择
                                            </button>
                                            <button type="button" class="layui-btn" id="image-upload">
                                                多图片上传
                                            </button>
                                            <blockquote class="layui-elem-quote layui-quote-nm"
                                                        style="margin-top: 10px;">
                                                预览图：
                                                <div class="layui-upload-list" id="imgShow"></div>
                                            </blockquote>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-title">图片对应的描述</div>
                                        <div id="content-box">

                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div style="float:right;margin-top:10px;">
                                        <button id="save" class="btn btn-success">保存</button>
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
    $(function () {
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
                , url: '/upload/img/article'
                , auto: false
                , multiple: true
                , acceptMime: 'image'
                , bindAction: '#image-upload'
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $('#imgShow').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                        $('#content-box').append('<span class="input-icon icon-right">\n' +
                                '                                <textarea rows="2" class="form-control"\n' +
                                '                        id="back-content"\n' +
                                '                        placeholder="请输入你的内容"></textarea>\n' +
                                '                                </span>')
                    });
                }
                , done: function (res) {
                    //上传成功
                    if (res.code == 0) {
                        article.smallImg = res.data.src;
                        return layer.msg(res.message);
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
        layui.use('layedit', function () {
            layedit = layui.layedit;
            layedit.set({
                uploadImage: {
                    url: '/upload/img/article' //接口url
                    , type: 'post' //默认post
                }
            });
            index = layedit.build('demo'); //建立编辑器
        });
        $("#save").click(function () {
            layer.msg("123")
            console.log($("#key-words").val())
        })
    })
</script>
</body>
</html>