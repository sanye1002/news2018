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
                                <span class="widget-caption">文章发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="col-sm-12">
                                    <div class="form-title">封面图片</div>
                                    <div class="col-sm-12">
                                        <div class="form-title">封面图片<span>大小：960*497</span></div>
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
                                                <div class="layui-upload-list" id="imgShow">

                                                </div>
                                            </blockquote>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div style="float:right;margin-top:10px;">
                                        <button id="save" class="btn btn-warning">上传</button>
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

        var logoUrl = "";

        layui.use('upload', function () {
            var $ = layui.jquery, upload = layui.upload;

            //普通图片上传
            var uploadInst = upload.render({
                elem: '#image-select'
                , url: '/oa/upload/img/article'
                , auto: false
                , multiple: true
                , acceptMime: 'image'
                , bindAction: '#image-upload'
                , choose: function (obj) {
                    //将每次选择的文件追加到文件队列
                    var files = obj.pushFile();
                    obj.preview(function (index, file, result) {
                        // console.log(index); //得到文件索引
                        // console.log(file); //得到文件对象
                        // console.log(result); //得到文件base64编码，比如图片

                        //这里还可以做一些 append 文件列表 DOM 的操作
                        $('#imgShow').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                        /*$('#content-box').append('<span class="input-icon icon-right">\n' +
                                '                                <textarea rows="2" class="form-control des"\n' +
                                '                        id="back-content"\n' +
                                '                        placeholder="请输入你的内容"></textarea>\n' +
                                '                                </span>')*/
                        //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                        //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
                    });
                }
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    /* $('#imgShow').html("");*/
                    obj.preview(function (index, file, result) {
                        //这里还可以做一些 append 文件列表 DOM 的操作
                        // $('#imgShow').html("")
                    });
                }
                , done: function (res) {
                    //上传成功

                    if (res.code == 0) {

                        // $('#imgShow').append('<img src="' + res.data.src + '" alt="' + res.data.src + '" class="layui-upload-img">')
                        if (logoUrl == "") {
                            logoUrl = res.data.src;

                        } else {
                            logoUrl = logoUrl + "," + res.data.src;
                        }
                        return layer.msg(res.message);
                    }

                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg(res.message);
                    }
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    var demoText = $('#demoText123');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });
        })
        $("#save").click(function () {

                    if (logoUrl == "") {
                        layer.msg("请上传封面图...")
                    } else {
                            //保存
                            $("#save").attr("disabled", true)
                            $.post(
                                    "/oa/article/save",
                                    {
                                        imgUrl: logoUrl,
                                    },
                                    function (data) {

                                        if (data.code == 0) {
                                            layer.msg(data.message);
                                            setTimeout(function () {
                                                location = "/oa/logo/save"
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





    })


</script>
</body>
</html>