package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.AuthorAuditDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.AuthorAudit;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.repository.AuthorAuditRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.AuthorAuditService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 4:00
 * @Description description
 */
@Service
@Transactional
public class AuthorAuditServiceImpl implements AuthorAuditService{

    @Autowired
    private AuthorAuditRepository authorAuditRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * 添加用户申请
     * @param reason
     * @param userId
     */
    @Override
    public void addAudit(String reason,String userId) {
        AuthorAudit authorAudit = new AuthorAudit();
        Long time = System.currentTimeMillis();
        authorAudit.setAuditState(2);
        authorAudit.setTime(time);
        authorAudit.setUserId(userId);
        authorAudit.setReason(reason);
        authorAuditRepository.save(authorAudit);
    }

    /**
     * 申请列表
     * @param auditState
     * @return
     */
    @Override
    public PageDTO<AuthorAuditDTO> findAllByAuditState(Pageable pageable,Integer auditState) {
        PageDTO<AuthorAuditDTO> pageDTO = new PageDTO<>();
        Page<AuthorAudit> authorAuditPage = authorAuditRepository.findAllByAuditState(pageable,auditState);
        List<AuthorAuditDTO> list = new ArrayList<>();
        if (!authorAuditPage.getContent().isEmpty()) {
            authorAuditPage.getContent().forEach(l->{
                AuthorAuditDTO authorAuditDTO = new AuthorAuditDTO();
                BeanUtils.copyProperties(l,authorAuditDTO);
                User user = userRepository.findOne(l.getUserId());
                authorAuditDTO.setName(user.getName());
                authorAuditDTO.setNikeName(user.getNikeName());
                list.add(authorAuditDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 审核
     * @param auditState
     */
    @Override
    public void updateAudit(Integer auditState,Integer id) {
        AuthorAudit authorAudit = authorAuditRepository.findOne(id);
        if(auditState == 0){
            authorAudit.setAuditState(0);
        }else {
            authorAudit.setAuditState(1);
            User user = userRepository.findOne(authorAudit.getUserId());
            user.setRoleId(4);
            user.setUserType("0");
        }
    }
}
