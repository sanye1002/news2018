package cn.popo.news.core.service;

import cn.popo.news.core.dto.ArticleDTO;
import cn.popo.news.core.dto.ArticleReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.form.ArticleDraftForm;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/22 16:38
 * @Desc
 */
public interface ArticleService {
    PageDTO<ArticleDTO> findAllArticleDTOByStateAndType(Pageable pageable, Integer state, Integer type);

    PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndUid(Pageable pageable, Integer state, Integer type, String uid);

    PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndSid(Pageable pageable, Integer state, Integer type,Integer manageId);

    PageDTO<ArticleDTO> findAllByShowAndStateAndType(Pageable pageable,Integer showState,Integer state, Integer type);

    PageDTO<ArticleDTO> findAllByTitleOrkeywordsOrClassifyLikeAndStateAndType(Pageable pageable,String line,String content,Integer type,Integer state);

    PageDTO<ArticleReportDTO> findAllReportByDisposeState(Pageable pageable,Integer disposeState);

    PageDTO<ArticleDTO> findAllByUserIdAndDraftAndTypeId(Pageable pageable,String userId,Integer draft,Integer typeId);

    Integer findStateNum(Integer state);

    Integer findStateAndUidNum(Integer state,String uid);

    Integer findStateAndTitleOrKeywordsOrClassifyLikeNum(Integer state,String line,String content);

    Integer findDisposeStateNum(Integer disposeState);

    Integer findStateAndSidNum(Integer state,Integer sid);

    Integer findStateAndShowNum(Integer state,Integer showState);

    Map<String,Object> findOneArticleSomeImg(String id);

    void articleSave(ArticleForm articleForm);

    void articleReportInfoSave(ReprotInfoForm reprotInfoForm);

    void updateArticleReportDisposeState(String id, Integer disposeState, String articleId, Integer dispose);

    void updateArticleStateByArticleId(String articleId,Integer state);

    void updateArticleSpecialByArticleId(String articleId,Integer sid);

    void updateArticleManage(String articleId,Integer manageId);

    void updateArticleShow(String articleId,Integer showState);

    void deleteArticleByArticleId(String articleId);

    void deleteArticleReportByArticleId(String articleId);

    void saveArticleDraft(ArticleDraftForm articleDraftForm);

    ArticleInfo findOneByArticleId(String articleId);

    Map<String,Object> issueContent(String articleId);
}
