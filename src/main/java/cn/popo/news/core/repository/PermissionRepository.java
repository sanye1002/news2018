package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 上午 10:54
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

}
