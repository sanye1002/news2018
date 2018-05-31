package cn.popo.news.core.controller.oa.install;

import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.entity.common.Role;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.RolePermissionService;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.Encrypt;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/install")
public class OAInstallController {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;
    //@GetMapping("/go")
    public ModelAndView init(Map<String, Object> map) {
        //权限初始化
        if (rolePermissionService.findAllPermission().isEmpty()) {
            rolePermissionService.savePermission(new Permission("首页标签", "userIndex:tag", "基本权限"));
            rolePermissionService.savePermission(new Permission("系统管理标签", "system:tag", "系统设置主标签"));
            rolePermissionService.savePermission(new Permission("权限标签", "permission:tag", "权限标签"));
            rolePermissionService.savePermission(new Permission("角色标签", "role:tag", "角色标签"));
            rolePermissionService.savePermission(new Permission("权限添加", "permission:add", "系统权限添加"));
            rolePermissionService.savePermission(new Permission("权限列表", "permission:list", "系统权限列表"));
            rolePermissionService.savePermission(new Permission("角色添加", "role:add", "系统角色添加"));
            rolePermissionService.savePermission(new Permission("角色列表", "role:list", "系统角色列表"));
        }
        //角色初始化
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        if (rolePermissionService.findAllRole().isEmpty()) {
            Role role = new Role();
            role.setName("系统初始化");
            role.setDescription("系统初始化");
            role.setLevel(2);
            Role result1 = rolePermissionService.saveRole(role);
            List<Integer> integerList1 = permissionList.stream().map(e -> e.getId()).collect(Collectors.toList());
            rolePermissionService.save(result1.getId(), integerList1);

            //人员添加
            if (true){
                User userInfo = new User();
                userInfo.setPhone("18188888888");
                userInfo.setName("初始化账号");
                userInfo.setNikeName("初始化账号");
                userInfo.setPassword(Encrypt.md5("18188888888"));
                userInfo.setAvatar("/layui/images/model.jpg");
                userInfo.setRoleId(result1.getId());
                userInfo.setUserType("0");
                userInfo.setShowStatus(1);
                userInfo.setStatus(1);
                userInfo.setUserId(KeyUtil.genUniqueKey());
                userInfo.setCreateDate(GetTimeUtil.getTime());
                userInfo.setUpdateDate(GetTimeUtil.getTime());
                User resultUser = userService.saveByUser(userInfo);
            }
        }
        map.put("message", "初始化成功！");
        map.put("url", "/login.html");
        return new ModelAndView("common/success", map);
    }
}
