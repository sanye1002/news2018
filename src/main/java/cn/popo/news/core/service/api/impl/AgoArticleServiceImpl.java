package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.common.*;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.entity.param.CollectParam;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import cn.popo.news.core.utils.SplitUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author  Administrator
 * @Date    2018/5/31 18:21
 * @Desc    前台文章逻辑控制中心
 */


@Transactional
@Service
public class AgoArticleServiceImpl implements AgoArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ClassifyRepository classifyRepository;


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private BrowsingHistoryRepository browsingHistoryRepository;


    /**
     * 通过文章id查找文章详情
     */
    @Override
    public ArticleDetailsVO findArticleDetails(String articleId,String userId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        ArticleDetailsVO articleDetailsVO = new ArticleDetailsVO();
        if(articleInfo!=null){
            BeanUtils.copyProperties(articleInfo,articleDetailsVO);
            articleDetailsVO.setKeywordList(SplitUtil.splitComme(articleInfo.getKeywords()));
            articleDetailsVO.setTime(GetTimeUtil.getDateFormat(articleInfo.getCrateTime()));
            if(articleInfo.getTypeId()==2){
                articleDetailsVO.setImgDesc(SplitUtil.splitComme(articleInfo.getContent()));
            }
            if(articleInfo.getImgUrl()!=null){
                articleDetailsVO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
            }
            articleDetailsVO.setImgNum(SplitUtil.splitComme(articleInfo.getImgUrl()).size());
            Collect collect = collectRepository.findAllByUidAndAid(articleInfo.getUid(),articleId);
            if(collect!=null){
                articleDetailsVO.setCollectId(collect.getId());
            }else {
                articleDetailsVO.setCollectId(0);
            }

        }

        return articleDetailsVO;
    }


    /**
     * 文章详情页面用户信息及推荐文章
     */
    @Override
    public UserVO findArticleDetailsUser(String articleId,String userId){
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        UserVO userVO = new UserVO();
        List<UserReCommentVO> userReCommentVOArrayList = new ArrayList<UserReCommentVO>();

        User user = userRepository.findOne(articleInfo.getUid());
        BeanUtils.copyProperties(user,userVO);
        Attention attention = attentionRepository.findAllByAidAndFid(userId,articleInfo.getUid());
        if (attention!=null){
            userVO.setAttentionId(attention.getId());
        }else {
            userVO.setAttentionId(0);
        }

        List<ArticleInfo> list = articleRepository.findAllByStateAndShowStateAndDraftAndUidAndTypeId(
                ResultEnum.PARAM_NULL.getCode(),ResultEnum.PARAM_NULL.getCode(),
                ResultEnum.SUCCESS.getCode(),articleInfo.getUid(),articleInfo.getTypeId());

        list.forEach(l->{
            UserReCommentVO userReCommentVO = new UserReCommentVO();
            userReCommentVO.setArticleId(l.getArticleId());
            userReCommentVO.setTitle(l.getTitle());
            String imgList = l.getImgUrl();
            if(imgList!=null){
                userReCommentVO.setImgList(SplitUtil.splitComme(imgList));
            }
            userReCommentVO.setImgNum(SplitUtil.splitComme(imgList).size());
            userReCommentVOArrayList.add(userReCommentVO);
        });

        userVO.setUserReCommentList(userReCommentVOArrayList);
        return userVO;
    }


    /**
     * 文章收藏（添加）
     */
    @Override
    public void articleCollect(CollectParam collectParam) {
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectParam,collect);
        collect.setTime(System.currentTimeMillis());

        //人气+10
        ArticleInfo articleInfo = articleRepository.findOne(collectParam.getAid());
        Integer lookNum = articleInfo.getLookNum();
        articleInfo.setLookNum(lookNum+10);


        collectRepository.save(collect);
    }


    /**
     * 文章取消收藏
     */
    @Override
    public void deleteCollectByCollectId(Integer collectId) {
        collectRepository.delete(collectId);
    }


    /**
     * 文章举报上传
     */
    @Override
    public void articleReportInfoSave(ReprotInfoForm reprotInfoForm) {
        ArticleReport articleReport = new ArticleReport();
        reprotInfoForm.setInfo(KeyWordFilter.doFilter(reprotInfoForm.getInfo()));
        BeanUtils.copyProperties(reprotInfoForm,articleReport);
        Long l = System.currentTimeMillis();
        articleReport.setTime(l);
        articleReport.setId(KeyUtil.genUniqueKey());
        articleReport.setDisposeState(ResultEnum.SUCCESS.getCode());
        reportRepository.save(articleReport);
    }

    /**
     * 通过文章分类查询
     */
    @Override
    public PageDTO<ArticleVO> findAllArticleByClassifyIdAndShowStateAndStateAndDraft(
            Pageable pageable, Integer classifyId, Integer state, Integer showState, Integer draft) {

        PageDTO<ArticleVO> pageDTO = new PageDTO<>();

        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByClassifyIdAndStateAndShowStateAndDraft(
                pageable,classifyId,state,showState,draft);

        List<ArticleVO> list = new ArrayList<>();
        Long time = System.currentTimeMillis();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleVO indexVO = new ArticleVO();
                    BeanUtils.copyProperties(l,indexVO);
                    indexVO.setArticleId(l.getArticleId());
                    indexVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    indexVO.setCommentNum(commentRepository.findAllByAid(l.getArticleId()).size());
                    if(l.getImgUrl()!=null){
                        indexVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getCrateTime()));
                    User user = userRepository.findOne(l.getUid());
                    Author author = new Author();
                    author.setAvatar(user.getAvatar());
                    author.setName(user.getNikeName());
                    indexVO.setAuthor(author);
                    list.add(indexVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 通过用户查询
     */
    @Override
    public PageDTO<ArticleVO> findAllArticleByUserCollect(Pageable pageable, String userId,Integer typeId) {

        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        Page<Collect> articleInfoPage = collectRepository.findAllByUidAndTypeId(pageable,userId,typeId);
        List<ArticleVO> list = new ArrayList<>();
        Long time = System.currentTimeMillis();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleInfo articleInfo = articleRepository.findOne(l.getAid());
                    ArticleVO indexVO = new ArticleVO();
                    BeanUtils.copyProperties(articleInfo,indexVO);
                    indexVO.setArticleId(articleInfo.getArticleId());
                    indexVO.setClassify(classifyRepository.findOne(articleInfo.getClassifyId()).getClassify());
                    indexVO.setCommentNum(commentRepository.findAllByAid(articleInfo.getArticleId()).size());
                    if(articleInfo.getImgUrl()!=null){
                        indexVO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
                    }
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,articleInfo.getCrateTime()));
                    indexVO.setImgNum(SplitUtil.splitComme(articleInfo.getImgUrl()).size());
                    User user = userRepository.findOne(l.getUid());
                    Author author = new Author();
                    author.setAvatar(user.getAvatar());
                    author.setName(user.getNikeName());
                    indexVO.setAuthor(author);
                    list.add(indexVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 模糊查询 （keywords）
     */
    @Override
    public PageDTO<ArticleVO> findAllArticleByKeywordsLike(Pageable pageable, Integer state, Integer draft, Integer showState, String content) {
        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        content="%"+content+"%";
        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraftAndKeywordsLike(pageable,state,showState,draft,content);
        Long time = System.currentTimeMillis();
        List<ArticleVO> list = new ArrayList<>();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleVO indexVO = new ArticleVO();
                    BeanUtils.copyProperties(l,indexVO);
                    indexVO.setArticleId(l.getArticleId());
                    indexVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    indexVO.setCommentNum(commentRepository.findAllByAid(l.getArticleId()).size());
                    if(l.getImgUrl()!=null){
                        indexVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getCrateTime()));
                    User user = userRepository.findOne(l.getUid());
                    Author author = new Author();
                    author.setAvatar(user.getAvatar());
                    author.setName(user.getNikeName());
                    indexVO.setAuthor(author);
                    list.add(indexVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }


    /**
     * 查询轮播图
     */
    @Override
    public List<ArticleVO> findSlide(Integer state, Integer draft, Integer showState, Integer manageId,Integer slideState) {
        List<ArticleInfo> articleInfoList = articleRepository.findAllByStateAndDraftAndShowStateAndManageIdAndSlideState(
                state,draft,showState,manageId,slideState);
        List<ArticleVO> articleVOList = new ArrayList<ArticleVO>();
        articleInfoList.forEach(l->{
            ArticleVO indexVO = new ArticleVO();
            BeanUtils.copyProperties(l,indexVO);
            indexVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
            if(l.getImgUrl()!=null){
                indexVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
            }
            articleVOList.add(indexVO);
        });

        return articleVOList;
    }

    /**
     * 查侧边栏
     */
    @Override
    public List<ArticleVO> findRecommentByTypeId(Integer state, Integer draft, Integer showState, Integer manageId, Integer typeId, Integer recommendState) {
        List<ArticleInfo> articleInfoList = articleRepository.findAllByStateAndDraftAndShowStateAndManageIdAndTypeIdAndRecommendState(
                state,draft,showState,manageId,typeId,recommendState);
        List<ArticleVO> articleVOList = new ArrayList<ArticleVO>();
        articleInfoList.forEach(l->{
            ArticleVO indexVO = new ArticleVO();
            BeanUtils.copyProperties(l,indexVO);
            indexVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
            if(l.getImgUrl()!=null){
                indexVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                indexVO.setImgNum(SplitUtil.splitComme(l.getImgUrl()).size());
            }
            articleVOList.add(indexVO);
        });

        return articleVOList;
    }


    /**
     * 增加浏览记录
     */
    @Override
    public void saveBrowsingHistory(String userId,String articleId) {
        System.out.println(userId);
        BrowsingHistory browsingHistory = null;
        if(userId.equals("")){
             browsingHistory = new BrowsingHistory();
        }else {
             browsingHistory = browsingHistoryRepository.findAllByUserIdAndArticleId(userId,articleId);
        }

        if(browsingHistory==null){
            browsingHistory.setTime(System.currentTimeMillis());
            browsingHistory.setArticleId(articleId);
            browsingHistory.setUserId(userId);
            browsingHistoryRepository.save(browsingHistory);

            //人气+1
            ArticleInfo articleInfo = articleRepository.findOne(articleId);
            Integer lookNum = articleInfo.getLookNum();
            articleInfo.setLookNum(lookNum+1);
        }

    }

}
