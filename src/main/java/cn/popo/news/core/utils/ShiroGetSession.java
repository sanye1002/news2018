package cn.popo.news.core.utils;

import cn.popo.news.core.dto.RoleDTO;
import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.RolePermissionService;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 3:43
 */
@Slf4j
@Component
public class ShiroGetSession {

    @Autowired
    private RolePermissionService rolePermissionService;
    public static User getUser(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User userInfo = (User) session.getAttribute("user");
        if (userInfo==null){
            session.removeAttribute("permissionList");
            throw new UnauthenticatedException();
        }
        System.out.println(GetTimeUtil.getDate()+":"+ userInfo.getName());
        return userInfo;
    }


    public static void removeUser(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute("user");
        session.removeAttribute("permissionList");
    }
    public static void setUser(User user){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);
    }
    public  List<Permission> permissionList(){
        List<Permission> permissionList = new ArrayList<>();


        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        permissionList = (List<Permission>) session.getAttribute("permissionList");
        if (permissionList==null){
            RoleDTO roleDTO = rolePermissionService.findOne(ShiroGetSession.getUser().getRoleId());
            permissionList = roleDTO.getPermissionList();
            session.setAttribute("permissionList",permissionList);
        }
        return permissionList;
    }


}
