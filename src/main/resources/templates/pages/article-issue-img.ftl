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
                                    <div class="form-title">基本内容</div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>标题</label>
                                            <span class="input-icon icon-right">
                                                    <input value="${article.getTitle()!}" id="article_title" type="text"
                                                           class="form-control">
                                                    <i class="fa fa-user"></i>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <label>关键字</label>
                                            <div>
                                                <input id="key-words" type="text" value="${article.getKeywords()!}"
                                                       data-role="tagsinput"
                                                       placeholder="关键字" style="display: none;">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-lg-4 col-sm-4 col-xs-4" style="padding: 0">
                                                <div class="checkbox">
                                                    <label style="padding: 0">
                                                        <input name="original" type="checkbox" <#if article.getOriginal()! == 1>checked</#if> >
                                                        <span class="text">原创</span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="anchorUser">文章类型</label>
                                            <span class="input-icon icon-right">
                                                <select id="e1" style="width:100%;">
                                                    <option value="0" <#if article.getClassifyId()! == 0>selected</#if>>请选择</option>
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
                                                <select id="e2" style="width:100%;">
                                                    <option value=2 >多图</option>
                                                </select>
                                                <i class="glyphicon glyphicon-fire"></i>
                                            </span>
                                        </div>

                                    </div>

                                </div>
                                <div class="col-sm-12">
                                    <div class="form-title">图片</div>
                                    <div class="col-sm-12">
                                        <div class="form-title">图片<span>大小：676*501</span></div>
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
                                                    <#list article.getImgList()! as img >
                                                        <img src="${img}" class="layui-upload-img">
                                                    </#list>
                                                </div>
                                            </blockquote>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-title">图片对应的描述</div>
                                        <div id="content-box">
                                                <#list article.getImgList()! as desc>
                                                    <span class="input-icon icon-right">
                                                                <textarea rows="2" class="form-control des"
                                                                          id="back-content"
                                                                          placeholder="请输入你的内容">${article.getManyImgDesc()[desc_index]!}</textarea>
                                                               </span>
                                                </#list>
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

    // cleanRedisCacheNews();
    $(function () {
        var DEFAULT = 1;
        var article = {
            title: "",
            smallImg: "${article.getImgUrl()!}",
            type: "",
            content: "",
            keywords: "",
            classify: "",
            article: "${articleId!}",
            original:0
        }

        var index;
        var layedit;




        //分类选择
        $("#e1").change(function () {
            article.classify = $(this).find("option:checked").val();
        });


        $("#e1").select2()
        $("#e2").select2()

        layui.use('layedit', function () {
            layedit = layui.layedit;
            layedit.set({
                uploadImage: {
                    url: '/oa/upload/img/article' //接口url
                    , type: 'post' //默认post
                }
            });
            index = layedit.build('demo'); //建立编辑器
        });

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
                        $('#content-box').append('<span class="input-icon icon-right">\n' +
                                '                                <textarea rows="2" class="form-control des"\n' +
                                '                        id="back-content"\n' +
                                '                        placeholder="请输入你的内容"></textarea>\n' +
                                '                                </span>')
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
                        if (article.smallImg == "") {
                            article.smallImg = res.data.src;

                        } else {
                            article.smallImg = article.smallImg + "," + res.data.src;
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
        });
        // $("#save").click(function () {
        //     layer.msg("123")
        //     console.log($("#key-words").val())
        // })
        $("#save").click(function () {
            var desc = "";
            var des = $(".des")
            var num = des.length
            if ($('input[name="original"]:checked').length > 0){
                article.original = 1
            }


            if (num != 0) {
                for (var i = 0; i < num; i++) {
                    if (desc == "") {
                        desc = des.eq(i).val()
                    } else {
                        desc = desc + "," + des.eq(i).val()
                    }
                }
            }

            article.content = desc;
            article.keyWords = $("#key-words").val();
            article.title = $("#article_title").val();
            article.classify = $("#e1").val();
            article.type = $("#e2").val();

            console.log(article.smallImg)
            if (article.title.length < 20) {
                layer.msg("文章标题必须大于20个字...")
            } else {
                if (article.keyWords == "") {
                    layer.msg("请填写关键字...")
                }if (article.classify == 0){
                    layer.msg("请选择分类...")
                } else {
                    if (article.smallImg == "") {
                        layer.msg("请上传图片...")
                    } else {
                        if (article.content == "") {
                            layer.msg("请编写文章内容...")
                        } else {
                            //保存
                            $("#save").attr("disabled", true)
                            $.post(
                                    "/oa/article/save",
                                    {
                                        typeId: article.type,
                                        imgUrl: article.smallImg,
                                        title: article.title,
                                        content: article.content,
                                        keywords: article.keyWords,
                                        classifyId: article.classify,
                                        draft: ${article.getDraft()!0},
                                        articleId:article.article,
                                        original:article.original,
                                        isOwn:1
                                    },
                                    function (data) {

                                        if (data.code == 0) {
                                            layer.msg(data.message);
                                            setTimeout(function () {
                                                location = "/oa/article/issue/img"
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
            article.classify = $("#e1").val();
            article.type = $("#e2").val();
            var desc = "";
            var des = $(".des")
            var num = des.length
            if ($('input[name="original"]:checked').length > 0){
                article.original = 1
            }
            if (num != 0) {
                for (var i = 0; i < num; i++) {
                    if (desc == "") {
                        desc = des.eq(i).val()
                    } else {
                        desc = desc + "," + des.eq(i).val()
                    }
                }
            }

                article.content = desc

            article.keyWords = $("#key-words").val();
            article.title = $("#article_title").val();

            //保存草稿
            $("#saveDraft").attr("disabled", true);
            $.post(
                    "/oa/article/draft/save",
                    {
                        typeId: article.type,
                        imgUrl: article.smallImg,
                        title: article.title,
                        content: article.content,
                        keywords: article.keyWords,
                        classifyId: article.classify,
                        draft: ${article.getDraft()!1},
                        articleId:article.article,
                        original:article.original
                    },
                    function (data) {

                        if (data.code == 0) {
                            layer.msg(data.message);
                            setTimeout(function () {
                                location = "/oa/article/issue/img"
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