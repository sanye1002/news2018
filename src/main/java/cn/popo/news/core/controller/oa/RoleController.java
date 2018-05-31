package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.RoleDTO;
import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.entity.common.Role;
import cn.popo.news.core.entity.form.RoleForm;
import cn.popo.news.core.service.RolePermissionService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 4:13
 */
@Controller
@RequestMapping("/oa/role")

public class RoleController {

    @Autowired
    private RolePermissionService rolePermissionService;


    /**
     * 角色添加页面
     */
    @RequiresAuthentication
    @GetMapping("/index")
    @RequiresPermissions("role:add")
    public ModelAndView index(Map<String, Object> map,
                              @RequestParam(value = "id", defaultValue = "") Integer id) {

        RoleDTO roleDTO = new RoleDTO();
        List<Permission> permissionList = new ArrayList<>();
        if (id != null) {
            roleDTO = rolePermissionService.findOne(id);
            map.put("pageTitle", "角色编辑");
            permissionList = rolePermissionService.findAllPermission();
            permissionList.removeAll(roleDTO.getPermissionList());
            map.put("checkPermissionList",roleDTO.getPermissionList());
        } else {
            roleDTO.setId(0);
            map.put("pageTitle", "角色添加");
            permissionList = rolePermissionService.findAllPermission();
        }

        map.put("pageId", 44);
        map.put("roleDTO", roleDTO);
        map.put("permissionList", permissionList);

        return new ModelAndView("pages/roleAdd", map);
    }


    /**
     * 角色展示页面
     */
    @GetMapping("/list")
    @RequiresPermissions("role:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<RoleDTO> pageDTO = rolePermissionService.findRoleDTO(pageRequest);
        map.put("pageId", 45);
        map.put("pageTitle", "角色列表");
        map.put("pageContent", pageDTO);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/role/list.html");
        return new ModelAndView("pages/roleList");
    }


    /**
     * 角色添加
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String, Object>> save(@RequestBody RoleForm roleForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        Role role = new Role();
        role.setId(roleForm.getId());
        role.setName(roleForm.getName());
        role.setDescription(roleForm.getDescription());
        role.setLevel(roleForm.getLevel());
        Role result = rolePermissionService.saveRole(role);
        rolePermissionService.save(result.getId(),roleForm.getIdList());
        return ResultVOUtil.success();
    }
}
