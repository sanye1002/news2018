package cn.popo.news.core.service;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.OutLink;
import cn.popo.news.core.entity.param.OutLinkParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-13 下午 4:37
 * @Description description
 */
public interface OutLinkService {
    void addLink(OutLinkParam outLinkParam);
    Page<OutLink> findAllOutLink(Pageable pageable);
    OutLink findOneLine(Integer id);
    void delete(Integer id);
}
