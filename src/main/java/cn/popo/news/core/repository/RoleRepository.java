package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 上午 10:52
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {

    List<Role> findAllByLevel(Integer level);
}
