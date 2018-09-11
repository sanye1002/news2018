package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.UserVO;
import cn.popo.news.core.entity.common.ReportType;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.entity.param.CollectParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:52
 * @Desc
 */
public interface AgoArticleService {

    ArticleDetailsVO findArticleDetails(String article,String userId);

    UserVO findArticleDetailsUser(String articleId,String userId);

    Integer articleCollect(CollectParam collectParam);

    void deleteCollectByCollectId(Integer collectId);

    void articleReportInfoSave(ReprotInfoForm reprotInfoForm);

    void saveBrowsingHistory(String userId,String articleId);

    PageDTO<ArticleVO> findAllArticleByClassifyIdAndShowStateAndStateAndDraft(Pageable pageable,Integer classifyId ,Integer state, Integer showState, Integer draft);

    PageDTO<ArticleVO> findAllArticleByUserCollect(Pageable pageable,String userId,Integer typeId);

    PageDTO<ArticleVO> findAllArticleByTypeId(Pageable pageable,Integer typeId,Integer state, Integer showState,Integer draft,Integer manageId,String userId);

    List<ArticleVO> findAllArticleByKeywordsLike(Integer state,Integer draft,Integer showState,String content);

    List<ArticleVO> findSlide(Integer state, Integer draft, Integer showState, Integer manageId, Integer typeId);

    List<ArticleVO> findRecommentByTypeId(Integer state,Integer draft,Integer showState,Integer manageId,Integer typeId,Integer recommendState);

    PageDTO<ArticleVO> findAllArticleByStateAndShowStateAndDraftAndTimeAfter(Pageable pageable,Integer state,Integer showState,Integer draft,Integer classifyId,Long time);

    List<ReportType> findAllReportType();

    void keywordsArticle(String content,Integer article);

    void articlePraise(String userId,String articleId);

}
