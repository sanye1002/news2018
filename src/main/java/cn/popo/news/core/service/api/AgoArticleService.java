package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.entity.param.CollectParam;
import org.springframework.data.domain.Pageable;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:52
 * @Desc
 */
public interface AgoArticleService {

    ArticleDetailsVO findArticleDetails(String article,String userId);

    void articleCollect(CollectParam collectParam);

    void deleteCollectByCollectId(Integer collectId);

    void articleReportInfoSave(ReprotInfoForm reprotInfoForm);

    PageDTO<ArticleVO> findAllArticleByClassifyIdAndShowStateAndStateAndDraft(Pageable pageable,Integer classifyId ,Integer state, Integer showState, Integer draft);

    PageDTO<ArticleVO> findAllArticleByUserCollect(Pageable pageable,String userId,Integer typeId);
}
