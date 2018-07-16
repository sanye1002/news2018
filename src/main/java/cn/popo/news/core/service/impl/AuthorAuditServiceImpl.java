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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 4:00
 * @Description description
 */
@Service
@Transactional
public class AuthorAuditServiceImpl implements AuthorAuditService {

    @Autowired
    private AuthorAuditRepository authorAuditRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * 添加用户申请
     *
     * @param reason
     * @param userId
     */
    @Override
    public void addAudit(String reason, String userId) {
        AuthorAudit authorAudit1 = authorAuditRepository.findAllByUserId(userId);
        Long time = System.currentTimeMillis();
        if (authorAudit1!=null) {
            authorAudit1.setReason(reason);
            authorAudit1.setTime(time);
            authorAudit1.setAuditState(1);
        }else {
            AuthorAudit authorAudit = new AuthorAudit();
            authorAudit.setAuditState(1);
            authorAudit.setTime(time);
            authorAudit.setUserId(userId);
            authorAudit.setReason(reason);
            authorAuditRepository.save(authorAudit);
        }

    }

    /**
     * 申请列表
     *
     * @param auditState
     * @return
     */
    @Override
    public PageDTO<AuthorAuditDTO> findAllByAuditState(Pageable pageable, Integer auditState) {
        PageDTO<AuthorAuditDTO> pageDTO = new PageDTO<>();
        Page<AuthorAudit> authorAuditPage = authorAuditRepository.findAllByAuditState(pageable, auditState);
        List<AuthorAuditDTO> list = new ArrayList<>();
        if (!authorAuditPage.getContent().isEmpty()) {
            authorAuditPage.getContent().forEach(l -> {
                AuthorAuditDTO authorAuditDTO = new AuthorAuditDTO();
                BeanUtils.copyProperties(l, authorAuditDTO);
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
     *
     * @param auditState
     */
    @Override
    public void updateAudit(Integer auditState, Integer id) {
        AuthorAudit authorAudit = authorAuditRepository.findOne(id);
        authorAudit.setAuditState(auditState);
        authorAudit.setAuditTime(System.currentTimeMillis());
        User user = userRepository.findOne(authorAudit.getUserId());
        user.setRoleId(4);
        user.setUserType("0");
    }

    /**
     * 审核状态
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findAuditStateById(String userId) {
        Map<String, Object> map = new HashMap<>();
        AuthorAudit authorAudit = authorAuditRepository.findAllByUserId(userId);
        if (authorAudit != null) {
            map.put("auditState", authorAudit.getAuditState());
        } else {
            map.put("auditState", 0);//未申请
        }
        User user = userRepository.findOne(userId);
        map.put("userType", user.getUserType());
        return map;
    }
}
