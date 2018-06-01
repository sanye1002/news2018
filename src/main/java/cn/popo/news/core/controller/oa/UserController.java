package cn.popo.news.core.controller.oa;


import cn.popo.news.core.entity.common.Role;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.UserForm;
import cn.popo.news.core.service.RolePermissionService;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.Encrypt;
import cn.popo.news.core.utils.MobileExactUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.ShiroGetSession;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/29 14:10
 * @Desc    用户控制中心
 */

@Controller
@RequestMapping("/oa/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 用户展示
     */
    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public ModelAndView listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "status", defaultValue = "1") Integer status,
                                 Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<User> pageDTO = userService.findAllByUserTypeAndStatusAndShowStatus(pageRequest, "0", status);
        map.put("pageId", 25);
        map.put("status", status);
        map.put("pageTitle", "主播用户列表");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/user/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("pages/anchorUserList", map);
    }


    /**
     * 用户添加页面展示
     */
    @GetMapping("/add")
    @RequiresPermissions("user:add")
    public ModelAndView userIndex(@RequestParam(value = "id", defaultValue = "0") String id,
                                  Map<String, Object> map) {
        User userInfo = new User();
        if (!id.equals("0")) {
            userInfo = userService.findOne(id);
            map.put("edit", 1);
            map.put("pageTitle", "用户编辑");
        } else {
            map.put("edit", 0);
            map.put("pageTitle", "用户添加");
        }

        /**权限**/
        List<Role> roleList = rolePermissionService.findAllRole();
        map.put("pageId", 24);

        map.put("roleList", roleList);
        map.put("userInfo", userInfo);
        return new ModelAndView("pages/anchorUserAdd");
    }

    /**
     * 用户上传
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveUser(@Valid UserForm userForm,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        log.info("【form】={}",userForm.toString());
        userService.saveUser(userForm);
        return ResultVOUtil.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteUser(String userId) {

        userService.deleteUser(userId);
        return ResultVOUtil.success();
    }

    /**
     * 后台首页
     */
    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map) {
        User userInfo = ShiroGetSession.getUser();
        map.put("pageId", 1);
        map.put("pageTitle", "首页");
        map.put("userInfo", userInfo);
        return new ModelAndView("pages/userIndex", map);

    }


    /**
     * 个人页面
     */
    @GetMapping("/info")
    public ModelAndView userInfo(Map<String, Object> map) {
        map.put("pageId", 110);
        map.put("pageTitle", "个人资料");
        map.put("userInfo", ShiroGetSession.getUser());
        return new ModelAndView("pages/userInfo", map);
    }


    /**
     * 密码修改
     */
    @PostMapping("/password/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveUser(@RequestParam(value = "oldPassword") String oldPassword,
                                                  @RequestParam(value = "newPassword") String newPassword) {
        User userInfo = ShiroGetSession.getUser();
        if (!Encrypt.md5(oldPassword).equals(userInfo.getPassword())) {
            return ResultVOUtil.error(100, "输入的原密码不正确");
        }
        userInfo.setPassword(Encrypt.md5(newPassword));
        User user = userService.saveByUser(userInfo);
        ShiroGetSession.removeUser();
        return ResultVOUtil.success();
    }

    /**
     * 头像修改
     */
    @PostMapping("/avatar/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveAvatar(@RequestParam(value = "id") String id,
                                                    @RequestParam(value = "avatar") String avatar) {
        User userInfo = userService.findOne(id);
        userInfo.setAvatar(avatar);
        User user = userService.saveByUser(userInfo);
        ShiroGetSession.setUser(user);
        return ResultVOUtil.success();
    }

    @PostMapping("/info/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> updateUser(@RequestParam(value = "name") String name,
                                                  @RequestParam(value = "phone") String phone,
                                                  @RequestParam(value = "nikeName") String nikeName) {
        User userInfo = ShiroGetSession.getUser();
        userInfo.setName(name);
        userInfo.setNikeName(nikeName);
        userInfo.setPhone(phone);
        User user = userService.saveByUser(userInfo);
        ShiroGetSession.setUser(user);
        return ResultVOUtil.success();
    }

    @PostMapping("/phone")
    @ResponseBody
    public ResultVO<Map<String, Object>> findPhone(@RequestParam(value = "phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        if (ShiroGetSession.getUser().getPhone().equals(phone)) {
            map.put("code", 0);
            map.put("message", "无变化");
            return ResultVOUtil.success(map);
        }
        if (MobileExactUtil.isMobileExact(phone)) {
            map = userService.findAllByPhone(phone);
        } else {
            map.put("code", 100);
            map.put("message", "请输入正确的手机号！");
        }
        return ResultVOUtil.success(map);

    }
    @PostMapping("/info")
    @ResponseBody
    public ResultVO<Map<String, Object>> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", ShiroGetSession.getUser());
        return ResultVOUtil.success(map);
    }
}
