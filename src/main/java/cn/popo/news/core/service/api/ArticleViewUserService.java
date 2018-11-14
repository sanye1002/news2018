package cn.popo.news.core.service.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhaoxiang
 * @Date 2018/11/09
 * @Desc
 */
public interface ArticleViewUserService {

    void addView(HttpServletRequest request,String userId,String articleId,String title);
}
