package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 上午 10:53
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer> {

    List<RolePermission> findAllByRoleId(Integer roleId);

    List<RolePermission> findAllByPermissionId(Integer roleId);

}
