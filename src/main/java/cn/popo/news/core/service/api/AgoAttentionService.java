package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.AttentionVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgoAttentionService {
    void addAttention(String aid,String fid);
    void deleteAttention(Integer attentionId);
    Integer findAttention(String aid, String fid);
    Integer findAttentionNum(String aid);
    Integer findFansNum(String fid);
    PageDTO<AttentionVO> findAllAttention(Pageable pageable, String aid);
    PageDTO<AttentionVO> findAllFans(Pageable pageable,String fid);
}
