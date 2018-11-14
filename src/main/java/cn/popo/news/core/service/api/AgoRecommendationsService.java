package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author zhaoxiang
 * @Date 2018/11/13
 * @Desc
 */
public interface AgoRecommendationsService {

    /**
     * 查找今天推荐文章
     * @param pageable
     * @param userId 用户ID
     * @param day 那天
     * @return
     */
    PageDTO<ArticleVO> findTodayRecommendArticle(Pageable pageable, String userId, Integer day);
}
