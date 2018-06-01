package cn.popo.news.core.repository;


import cn.popo.news.core.dto.api.AttentionVO;
import cn.popo.news.core.entity.common.Attention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface AttentionRepository extends JpaRepository<Attention,Integer> {
    Attention findAllByAidAndFid(String aid, String fid);
    List<Attention> findAllByAid(String aid);
    List<Attention> findAllByFid(String fid);
    Page<Attention> findAllByAid(Pageable pageable, String aid);
    Page<Attention> findAllByFid(Pageable pageable,String fid);
}
