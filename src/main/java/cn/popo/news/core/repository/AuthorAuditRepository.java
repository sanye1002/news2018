package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.AuthorAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface AuthorAuditRepository extends JpaRepository<AuthorAudit,Integer> {
    Page<AuthorAudit> findAllByAuditState(Pageable pageable,Integer AuditState);
    AuthorAudit findAllByUserId(String userId);
}
