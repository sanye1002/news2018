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
                <div class="row row_add_character ">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">添加角色</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form id="formRole" class="form-horizontal form-bordered" role="form">
                                        <div class="form-group">
                                            <label for="name"
                                                   class="col-sm-2 control-label no-padding-right">名称：</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="name" value="${roleDTO.getName()!}" id="name"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="description"
                                                   class="col-sm-2 control-label no-padding-right">描述：</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="description" id="description"
                                                       value="${roleDTO.getDescription()!}"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="level"
                                                   class="col-sm-2 control-label no-padding-right">等级：</label>
                                            <div class="col-sm-10">
                                                <input type="number" name="level" value="${roleDTO.getLevel()!}" id="level"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label no-padding-right">权限：</label>
                                            <div class="col-sm-10">
                                                <#if  roleDTO.getId()==0>
                                                    <#list permissionList as permission>
                                                        <div class="col-lg-2 col-sm-2 col-xs-2">
                                                            <div class="checkbox">
                                                                <label>
                                                                    <input type="checkbox" name="level"
                                                                           value="${permission.getId()}">
                                                                    <span class="text">${permission.getName()}</span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </#list>
                                                <#else >
                                                    <#list checkPermissionList as check >

                                                            <div class="col-lg-2 col-sm-2 col-xs-2">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox"
                                                                               name="level" checked
                                                                               value="${check.getId()}">
                                                                        <span class="text">${check.getName()}</span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                    </#list>
                                                    <#list permissionList as permission >

                                                            <div class="col-lg-2 col-sm-2 col-xs-2">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox"
                                                                               name="level"
                                                                               value="${permission.getId()}">
                                                                        <span class="text">${permission.getName()}</span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                    </#list>

                                                </#if>
                                            </div>
                                        </div>
                                        <div class="form-group text-align-right">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <a class="btn btn-success" id="roleSubmit"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 提交</a>

                                                <a class="btn btn-warning" id="role_list"><i
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
    $("#role_list").click(function () {
        location = "/oa/role/list.html"
    })

    $(function () {
        $("#roleSubmit").click(function () {
            var id = "${roleDTO.getId()!}";
            var name = $("#name").val();
            var level = $("#level").val();
            var description = $("#description").val();
            var idList = $("input[name='level']:checked").map(function () {
                return $(this).val();
            }).get();
            if (name==""){
                layer.msg("名称不能为空", {
                    time: 1000
                });
            }else if(description==""){
                layer.msg("描述不能为空", {
                    time: 1000
                });
            }else if(idList.length==0){
                layer.msg("描述不能为空", {
                    time: 1000
                });
            }else if(level==""){
                layer.msg("级别必须输入", {
                    time: 1000
                });
            }else {
                $.ajax({
                    type: 'POST',
                    url: "/oa/role/save",
                    contentType: 'application/json;charset=UTF-8',
                    dataType: 'json',
                    data: JSON.stringify({
                        id: id,
                        name: name,
                        level: level,
                        description: description,
                        idList: idList
                    }),
                    success: function (res) {
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
                                location = "/oa/role/list.html";
                            }, 1000);
                        }
                    },
                    error: function () {
                        layer.msg("失败！", {
                            time: 1000
                        });
                    },
                    processData: false,
                });
            }
        })
    })
</script>
</body>
</html>