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
            <#--添加-->
                <div class="row add_area_row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form id="addform" class="form-horizontal form-bordered" role="form">
                                        <div class="form-group">
                                            <label for="getName"
                                                   class="col-sm-2 control-label no-padding-right">分类名称</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="getName" value="${classify.getClassify()!}"
                                                       class="form-control" id="getName">
                                            </div>
                                        </div>

                                        <div class="form-group ">
                                            <div class="text-right" style="margin-right:16px;">
                                                <a class="btn btn-success" id="permissionSubmit"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 提交</a>
                                                <a class="btn btn-warning" id="permission_list"><i
                                                        class="btn-label glyphicon glyphicon-th-list"></i>列表</a>
                                            </div>
                                        </div>
                                    </form>
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
<script type="text/javascript">
    $("#permission_list").click(function () {
        location = "/oa/classify/alllist.html"
    })

    $(function () {
        $("#permissionSubmit").click(function () {
            var id = "${classify.getId()!}";
            var name = $("#getName").val();
            $.post(
                    "/oa/classify/save",
                    {
                        id: id,
                        classify: name
                    },
                    function (res) {
                        if (res.code != 0) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                        if (res.code == 0) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            setTimeout(function () {
                                location = "/oa/classify/index.html";
                            }, 1000);
                        }
                    }
            )
        })
    })
</script>
</body>
</html>