package cn.popo.news.core.service;

import cn.popo.news.core.dto.AuthorAuditDTO;
import cn.popo.news.core.dto.PageDTO;
import org.springframework.data.domain.Pageable;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 3:59
 * @Description description
 */
public interface AuthorAuditService {
    void addAudit(String reason,String userId);
    PageDTO<AuthorAuditDTO> findAllByAuditState(Pageable pageable, Integer auditState);
    void updateAudit(Integer auditState,Integer id);
}
