package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.ArticleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:52
 * @Desc
 */
public interface ArticleRepository extends JpaRepository<ArticleInfo,String> {
    Page<ArticleInfo> findAllByStateAndTypeIdAndDraft(Pageable pageable,Integer state,Integer type,Integer draft);
    Page<ArticleInfo> findAllByStateAndTypeIdAndDraftAndClassifyId(Pageable pageable,Integer state,Integer type,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndTypeIdAndUidAndDraft(Pageable pageable,Integer state,Integer type,String uid,Integer draft);
    Page<ArticleInfo> findAllByStateAndTypeIdAndShowStateAndDraftAndClassifyId(Pageable pageable,Integer state,Integer type,Integer showState,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndTypeIdAndShowStateAndDraft(Pageable pageable,Integer state,Integer type,Integer showState,Integer draft);
    Page<ArticleInfo> findAllByStateAndDraft(Pageable pageable,Integer state,Integer draft);
    Page<ArticleInfo> findAllByStateAndDraftAndClassifyId(Pageable pageable,Integer state,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndUidAndDraft(Pageable pageable,Integer state,String uid,Integer draft);
    Page<ArticleInfo> findAllByStateAndShowStateAndDraft(Pageable pageable,Integer state, Integer showState,Integer draft);
    Page<ArticleInfo> findAllByStateAndShowStateAndDraftAndClassifyId(Pageable pageable,Integer state, Integer showState,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraft(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraftAndSlideState(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft,Integer slideState);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraftAndRecommendState(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft,Integer recommendState);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyId(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyIdAndSlideState(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft,Integer classifyId,Integer slideState);
    Page<ArticleInfo> findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyIdAndRecommendState(Pageable pageable,Integer state,Integer type,Integer manageId,Integer draft,Integer classifyId,Integer recommendState);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraft(Pageable pageable,Integer state,Integer manageId,Integer draft);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraftAndRecommendState(Pageable pageable,Integer state,Integer manageId,Integer draft,Integer recommendState);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraftAndSlideState(Pageable pageable,Integer state,Integer manageId,Integer draft,Integer slideState);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyId(Pageable pageable,Integer state,Integer manageId,Integer draft,Integer classifyId);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndSlideState(Pageable pageable,Integer state,Integer manageId,Integer draft,Integer classifyId,Integer slideState);
    Page<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndRecommendState(Pageable pageable,Integer state,Integer manageId,Integer draft,Integer classifyId,Integer recommendState);
    List<ArticleInfo> findAllByStateAndDraft(Integer state,Integer draft);
    List<ArticleInfo> findAllByStateAndDraftAndTypeId(Integer state,Integer draft,Integer typeId);
    List<ArticleInfo> findAllByStateAndDraftAndClassifyIdAndTypeId(Integer state,Integer draft,Integer classifyId,Integer typeId);
    List<ArticleInfo> findAllByStateAndDraftAndClassifyId(Integer state,Integer draft,Integer classifyId);
    List<ArticleInfo> findAllByStateAndUidAndDraft(Integer state,String uid,Integer draft);
    List<ArticleInfo> findAllByStateAndManageIdAndDraft(Integer state,Integer manageId,Integer draft);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndTypeId(Integer state,Integer manageId,Integer draft,Integer typeId);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndTypeIdAndSlideState(Integer state,Integer manageId,Integer draft,Integer typeId,Integer slideState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndTypeIdAndRecommendState(Integer state,Integer manageId,Integer draft,Integer typeId,Integer recommendState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndSlideState(Integer state,Integer manageId,Integer draft,Integer slideState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndRecommendState(Integer state,Integer manageId,Integer draft,Integer recommendState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyId(Integer state,Integer manageId,Integer draft,Integer classifyId);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeId(Integer state,Integer manageId,Integer draft,Integer classifyId,Integer typeId);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeIdAndSlideState(Integer state,Integer manageId,Integer draft,Integer classifyId,Integer typeId,Integer slideState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeIdAndRecommendState(Integer state,Integer manageId,Integer draft,Integer classifyId,Integer typeId,Integer recommendState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndSlideState(Integer state,Integer manageId,Integer draft,Integer classifyId,Integer slideState);
    List<ArticleInfo> findAllByStateAndManageIdAndDraftAndClassifyIdAndRecommendState(Integer state,Integer manageId,Integer draft,Integer classifyId,Integer recommendState);
    List<ArticleInfo> findAllByStateAndShowStateAndDraft(Integer state,Integer showState,Integer draft);
    List<ArticleInfo> findAllByStateAndShowStateAndDraftOrderByAuditTimeDesc(Integer state,Integer showState,Integer draft);
    List<ArticleInfo> findAllByStateAndShowStateAndDraftAndClassifyId(Integer state,Integer showState,Integer draft,Integer classifyId);
    List<ArticleInfo> findAllByStateAndShowStateAndDraftAndTypeId(Integer state,Integer showState,Integer draft,Integer typeId);
    List<ArticleInfo> findAllByStateAndShowStateAndDraftAndTypeIdAndClassifyId(Integer state,Integer showState,Integer draft,Integer typeId,Integer classifyId);
    List<ArticleInfo> findAllByArticleIdAndDraft(String articleId,Integer draft);
    List<ArticleInfo> findAllByStateAndTitleLikeAndDraft(Integer state,String content,Integer draft);
    List<ArticleInfo> findAllByStateAndKeywordsLikeAndDraft(Integer state,String content,Integer draft);
    List<ArticleInfo> findAllByClassifyIdAndStateAndDraft(Integer classifyId,Integer state,Integer draft);
    Page<ArticleInfo> findAllByStateAndTypeIdAndTitleLikeAndDraft(Pageable pageable,Integer state,Integer typeId,String content,Integer draft);
    Page<ArticleInfo> findAllByStateAndTitleLikeAndDraft(Pageable pageable,Integer state,String content,Integer draft);
    Page<ArticleInfo> findAllByStateAndTypeIdAndKeywordsLikeAndDraft(Pageable pageable,Integer state,Integer typeId,String content,Integer draft);
    Page<ArticleInfo> findAllByStateAndKeywordsLikeAndDraft(Pageable pageable,Integer state,String content,Integer draft);
    Page<ArticleInfo> findAllByClassifyIdAndStateAndTypeIdAndDraft(Pageable pageable,Integer classifyId,Integer state,Integer typeId,Integer draft);
    Page<ArticleInfo> findAllByClassifyIdAndStateAndDraft(Pageable pageable,Integer classifyId,Integer state,Integer draft);
    Page<ArticleInfo> findAllByUidAndDraftAndTypeId(Pageable pageable,String userId, Integer draft,Integer typeId);
    Page<ArticleInfo> findAllByUidAndDraft(Pageable pageable,String userId, Integer draft);

    //api
    Page<ArticleInfo> findAllByStateAndTitleLikeAndShowStateAndDraft(Pageable pageable,Integer state,String content,Integer showState,Integer draft);
    //Page<ArticleInfo> findAllByUidAndStateAndShowStateAndDraft(Pageable pageable,String userId,Integer state, Integer showState,Integer draft);
    Page<ArticleInfo> findAllByClassifyIdAndStateAndShowStateAndDraft(Pageable pageable,Integer classify,Integer state, Integer showState,Integer draft);

    List<ArticleInfo> findAllByStateAndDraftAndShowStateAndManageIdAndTypeIdAndRecommendState(
            Integer state,Integer draft,Integer showState,Integer manageId,Integer typeId,Integer recommendState);

    Page<ArticleInfo> findAllByStateAndDraftAndShowStateAndManageIdAndSlideState(
            Pageable pageable,Integer state,Integer draft,Integer showState,Integer manageId,Integer slideState);

    List<ArticleInfo> findAllByManageIdAndSlideState(Integer manageId,Integer slideState);

    Page<ArticleInfo> findAllByStateAndShowStateAndDraftAndUidAndTypeId(Pageable pageable,Integer state,Integer showState, Integer draft, String uid,Integer typeId);

    Page<ArticleInfo> findAllByStateAndShowStateAndDraftAndKeywordsLike(Pageable pageable,Integer state,Integer showState,Integer draft,String content);

    List<ArticleInfo> findAllByStateAndShowStateAndDraftAndKeywordsLikeOrderByCrateTimeDesc(Integer state,Integer showState,Integer draft,String content);

    List<ArticleInfo> findAllByClassifyId(Integer classifyId);

    Page<ArticleInfo> findAllByStateAndShowStateAndDraftAndClassifyIdAndCrateTimeAfter(Pageable pageable,Integer state,Integer showState,Integer draft,Integer classifyId,Long time);

    Page<ArticleInfo> findAllByStateAndShowStateAndDraftAndCrateTimeAfter(Pageable pageable,Integer state,Integer showState,Integer draft,Long time);


}
