package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Type_info;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author  Administrator
 * @Date    2018/5/24 10:53
 * @Desc
 */
public interface TypeRepository extends JpaRepository<Type_info,Integer> {
}
