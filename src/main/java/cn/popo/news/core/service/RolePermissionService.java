package cn.popo.news.core.service;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.RoleDTO;
import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.entity.common.Role;
import cn.popo.news.core.entity.common.RolePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 上午 11:23
 */
public interface RolePermissionService {
    RoleDTO findOne(Integer id);

    /**
     * @return
     */
    PageDTO<RoleDTO> findRoleDTO(Pageable pageable);

    /**
     * @return
     */
    Page<Permission> findPermission(Pageable pageable);

    /**
     * @param id
     * @return
     */
    Map<String, Object> deleteRolePermission(Integer id);

    /**
     * @param id
     * @return
     */
    Map<String, Object> deleteRole(Integer id);

    /**
     * @param id
     * @return
     */
    Map<String, Object> deletePermission(Integer id);

    /**
     * @param role
     * @return
     */
    Role saveRole(Role role);

    /**
     * @param permission
     * @return
     */
    Permission savePermission(Permission permission);

    /**
     *
     * @param id
     * @return
     */
    Permission findPermissionById(Integer id);

    /**
     *
     * @return
     */
    List<Permission> findAllPermission();

    /**
     *
     * @param id
     * @return
     */
    Role findRoleById(Integer id);

    /**
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    RolePermission save(Integer roleId, List<Integer> permissionId);

    /**
     *
     * @return
     */
    List<Role> findAllRole();

    List<Role> findAllByLevel(Integer level);
}
