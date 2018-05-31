package cn.popo.news.core.controller.oa;



import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.entity.form.PermissionForm;
import cn.popo.news.core.service.RolePermissionService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 1:36
 */
@Controller
@RequestMapping("/oa/permission")
@RequiresAuthentication
public class PermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("permission:add")
    public ModelAndView permissionIndex(Map<String, Object> map,
                                        @RequestParam(value = "id", defaultValue = "") Integer id) {
        Permission permission = new Permission();
        map.put("pageId", 22);
        if (id != null) {
            map.put("pageTitle", "权限编辑");
            permission = rolePermissionService.findPermissionById(id);
        } else {
            map.put("pageTitle", "权限添加");
        }
        map.put("permission", permission);
        return new ModelAndView("pages/permissionAdd", map);
    }

    /**
     * @param permissionForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String, Object>> save(@Valid PermissionForm permissionForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        Permission permission = new Permission();
        if (permissionForm.getId() != null) {
            permission = rolePermissionService.findPermissionById(permissionForm.getId());
        }
        BeanUtils.copyProperties(permissionForm, permission);
        rolePermissionService.savePermission(permission);
        return ResultVOUtil.success();

    }

    @RequiresAuthentication
    @GetMapping("/list")
    @RequiresPermissions("permission:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<Permission> permissionPage = rolePermissionService.findPermission(pageRequest);
        map.put("pageId", 23);
        map.put("pageTitle", "操作权限列表");
        map.put("pageContent", permissionPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/permission/list.html");
        return new ModelAndView("pages/permissionList", map);
    }

    @ResponseBody
    @PostMapping("/delete")
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id) {

        return ResultVOUtil.success(rolePermissionService.deletePermission(id));
    }

}
