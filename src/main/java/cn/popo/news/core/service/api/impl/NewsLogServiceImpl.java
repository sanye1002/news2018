package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.utils.ToolUtil;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.NewsLogs;
import cn.popo.news.core.repository.NewsLogRepository;
import cn.popo.news.core.service.api.NewsLogService;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhaoxiang
 * @Date 2018/11/13
 * @Desc
 */
@Service
@Transactional
public class NewsLogServiceImpl implements NewsLogService {
    @Autowired
    private NewsLogRepository repository;
    @Override
    public void add(String userId, ArticleDetailsVO articleInfo, HttpServletRequest request) {
        NewsLogs newsLogs = new NewsLogs();
        newsLogs.setId(KeyUtil.genUniqueKey());
        newsLogs.setArticleId(articleInfo.getArticleId());
        newsLogs.setUserId(userId);
        newsLogs.setTitle(articleInfo.getTitle());
        newsLogs.setPreferDegree(0);
        newsLogs.setIp(ToolUtil.getClientIp(request));
        repository.save(newsLogs);
    }
}
