package cn.popo.news.core.service;

import cn.popo.news.core.dto.ArticleDTO;
import cn.popo.news.core.dto.ArticleReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.form.ArticleDraftForm;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.utils.ArticleExcelUtil;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/22 16:38
 * @Desc
 */
public interface ArticleService {
    PageDTO<ArticleDTO> findAllArticleDTOByStateAndType(Pageable pageable, Integer state, Integer typeId,Integer classifyId);

    PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndUid(Pageable pageable, Integer state, Integer typeId, String uid);

    PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndSid(Pageable pageable, Integer state, Integer typeId,Integer manageId,Integer classifyId,Integer position);

    PageDTO<ArticleDTO> findAllByShowAndStateAndType(Pageable pageable,Integer showState,Integer state, Integer typeId,Integer classifyId);

    PageDTO<ArticleDTO> findAllByTitleOrkeywordsOrClassifyLikeAndStateAndType(Pageable pageable,String line,String content,Integer typeId,Integer state);

    PageDTO<ArticleReportDTO> findAllReportByDisposeState(Pageable pageable,Integer disposeState);

    PageDTO<ArticleDTO> findAllByUserIdAndDraftAndTypeId(Pageable pageable,String userId,Integer draft,Integer typeId);

    Integer findStateNum(Integer state,Integer classifyId,Integer typeId);

    Integer findStateNum(Integer state);

    Integer findStateAndUidNum(Integer state,String uid);

    Integer findStateAndTitleOrKeywordsOrClassifyLikeNum(Integer state,String line,String content);

    Integer findDisposeStateNum(Integer disposeState);

    Integer findStateAndSidNum(Integer state,Integer sid,Integer classifyId,Integer typeId,Integer position);

    Integer findStateAndShowNum(Integer state,Integer showState,Integer typeId,Integer classifyId);

    Map<String,Object> findOneArticleSomeImg(String id);

    void articleSave(ArticleForm articleForm);

    void updateArticleReportDisposeState(String id, Integer disposeState, String articleId, Integer dispose);

    void updateArticleStateByArticleId(String articleId,Integer state,Integer integral);

    void updateArticleSpecialByArticleId(String articleId,Integer typeId);

    void updateArticleManage(String articleId,Integer manageId);

    void updateArticleShow(String articleId,Integer showState);

    void deleteArticleByArticleId(String articleId);

    void deleteArticleReportByArticleId(String articleId);

    void saveArticleDraft(ArticleDraftForm articleDraftForm);

    ArticleInfo findOneByArticleId(String articleId);

    Map<String,Object> issueContent(String articleId);

    PageDTO<ArticleVO> findArticleTitleLikeAndStateAndShowStateAndDraft(Pageable pageable,Integer state, String content, Integer showState, Integer draft);

    PageDTO<ArticleVO> findAllArticleByShowStateAndStateAndDraft(Pageable pageable, Integer state, Integer showState, Integer draft);

    void updateSlide(String articleId);

    Integer findAllSlideNum(Integer manageId,Integer slideState);

    Boolean findArticleByClassifyId(Integer typeId);

    void addDefaultAuditTime();

    void addDefaultLookNum();

    boolean findTitleIsSame(String title);

    List<ArticleInfo> findAllByStateAndShowStateAndDraft(Integer state,Integer showState,Integer draft);

    String findUidByArticleId(String articleId);

    void addInfo();


}
