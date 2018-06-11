package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.PrivateLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface PrivateLetterRepository extends JpaRepository<PrivateLetter,String> {
    Page<PrivateLetter> findAllByUidAndUserId(Pageable pageable,String uid, String userId);

}
