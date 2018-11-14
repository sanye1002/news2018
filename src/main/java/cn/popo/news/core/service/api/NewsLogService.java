package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.api.ArticleDetailsVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhaoxiang
 * @Date 2018/11/13
 * @Desc
 */
public interface NewsLogService {
    void add(String userId, ArticleDetailsVO articleInfo, HttpServletRequest request);
}
