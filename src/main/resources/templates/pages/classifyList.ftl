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
                <!--管理-->
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                            </div>
                            <div class="form-group">
                                <div class="text-right">
                                    <a href="/oa/classify/index.html" class="btn btn-success"
                                       name="pullModel-add_area_row">添加分类</a>
                                </div>
                            </div>

                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>#ID</th>
                                        <th>分类名称</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list pageContent.getContent() as classify>
                                    <tr id="${classify.getId()}"
                                       <#if classify.getId()%2!=0>class="success"</#if>
                                    >
                                        <td>${classify.getId()}</td>
                                        <td id="name${classify.getId()}">${classify.getClassify()}</td>
                                        <td>
                                            <a href="/oa/classify/index?id=${classify.getId()}"
                                               name="pullModel-edit_area_row"
                                               class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                            <a id="remove-area-1" onclick="deleteDept(${classify.getId()})"
                                               class="btn btn-danger btn-xs edit"><i
                                                    class="glyphicon glyphicon-trash"></i> 删除</a>
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>
                           <#include "../common/page.ftl">
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
    function deleteDept(id) {
        layer.confirm('此操作为不可逆操作，客官已确认？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.closeAll();
            layer.msg('请稍等...', {
                icon: 16
                , shade: 0.01
            });
            //执行POST请求
            $.post(
                    "/oa/classify/delete",
                    {
                        id: id
                    },
                    function (res) {

                        if(res.data.mmp){
                            if (res.code == 0) {
                                setTimeout(function () {
                                    $("#" + id).remove();
                                    layer.closeAll();
                                }, 1000);
                            }
                        }else {
                            layer.msg("还有此分类的文章，不能删除此分类", {
                                time: 1000
                            });
                        }


                    }
            )

        });
    }

</script>
</body>
</html>