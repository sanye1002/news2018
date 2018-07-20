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
                <form enctype="multipart/form-data" id="batchUpload"  action="user/upload" method="post" class="form-horizontal">
                    <button class="btn btn-success btn-xs" id="uploadEventBtn" style="height:26px;"  type="button" >选择文件</button>
                    <input type="file" name="file"  style="width:0px;height:0px;" id="uploadEventFile">
                    <input id="uploadEventPath"  disabled="disabled"  type="text" placeholder="请选择excel表" style="border: 1px solid #e6e6e6; height: 26px;width: 200px;" >
                </form>

                <button id="sc" type="button" class="btn btn-success btn-sm"  onclick="user.uploadBtn()" >上传</button>
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
<script>

    var User = function(){

        this.init = function(){

            //模拟上传excel
            $("#uploadEventBtn").unbind("click").bind("click",function(){
                $("#uploadEventFile").click();
            });
            $("#uploadEventFile").bind("change",function(){
                $("#uploadEventPath").attr("value",$("#uploadEventFile").val());
            });

        };
        //点击上传按钮
        this.uploadBtn = function(){
            var uploadEventFile = $("#uploadEventFile").val();
            if(uploadEventFile == ''){
                alert("请选择excel,再上传");
            }else if(uploadEventFile.lastIndexOf(".xls")<0){//可判断以.xls和.xlsx结尾的excel
                alert("只能上传Excel文件");
            }else{
                var url =  '/api/test/save';
                var formData = new FormData($('form')[0]);
                user.sendAjaxRequest(url,'POST',formData);
            }
        };

        this.sendAjaxRequest = function(url,type,data){
            $.ajax({
                url : url,
                type : type,
                data : data,
                success : function(result) {
                    $("#sc").attr("disabled",true);
                    console.log(result)
                },
                error : function() {
                    alert("失败")
                },
                cache : false,
                contentType : false,
                processData : false
            });
        };
    }


    var user;
    $(function(){
        user = new User();
        user.init();
    });
</script>
</body>
</html>