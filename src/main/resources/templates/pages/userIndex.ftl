<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
<style>
    @media (max-width: 800px) {
        #contact-map {
            display: none;
        }
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
                    <div class="col-md-12">
                        <div class="profile-container">
                            <div class="profile-header row">
                                <div class="col-lg-2 col-md-4 col-sm-12 text-center">
                                    <img <#if userInfo.getAvatar()! == ""> src="/layui/images/model.jpg"<#else >
                                                                           src="${userInfo.getAvatar()!}"</#if>
                                                                           alt="赶紧上传自己的头像吧" class="header-avatar"/>
                                </div>
                                <div class="col-lg-5 col-md-8 col-sm-12 profile-info">
                                    <div class="header-fullname">${userInfo.getNikeName()!}</div>
                                    <a href="/oa/user/info.html" class="btn btn-palegreen btn-sm  btn-follow">
                                        <i class="fa fa-sun-o"></i>
                                        更改资料
                                    </a>
                                    <div class="header-information">
                                        亲爱的${userInfo.getNikeName()!}，你于${userInfo.getCreateDate()!}
                                        加入系统，最近修改个人信息时间为${userInfo.getUpdateDate()!}
                                    </div>
                                </div>
                                <div class="col-lg-5 col-md-12 col-sm-12 col-xs-12 profile-stats">
                                    <div class="row">
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 stats-col">
                                            <div class="stats-value pink">${userInfo.getCreateDate()!}</div>
                                            <div class="stats-title">加入时间</div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 stats-col">
                                            <div class="stats-value pink">${userInfo.getUpdateDate()!}</div>
                                            <div class="stats-title">修改时间</div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 stats-col">
                                            <div class="stats-value pink">
                                                竞标中...
                                            </div>
                                            <div class="stats-title">附加栏目
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="profile-body">
                                <div class="col-lg-12">
                                    <div class="tabbable">
                                        <div class="tab-content tabs-flat">
                                            <div id="contacts">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="profile-contacts">

                                                            <div class="profile-badge orange"><i
                                                                    class="fa fa-phone orange"></i><span>联系方式</span>
                                                            </div>
                                                            <div class="contact-info">
                                                                <p>
                                                                    <span>电话：</span>${userInfo.getPhone()!}
                                                                    <br><br>
                                                                </p>
                                                            </div>
                                                            <div class="profile-badge azure">
                                                                <i class="fa fa-map-marker azure"></i><span>个人信息</span>
                                                            </div>
                                                            <div class="contact-info">
                                                                <p>
                                                                    <span>姓名：</span>${userInfo.getName()!}<br><br>
                                                                    <span>昵称：</span>${userInfo.getNikeName()!}
                                                                    <br><br>
                                                                    <br><br>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <img id="contact-map" class="animated flipInY" alt="赶紧上传自己的头像吧"
                                                             <#if userInfo.getAvatar()! =="">
                                                             src="/layui/images/model.jpg"<#else >
                                                             src="${userInfo.getAvatar()!}"</#if>/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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


</body>
</html>