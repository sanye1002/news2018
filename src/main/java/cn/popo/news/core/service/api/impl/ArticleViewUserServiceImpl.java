package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.utils.ToolUtil;
import cn.popo.news.core.entity.common.ArticleViewUser;
import cn.popo.news.core.repository.ArticleViewUserRepository;
import cn.popo.news.core.service.api.ArticleViewUserService;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2018/11/09
 * @Desc
 */
@Service
public class ArticleViewUserServiceImpl implements ArticleViewUserService {
    @Autowired
    private ArticleViewUserRepository repository;

    @Override
    public void addView(HttpServletRequest request, String userId, String articleId, String title) {
        ArticleViewUser articleViewUser = new ArticleViewUser();
        articleViewUser.setId(KeyUtil.genUniqueKey());
        articleViewUser.setArticleId(articleId);
        articleViewUser.setUserId(userId);
        articleViewUser.setViewTime(new Date());
        articleViewUser.setIp(ToolUtil.getClientIp(request));
        articleViewUser.setTitle(title);
        repository.save(articleViewUser);
    }
}
