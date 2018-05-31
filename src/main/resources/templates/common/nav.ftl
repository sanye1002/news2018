<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">
                <a href="/oa/user/index.html" target="_self" class="navbar-brand">
                    <small>
                        <span style="line-height: 40px">妙漫网创后台系统</span>
                    </small>
                </a>
            </div>
            <!-- /Navbar Barnd -->

            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <!-- /Sidebar Collapse -->
            <!-- Account Area and Settings --->
            <div class="navbar-header pull-right">
                <div class="navbar-account">
                    <ul class="account-area">
                        <#--<li class="" >

                            <a class=" dropdown-toggle" id="LifeShow"  data-toggle="dropdown" title="生活显示" >
                                <i class="icon fa  fa-rss"></i>
                            </a>
                            <!--Notification Dropdown&ndash;&gt;
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-notifications">

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <div class="notification-icon">
                                                <i class="fa fa-check bg-darkorange white"></i>
                                            </div>
                                            <div class="notification-body">
                                                <span class="title">登录地址</span>
                                                <span class="description" id="address"></span>
                                            </div>
                                            <div class="notification-extra">
                                                <i class="fa fa-map-marker darkorange"></i>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <div class="notification-icon">
                                                <i class="fa fa-gift bg-warning white"></i>
                                            </div>
                                            <div class="notification-body">
                                                <span class="title">当前时间</span>
                                                <span class="description" id="currentTime"></span>
                                            </div>
                                            <div class="notification-extra">

                                                <i class="fa fa-clock-o warning"></i>
                                                <span class="description" id="currentTimes"></span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <div class="notification-icon">
                                                <i class="fa fa-glass bg-success white"></i>
                                            </div>
                                            <div class="notification-body">
                                                <span class="title">当前天气状况</span>
                                                <span class="description" id="weather"></span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li class="dropdown-footer ">
                                        <span id="weekTime">

                                        </span>
                                    <span class="pull-right" id="temperature">
                                            10°c
                                            <%--<i class="wi wi-cloudy"></i>--%>
                                        </span>
                                </li>
                            </ul>
                            <!--/Notification Dropdown&ndash;&gt;
                        </li>-->
                        <li class="">
                            <a class="dropdown-toggle" data-toggle="dropdown" title="帮助中心" href="#">
                                <i class="icon fa fa-comments"></i>

                            </a>

                            <!--Messages Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-messages">
                                <li>
                                    <a href="http://wpa.qq.com/msgrd?V=3&uin=331139839&Site=QQ&Menu=yes" target="_blank">
                                        <img src="https://q.qlogo.cn/headimg_dl?bs=qq&dst_uin=331139839&spec=100" class="message-avatar" alt="三爷">
                                        <div class="message">
                                                <span class="message-sender">
                                                    Simon
                                                </span>
                                            <span class="message-time">
                                                    9:00~6:30
                                                </span>
                                            <span class="message-subject">
                                                    后台管理技术支持
                                                </span>
                                            <span class="message-body">
                                                    点击和我聊天，解决问题
                                                </span>
                                        </div>
                                    </a>
                                </li>


                            </ul>
                            <!--/Messages Dropdown-->
                        </li>
                        <li id="notice-box">
                            <a class="wave in dropdown-toggle" data-toggle="dropdown" title="通知" href="#">
                                <i class="icon fa fa-envelope"></i>
                                <span class="badge badge-darkorange graded" id="notice-number">0</span>
                            </a>
                            <!--Messages Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-messages" id="notice-list">
                                <#--<li>
                                    <a href="/oa/notice/system/read.html?id=">
                                        <img src="/layui/images/model.jpg" class="message-avatar"
                                             alt="Divyia Austin">
                                        <div class="message">
                                                <span class="message-sender">
                                                    发生人
                                                </span>
                                            <span class="message-time">
                                                    时间
                                                </span>
                                            <span class="message-subject">
                                                    标题
                                                </span>
                                        </div>
                                    </a>
                                </li>-->
                            </ul>
                            <!--/Messages Dropdown-->
                        </li>

                        <li>
                            <a class="login-area dropdown-toggle" data-toggle="dropdown">
                                <div class="avatar" title="个人信息">
                                    <img id="user-avatar-1" src="/layui/images/model.jpg" width="29px" height="29px">
                                </div>
                                <section>
                                    <h2><span class="profile"><span id="user-username-1">用户名</span></span></h2>
                                </section>
                            </a>
                            <!--Login Area Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area">
                                <li class="username"><a id="user-username-2">用户名</a></li>
                                <li class="email"><a id="user-phone">手机号码</a></li>
                                <!--Avatar Area-->
                                <li>
                                    <div class="avatar-area">
                                        <img id="user-avatar-2" src="/layui/images/model.jpg" width="128px" height="128px" class="avatar">
                                    </div>
                                </li>
                                <!--Avatar Area-->
                                <li class="edit">
                                    <a href="/oa/user/info.html" class="pull-left">个人资料</a>
                                    <a href="/oa/user/info.html" class="pull-right">设置</a>
                                </li>
                                <!--Theme Selector Area-->
                                <li class="theme-area">
                                    <ul class="colorpicker" id="skin-changer">
                                        <li><a class="colorpick-btn" href="#" style="background-color:#5DB2FF;"
                                               rel="/assets/css/skins/blue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#2dc3e8;"
                                               rel="/assets/css/skins/azure.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#03B3B2;"
                                               rel="/assets/css/skins/teal.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#53a93f;"
                                               rel="/assets/css/skins/green.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#FF8F32;"
                                               rel="/assets/css/skins/orange.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#cc324b;"
                                               rel="/assets/css/skins/pink.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#AC193D;"
                                               rel="/assets/css/skins/darkred.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#8C0095;"
                                               rel="/assets/css/skins/purple.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#0072C6;"
                                               rel="/assets/css/skins/darkblue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#585858;"
                                               rel="/assets/css/skins/gray.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#474544;"
                                               rel="/assets/css/skins/black.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#001940;"
                                               rel="/assets/css/skins/deepblue.min.css"></a></li>
                                    </ul>
                                </li>
                                <!--/Theme Selector Area-->
                                <li class="dropdown-footer">
                                    <a href="/login-out.html">
                                        退出
                                    </a>
                                </li>
                            </ul>
                            <!--/Login Area Dropdown-->
                        </li>
                        <!-- /Account Area -->
                        <!--Note: notice that setting div must start right after account area list.
                        no space must be between these elements-->
                        <!-- Settings -->
                    </ul>
                    <div class="setting">
                        <a id="btn-setting" title="Setting" href="#">
                            <i class="icon glyphicon glyphicon-cog"></i>
                        </a>
                    </div>
                    <div class="setting-container">
                        <label>
                            <input type="checkbox" id="checkbox_fixednavbar">
                            <span class="text">固定顶部</span>
                        </label>
                        <label>
                            <input type="checkbox" id="checkbox_fixedsidebar">
                            <span class="text">固定侧边</span>
                        </label>
                        <label>
                            <input type="checkbox" id="checkbox_fixedbreadcrumbs">
                            <span class="text">固定顶部侧边</span>
                        </label>
                        <label>
                            <input type="checkbox" id="checkbox_fixedheader">
                            <span class="text">固定全部</span>
                        </label>
                    </div>
                    <!-- Settings -->
                </div>
            </div>
            <!-- /Account Area and Settings -->
        </div>
    </div>
</div>