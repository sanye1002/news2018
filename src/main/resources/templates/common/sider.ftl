<div class="page-sidebar" id="sidebar">
    <!-- Page Sidebar Header-->
    <div class="sidebar-header-wrapper">
        <input type="text" class="searchinput"/>
        <i class="searchicon fa fa-search"></i>
        <div class="searchhelper">搜索</div>
    </div>
    <ul class="nav sidebar-menu">

        <li <#if pageId==1>class="active" </#if>>
            <a href="/oa/user/index.html">
                <i class="menu-icon glyphicon glyphicon-home"></i>
                <span class="menu-text"> 控制台 </span>
            </a>
        </li>
        <li <#if pageId==104>class="active" </#if>>
            <a href="/oa/article/issue.html">
                <i class="menu-icon fa fa-edit"></i>
                <span class="menu-text">文章发布</span>
            </a>
        </li>

        <li <#if pageId==121>class="active" </#if>>
            <a href="/oa/article/issue/img.html">
                <i class="menu-icon fa fa-picture-o"></i>
                <span class="menu-text">多图发布</span>
            </a>
        </li>

        <li <#if pageId==120>class="active" </#if>>
            <a href="/oa/article/video/index.html">
                <i class="menu-icon fa fa-video-camera"></i>
                <span class="menu-text">视频发布</span>
            </a>
        </li>


     <@shiro.hasPermission name="statistics:tag">
        <li <#if pageId==1000||pageId==1005>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-bar-chart-o"></i>
                <span class="menu-text">访问统计</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==1000>class="active" </#if>>
                    <a href="/oa/chart/ip.html">
                        <span class="menu-text">ip访问量查询</span>
                    </a>
                </li>
                <li <#if pageId==1005>class="active" </#if>>
                    <a href="/oa/chart/ip/list.html">
                        <span class="menu-text">ip访问图表</span>
                    </a>
                </li>
            </ul>
        </li>
     </@shiro.hasPermission>


    <@shiro.hasPermission name="articleManage:tag">
        <li <#if pageId==102||pageId==103||pageId==1012>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-align-right"></i>
                <span class="menu-text">文章处理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
               <#-- <li <#if pageId==101>class="active" </#if>>
                    <a href="/oa/article/auditlist.html">
                        <span class="menu-text">文章审核</span>
                    </a>
                </li>-->
                <li <#if pageId==102>class="active" </#if>>
                    <a href="/oa/article/showlist.html" class="menu-dropdown">
                        <span class="menu-text">文章展示</span>
                    </a>
                </li>
                <li <#if pageId==103>class="active" </#if>>
                    <a href="/oa/article/managelist.html" class="menu-dropdown">
                        <span class="menu-text">特殊板块文章展示</span>
                    </a>
                </li>
               <li <#if pageId==1012>class="active" </#if>>
                   <a href="/oa/article/top/list.html" class="menu-dropdown">
                       <span class="menu-text">文章顶置</span>
                   </a>
               </li>
            </ul>
        </li>
    </@shiro.hasPermission>

        <#--checkManage:tag-->
    <@shiro.hasPermission name="checkManage:tag">
        <li <#if pageId==101|| pageId==201||pageId==26>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-bolt"></i>
                <span class="menu-text">审核管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==101>class="active" </#if>>
                    <a href="/oa/article/auditlist.html">
                        <span class="menu-text">文章审核</span>
                    </a>
                </li>
                <li <#if pageId==201>class="active" </#if>>
                    <a href="/oa/reward/notes/list.html" class="menu-dropdown">
                        <span class="menu-text">提现审核</span>
                    </a>
                </li>
                <li <#if pageId==26>class="active" </#if>>
                    <a href="/oa/author/apply/list" class="menu-dropdown">
                        <span class="menu-text">作者申请</span>
                    </a>
                </li>
            </ul>
        </li>
    </@shiro.hasPermission>



    <@shiro.hasPermission name="reportManage:tag">
        <li <#if pageId==105|| pageId==106||pageId==107>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-exclamation"></i>
                <span class="menu-text">举报管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==105>class="active" </#if>>
                    <a href="/oa/article/reportlist.html">
                        <span class="menu-text">文章举报</span>
                    </a>
                </li>
                <li <#if pageId==106>class="active" </#if>>
                    <a href="/oa/report/comment/reportlist.html" class="menu-dropdown">
                        <span class="menu-text">评论举报</span>
                    </a>
                </li>
                <li <#if pageId==107>class="active" </#if>>
                    <a href="/oa/report/reply/reportlist.html" class="menu-dropdown">
                        <span class="menu-text">回复举报</span>
                    </a>
                </li>
            </ul>
        </li>
    </@shiro.hasPermission>


        <li <#if pageId==109|| pageId==110||pageId==108>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-user"></i>
                <span class="menu-text">个人中心</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==110>class="active" </#if>>
                    <a href="/oa/user/info.html" class="menu-dropdown">
                        <span class="menu-text">个人资料</span>
                    </a>
                </li>

                <li <#if pageId==108>class="active" </#if>>
                    <a href="/oa/article/user/articlelist.html" class="menu-dropdown">
                        <span class="menu-text">文章查询</span>
                    </a>
                </li>

                <li <#if pageId==109>class="active" </#if>>
                    <a href="/oa/article/draft/list.html" disabled>
                        <span class="menu-text">草稿查询</span>
                    </a>
                </li>
            </ul>
        </li>

        <li <#if pageId==202|| pageId==200>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-rmb"></i>
                <span class="menu-text">个人积分</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==22>class="active" </#if>>
                    <a href="/oa/reward/notes/user/list.html">
                        <span class="menu-text">积分提现</span>
                    </a>
                </li>
                <li <#if pageId==202>class="active" </#if>>
                    <a href="/oa/reward/rewardNotes/list.html" class="menu-dropdown">
                        <span class="menu-text">奖励记录</span>
                    </a>
                </li>
            </ul>
        </li>

       <@shiro.hasPermission name="system:tag">
            <li
            <#if pageId==45|| pageId==44|| pageId==23|| pageId==22>class="active open" </#if> >
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon fa fa-cog"></i>
                    <span class="menu-text">系统设置</span>

                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
            <@shiro.hasPermission name="permission:tag">
            <li <#if pageId==23|| pageId==22>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        权限设置
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==22>class="active" </#if>>
                        <a href="/oa/permission/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加权限</span>
                        </a>
                    </li>
                    <li <#if pageId==23>class="active" </#if>>
                        <a href="/oa/permission/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询权限</span>
                        </a>
                    </li>
                </ul>
            </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="role:tag">
                <li <#if pageId==44|| pageId==45>class=" open" </#if>>
                    <a href="#" class="menu-dropdown">
                                        <span class="menu-text">
                                            角色设置
                                        </span>
                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu">
                        <li <#if pageId==44>class="active" </#if>>
                            <a href="/oa/role/index.html">
                                <i class="menu-icon fa fa-rocket"></i>
                                <span class="menu-text">添加角色</span>
                            </a>
                        </li>
                        <li <#if pageId==45>class="active" </#if>>
                            <a href="/oa/role/list.html">
                                <i class="menu-icon glyphicon glyphicon-stats"></i>
                                <span class="menu-text">查询角色</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </@shiro.hasPermission>
                </ul>
            </li>
       </@shiro.hasPermission>


    <@shiro.hasPermission name="userManage:tag">
        <li <#if pageId==24|| pageId==25>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-group"></i>
                <span class="menu-text">用户管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==24>class="active" </#if>>
                    <a href="/oa/user/add.html">
                        <span class="menu-text">用户添加</span>
                    </a>
                </li>
                <li <#if pageId==25>class="active" </#if>>
                    <a href="/oa/user/list.html" class="menu-dropdown">
                        <span class="menu-text">用户查询</span>
                    </a>
                </li>

            </ul>
        </li>
    </@shiro.hasPermission>


    <@shiro.hasPermission name="classify:tag">
        <li <#if pageId==33|| pageId==34>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-tasks"></i>
                <span class="menu-text">分类管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==33>class="active" </#if>>
                    <a href="/oa/classify/index.html">
                        <span class="menu-text">分类添加</span>
                    </a>
                </li>
                <li <#if pageId==34>class="active" </#if>>
                    <a href="/oa/classify/alllist.html" class="menu-dropdown">
                        <span class="menu-text">分类查询</span>
                    </a>
                </li>
            </ul>
        </li>
    </@shiro.hasPermission>

     <@shiro.hasPermission name="link:tag">
        <li <#if pageId==500|| pageId==501>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-link"></i>
                <span class="menu-text">链接管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==500>class="active" </#if>>
                    <a href="/oa/link/index.html">
                        <span class="menu-text">链接添加</span>
                    </a>
                </li>
                <li <#if pageId==501>class="active" </#if>>
                    <a href="/oa/link/list.html" class="menu-dropdown">
                        <span class="menu-text">链接查询</span>
                    </a>
                </li>
            </ul>
        </li>
     </@shiro.hasPermission>

    <@shiro.hasPermission name="radio:tag">
        <li <#if pageId==600|| pageId==601|| pageId==602>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-headphones"></i>
                <span class="menu-text">电台管理</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==600>class="active" </#if>>
                    <a href="/oa/radio/index.html">
                        <span class="menu-text">电台添加</span>
                    </a>
                </li>
                <li <#if pageId==601>class="active" </#if>>
                    <a href="/oa/radio/list.html" class="menu-dropdown">
                        <span class="menu-text">电台查询</span>
                    </a>
                </li>
                <li <#if pageId==602>class="active" </#if>>
                    <a href="/oa/radio/list/letter.html" class="menu-dropdown">
                        <span class="menu-text">用户留言</span>
                    </a>
                </li>
            </ul>
        </li>
        </@shiro.hasPermission>
    <#--
    <@shiro.hasPermission name="personnelSalary:tag">


    <!--UI 个人工资&ndash;&gt;



    <!--UI 员工预支&ndash;&gt;
        <@shiro.hasPermission name="personnelSalaryAdvanc:tag">
        <li <#if pageId==2|| pageId==3>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-desktop"></i>
                <span class="menu-text">员工预支</span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==2>class="active" </#if>>
                    <a href="/oa/personnelSalaryAdvance/list/user.html">
                        <span class="menu-text">记录查询</span>
                    </a>
                </li>
                <li <#if pageId==3>class="active" </#if>>
                    <a href="/oa/personnelSalaryAdvance/index.html" class="menu-dropdown">
                        <span class="menu-text">预支申请</span>
                    </a>
                </li>
            </ul>
        </li>
        </@shiro.hasPermission>
    <!--主播预支&ndash;&gt;
         <@shiro.hasPermission name="anchorSalaryAdvance:tag">
        <li <#if pageId==4|| pageId==5>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-table"></i>
                <span class="menu-text"> 主播预支 </span>
                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==4>class="active" </#if>>
                    <a href="/oa/anchorSalaryAdvance/list/user.html">
                        <span class="menu-text">记录查询</span>
                    </a>
                </li>
                <li <#if pageId==5>class="active" </#if>>
                    <a href="/oa/anchorSalaryAdvance/index.html">
                        <span class="menu-text">预支申请</span>
                    </a>
                </li>
            </ul>
        </li>
         </@shiro.hasPermission>
        <!--主播提现&ndash;&gt;
             <@shiro.hasPermission name="spending:tag">
         <li <#if pageId==7|| pageId==8>class="open" </#if>>
             <a href="#" class="menu-dropdown">
                 <i class="menu-icon fa fa-building-o"></i>
                 <span class="menu-text"> 日常开支 </span>

                 <i class="menu-expand"></i>
             </a>

             <ul class="submenu">
                 <li <#if pageId==7>class="active" </#if>>
                     <a href="/oa/spending/list/user.html">
                         <span class="menu-text">申请记录</span>
                     </a>
                 </li>

                 <li <#if pageId==8>class="active" </#if>>
                     <a href="/oa/spending/index.html">
                         <span class="menu-text">开支申请</span>
                     </a>
                 </li>

             </ul>
         </li>
             </@shiro.hasPermission>
        <!--物品借记&ndash;&gt;
             <@shiro.hasPermission name="itemDebit:tag">

        <li  <#if pageId==9|| pageId==10>class="open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-envelope-o"></i>
                <span class="menu-text"> 物品借记 </span>

                <i class="menu-expand"></i>
            </a>

            <ul class="submenu">
                <li <#if pageId==9>class="active" </#if>>
                    <a href="/oa/itemDebit/list/user.html">
                        <span class="menu-text">借记记录</span>
                    </a>
                </li>

                <li <#if pageId==10>class="active" </#if>>
                    <a href="/oa/itemDebit/index.html">
                        <span class="menu-text">借记添加</span>
                    </a>
                </li>
            </ul>
        </li>
             </@shiro.hasPermission>

        <!--M人员管理&ndash;&gt;
             <@shiro.hasPermission name="staff:tag">
        <li
        <#if pageId==11|| pageId==12|| pageId==13|| pageId==14|| pageId==15|| pageId==16>class="active open" </#if> >
            <a href="#" class="menu-dropdown">
                <i class="menu-icon fa fa-user"></i>
                <span class="menu-text">人员管理</span>

                <i class="menu-expand"></i>
            </a>

            <ul class="submenu">



         <@shiro.hasPermission name="personnel:tag">
            <li <#if pageId==12|| pageId==11>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        工作人员
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==11>class="active" </#if>>
                        <a href="/oa/personnel/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加人员</span>
                        </a>
                    </li>
                    <li <#if pageId==12>class="active" </#if>>
                        <a href="/oa/personnel/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询人员</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
         <@shiro.hasPermission name="anchorUser:tag">
            <li <#if pageId==13|| pageId==14>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        主播用户
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==13>class="active" </#if>>
                        <a href="/oa/anchor/user/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加用户</span>
                        </a>
                    </li>
                    <li <#if pageId==14>class="active" </#if>>
                        <a href="/oa/anchor/user/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询用户</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
         <@shiro.hasPermission name="anchor:tag">
            <li <#if pageId==15|| pageId==16>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        平台主播
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==15>class="active" </#if>>
                        <a href="/oa/anchor/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加主播</span>
                        </a>
                    </li>
                    <li <#if pageId==16>class="active" </#if>>
                        <a href="/oa/anchor/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询主播</span>
                        </a>
                    </li>
                </ul>
            </li>
         </@shiro.hasPermission>
            </ul>
        </li>
             </@shiro.hasPermission>
        <!--部门管理&ndash;&gt;
             <@shiro.hasPermission name="dept:tag">
        <li <#if pageId==17|| pageId==18>class="active open" </#if>>
            <a href="#" class="menu-dropdown">
                <i class="menu-icon glyphicon glyphicon-paperclip"></i>
                <span class="menu-text"> 部门管理 </span>

                <i class="menu-expand"></i>
            </a>
            <ul class="submenu">
                <li <#if pageId==17>class="active" </#if>>
                    <a href="/oa/dept/index.html">
                        <span class="menu-text">添加部门</span>
                    </a>
                </li>
                <li <#if pageId==18>class="active" </#if>>
                    <a href="/oa/dept/list.html">
                        <span class="menu-text">查询部门</span>
                    </a>
                </li>

            </ul>
        </li>
             </@shiro.hasPermission>
        <!--平台管理&ndash;&gt;
             <@shiro.hasPermission name="platform:tag">
            <li <#if pageId==19|| pageId==20>class="active open" </#if>>
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon glyphicon glyphicon-paperclip"></i>
                    <span class="menu-text"> 平台管理 </span>

                    <i class="menu-expand"></i>
                </a>
                <ul class="submenu">
                    <li <#if pageId==19>class="active" </#if>>
                        <a href="/oa/platform/index.html">
                            <span class="menu-text">添加平台</span>
                        </a>
                    </li>
                    <li <#if pageId==20>class="active" </#if>>
                        <a href="/oa/platform/list.html">
                            <span class="menu-text">平台查询</span>
                        </a>
                    </li>
                </ul>
            </li>
             </@shiro.hasPermission>
        <!--系统设置&ndash;&gt;
             <@shiro.hasPermission name="system:tag">
            <li
            <#if pageId==21|| pageId==22|| pageId==23|| pageId==24|| pageId==32|| pageId==33|| pageId==38|| pageId==39>class="active open" </#if> >
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon fa fa-cog"></i>
                    <span class="menu-text">系统设置</span>

                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
            <@shiro.hasPermission name="permission:tag">
            <li <#if pageId==21|| pageId==22>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        权限设置
                                    </span>
                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
                    <li <#if pageId==21>class="active" </#if>>
                        <a href="/oa/permission/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">添加权限</span>
                        </a>
                    </li>
                    <li <#if pageId==22>class="active" </#if>>
                        <a href="/oa/permission/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">查询权限</span>
                        </a>
                    </li>
                </ul>
            </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="role:tag">
                <li <#if pageId==23|| pageId==24>class=" open" </#if>>
                    <a href="#" class="menu-dropdown">
                                        <span class="menu-text">
                                            角色设置
                                        </span>
                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu">
                        <li <#if pageId==23>class="active" </#if>>
                            <a href="/oa/role/index.html">
                                <i class="menu-icon fa fa-rocket"></i>
                                <span class="menu-text">添加角色</span>
                            </a>
                        </li>
                        <li <#if pageId==24>class="active" </#if>>
                            <a href="/oa/role/list.html">
                                <i class="menu-icon glyphicon glyphicon-stats"></i>
                                <span class="menu-text">查询角色</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="item:tag">
                 <li <#if pageId==32|| pageId==33>class=" open" </#if>>
                     <a href="#" class="menu-dropdown">
                                            <span class="menu-text">
                                                物品管理
                                            </span>
                         <i class="menu-expand"></i>
                     </a>

                     <ul class="submenu">
                         <li <#if pageId==23>class="active" </#if>>
                             <a href="/oa/item/index.html">
                                 <i class="menu-icon fa fa-rocket"></i>
                                 <span class="menu-text">添加物品</span>
                             </a>
                         </li>
                         <li <#if pageId==24>class="active" </#if>>
                             <a href="/oa/item/list.html">
                                 <i class="menu-icon glyphicon glyphicon-stats"></i>
                                 <span class="menu-text">查询物品</span>
                             </a>
                         </li>
                     </ul>
                 </li>
            </@shiro.hasPermission>
                    <@shiro.hasPermission name="feedbackType:tag">
                 <li <#if pageId==38|| pageId==39>class=" open" </#if>>
                     <a href="#" class="menu-dropdown">
                                            <span class="menu-text">
                                                反馈类型
                                            </span>
                         <i class="menu-expand"></i>
                     </a>

                     <ul class="submenu">
                         <li <#if pageId==38>class="active" </#if>>
                             <a href="/oa/feedback/type/index.html">
                                 <i class="menu-icon fa fa-rocket"></i>
                                 <span class="menu-text">添加类型</span>
                             </a>
                         </li>
                         <li <#if pageId==39>class="active" </#if>>
                             <a href="/oa/feedback/type/list.html">
                                 <i class="menu-icon glyphicon glyphicon-stats"></i>
                                 <span class="menu-text">查询类型</span>
                             </a>
                         </li>
                     </ul>
                 </li>
                    </@shiro.hasPermission>
                </ul>
            </li>
             </@shiro.hasPermission>
        <!--审核管理&ndash;&gt;
        <!--Right to Left&ndash;&gt;
             <@shiro.hasPermission name="check:tag">

          <li <#if pageId==25||pageId==40>class="open" </#if>>
              <a href="#" class="menu-dropdown">
                  <i class="menu-icon fa fa-align-right"></i>
                  <span class="menu-text">审核管理</span>

                  <i class="menu-expand"></i>
              </a>
              <ul class="submenu">
                  <li <#if pageId==25>class="active" </#if>>
                      <a href="/oa/check/list.html">
                          <span class="menu-text">审核记录</span>
                      </a>
                  </li>
                  <li <#if pageId==40>class="active" </#if>>
                      <a href="/oa/feedback/info/check.html">
                          <span class="menu-text">反馈记录</span>
                      </a>
                  </li>
              </ul>
          </li>
             </@shiro.hasPermission>
        <!--财务出纳&ndash;&gt;
             <@shiro.hasPermission name="caiwu:tag">
               <li <#if pageId==26|| pageId==27|| pageId==28|| pageId==29|| pageId==34>class="open" </#if>>
                   <a href="#" class="menu-dropdown">
                       <i class="menu-icon fa fa-plane"></i>
                       <span class="menu-text">财务出纳</span>

                       <i class="menu-expand"></i>
                   </a>
                   <ul class="submenu">
                       <li <#if pageId==26>class="active" </#if>>
                           <a href="/oa/spending/checkList.html">
                               <span class="menu-text">日常开支</span>
                           </a>
                       </li>
                       <li <#if pageId==27>class="active" </#if>>
                           <a href="/oa/anchorSalaryAdvance/checkList.html">
                               <span class="menu-text">主播预支</span>
                           </a>
                       </li>
                       <li <#if pageId==28>class="active" </#if>>
                           <a href="/oa/withdraw/list.html">
                               <span class="menu-text">主播提现</span>
                           </a>
                       </li>
                       <li <#if pageId==29>class="active" </#if>>
                           <a href="/oa/personnelSalaryAdvance/checkList.html">
                               <span class="menu-text">员工预支</span>
                           </a>
                       </li>
                       <li <#if pageId==34>class="active" </#if>>
                           <a href="/oa/itemDebit/list.html">
                               <span class="menu-text">物品出库</span>
                           </a>
                       </li>
                   </ul>
               </li>
             </@shiro.hasPermission>
             <!--工资管理&ndash;&gt;
             <@shiro.hasPermission name="salary:tag">
                <li <#if pageId==30|| pageId==31>class="active open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon glyphicon glyphicon-credit-card"></i>
                        <span class="menu-text"> 工资管理 </span>

                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==30>class="active" </#if>>
                            <a href="/oa/personnelSalary/list.html">
                                <span class="menu-text">工作人员工资</span>
                            </a>
                        </li>
                        <li <#if pageId==31>class="active" </#if>>
                            <a href="/oa/anchorSalary/list/momo.html">
                                <span class="menu-text">陌陌主播工资</span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>
             <@shiro.hasPermission name="union:tag">
                <li <#if pageId==35|| pageId==36>class="active open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon fa fa-laptop"></i>
                        <span class="menu-text"> 工会管理 </span>

                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==35>class="active" </#if>>
                        &lt;#&ndash;https://live-star.immomo.com/guild/#/signed&ndash&ndash;&gt;
                            <a href="/momoUnion.html" target="" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 陌陌工会 </span>
                            </a>
                        </li>
                        <li <#if pageId==36>class="active" </#if>>
                        &lt;#&ndash;https://live-star.immomo.com/guild/#/signed&ndash&ndash;&gt;
                            <a href="/KKUnion.html" target="_blank" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 唱响家族 </span>
                            </a>
                            <a href="http://www.cdrysj.com/oa/login/index" target="_blank" class="menu-dropdown">
                                <i class=" "></i>
                                <span class="menu-text"> 官网后台 </span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>

    <!--反馈&ndash;&gt;
             <@shiro.hasPermission name="feedback:tag">
                <li <#if pageId==41|| pageId==42>class="open" </#if>>
                    <a href="#" class="menu-dropdown">
                        <i class="menu-icon fa fa-comment"></i>
                        <span class="menu-text"> 反馈管理 </span>
                        <i class="menu-expand"></i>
                    </a>
                    <ul class="submenu">
                        <li <#if pageId==42>class="active" </#if>>
                            <a href="/oa/feedback/info/index.html">
                                <span class="menu-text">反馈添加</span>
                            </a>
                        </li>
                        <li <#if pageId==41>class="active" </#if>>
                            <a href="/oa/feedback/info/list.html">
                                <span class="menu-text">反馈查询</span>
                            </a>
                        </li>
                    </ul>
                </li>
             </@shiro.hasPermission>
    <!--通知管理&ndash;&gt;

      <@shiro.hasPermission name="notice:tag">
            <li
            <#if pageId==44|| pageId==45|| pageId==46|| pageId==47>class="active open" </#if> >
                <a href="#" class="menu-dropdown">
                    <i class="menu-icon fa  fa-bullhorn"></i>
                    <span class="menu-text">通知管理</span>

                    <i class="menu-expand"></i>
                </a>

                <ul class="submenu">
            <@shiro.hasPermission name="noticeMessage:tag">
                <li <#if pageId==44|| pageId==45>class=" open" </#if>>
                <a href="#" class="menu-dropdown">
                                    <span class="menu-text">
                                        短信通知
                                    </span>
                    <i class="menu-expand"></i>
                </a>
                <ul class="submenu">
                    <li <#if pageId==44>class="active" </#if>>
                        <a href="/oa/notice/message/index.html">
                            <i class="menu-icon fa fa-rocket"></i>
                            <span class="menu-text">通知发送</span>
                        </a>
                    </li>
                    <li <#if pageId==45>class="active" </#if>>
                        <a href="/oa/notice/message/list.html">
                            <i class="menu-icon glyphicon glyphicon-stats"></i>
                            <span class="menu-text">通知记录</span>
                        </a>
                    </li>
                </ul>
            </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="noticeSystem:tag">
                <li <#if pageId==46|| pageId==47>class=" open" </#if>>
                    <a href="#" class="menu-dropdown">
                                        <span class="menu-text">
                                            系统通知
                                        </span>
                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu">
                        <li <#if pageId==46>class="active" </#if>>
                            <a href="/oa/notice/system/index.html">
                                <i class="menu-icon fa fa-rocket"></i>
                                <span class="menu-text">添加发送</span>
                            </a>
                        </li>
                        <li <#if pageId==47>class="active" </#if>>
                            <a href="/oa/notice/system/list.html">
                                <i class="menu-icon glyphicon glyphicon-stats"></i>
                                <span class="menu-text">记录查询</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </@shiro.hasPermission>

                </ul>
            </li>
      </@shiro.hasPermission>
    <!--个人通知&ndash;&gt;
     <@shiro.hasPermission name="noticeUser:tag">
        <li <#if pageId==43>class="active" </#if>>
            <a href="/oa/notice/system/list/user.html">
                <i class="menu-icon glyphicon glyphicon-bell"></i>
                <span class="menu-text"> 通知消息 </span>
            </a>
        </li>
     </@shiro.hasPermission>
-->
    </ul>
</div>