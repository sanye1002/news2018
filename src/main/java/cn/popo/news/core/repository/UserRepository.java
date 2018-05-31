package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface UserRepository extends JpaRepository<User,String> {
    Page<User> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable, String userType, Integer status, Integer showStatus);

    User findByPhoneAndStatus(String phone,Integer status);

    User findByPhoneAndPassword(String phone,String password);

    List<User> findAllByPhone(String phone);
}
