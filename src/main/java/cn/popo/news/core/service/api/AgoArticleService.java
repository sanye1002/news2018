package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.entity.form.ReprotInfoForm;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:52
 * @Desc
 */
public interface AgoArticleService {

    ArticleDetailsVO findArticleDetails(String article);
    void articleCollect(String articleId,String userId);
    void deleteCollectByCollectId(Integer collectId);
    void articleReportInfoSave(ReprotInfoForm reprotInfoForm);
}
