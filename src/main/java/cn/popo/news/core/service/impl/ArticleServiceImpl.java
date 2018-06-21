package cn.popo.news.core.service.impl;

import cn.popo.news.common.constant.UrlConstant;
import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.common.utils.WordParticipleUtil;
import cn.popo.news.core.dto.ArticleDTO;
import cn.popo.news.core.dto.ArticleReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.Author;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.ArticleReport;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.ArticleDraftForm;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.UserRewardService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import cn.popo.news.core.utils.ShiroGetSession;
import cn.popo.news.core.utils.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @Author  Administrator
 * @Date    2018/5/23 15:49
 * @Desc
 */
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ClassifyRepository classifyRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRewardService userRewardService;


    /**
     * 文章上传
     */
    public void articleSave(ArticleForm articleForm){
        ArticleInfo articleInfo = new ArticleInfo();
        articleForm.setContent(KeyWordFilter.doFilter(articleForm.getContent()));
        articleForm.setKeywords(KeyWordFilter.doFilter(articleForm.getKeywords()));
        articleForm.setTitle(KeyWordFilter.doFilter(articleForm.getTitle()));
        if(articleForm.getDes()!=null){
            articleForm.setDes(KeyWordFilter.doFilter(articleForm.getDes()));
        }
        BeanUtils.copyProperties(articleForm,articleInfo);
        if(articleForm.getDraft() == 1){
            articleInfo.setDraft(0);
        }else{
            articleInfo.setArticleId(KeyUtil.genUniqueKey());
        }
        articleInfo.setState(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        articleInfo.setShowState(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setManageId(ResultEnum.SUCCESS.getCode());
        articleInfo.setUid(ShiroGetSession.getUser().getUserId());
        articleInfo.setCrateTime(System.currentTimeMillis());
        articleInfo.setRecommendState(0);
        articleInfo.setSlideState(0);
        articleInfo.setLookNum(0);
        articleRepository.save(articleInfo);
    }


    /**
     * 后台文章审核页面
     */
    @Override
    public PageDTO<ArticleDTO> findAllArticleDTOByStateAndType(Pageable pageable, Integer state, Integer type,Integer classifyId) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if(classifyId == 0){
            if(type == 0){
                articleInfoPage = articleRepository.findAllByStateAndDraft(pageable,state,draft);
            }else {
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndDraft(pageable, state, type,draft);
            }
        }else {
            if(type == 0){
                articleInfoPage = articleRepository.findAllByStateAndDraftAndClassifyId(pageable,state,draft,classifyId);
            }else {
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndDraftAndClassifyId(pageable, state, type,draft,classifyId);
            }
        }


        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setContent("");
                        if (l.getTypeId()==3){
                            articleDTO.setContent(UrlConstant.URL+l.getContent());
                        }
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getCrateTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    articleDTO.setUser(userRepository.getOne(l.getUid()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 用户文章查询页面
     */
    @Override
    public PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndUid(Pageable pageable, Integer state, Integer type,String uid) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if(type == 0){
            articleInfoPage = articleRepository.findAllByStateAndUidAndDraft(pageable,state,uid,draft);
        }else {
            articleInfoPage = articleRepository.findAllByStateAndTypeIdAndUidAndDraft(pageable, state, type,uid,draft);
        }

        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setContent("");
                    if (l.getTypeId()==3){
                        articleDTO.setContent(UrlConstant.URL+l.getContent());
                    }
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getCrateTime()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 文章审核-数据长度
     */
    @Override
    public Integer findStateNum(Integer state,Integer classifyId,Integer typeId) {
        List<ArticleInfo> list = new ArrayList<>();
        if (typeId == 0){
            if (classifyId == 0){
                list = articleRepository.findAllByStateAndDraft(state,ResultEnum.SUCCESS.getCode());
            }else {
                list = articleRepository.findAllByStateAndDraftAndClassifyId(state,ResultEnum.SUCCESS.getCode(),classifyId);
            }
        }else {
            if (classifyId == 0){
                list = articleRepository.findAllByStateAndDraftAndTypeId(state,ResultEnum.SUCCESS.getCode(), typeId);
            }else {
                list = articleRepository.findAllByStateAndDraftAndClassifyIdAndTypeId(state,ResultEnum.SUCCESS.getCode(),classifyId, typeId);
            }
        }


        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 文章审核-数据长度
     */
    @Override
    public Integer findStateNum(Integer state) {
        List<ArticleInfo> list = articleRepository.findAllByStateAndDraft(state,ResultEnum.SUCCESS.getCode());

        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 用户文章-数据长度
     */
    @Override
    public Integer findStateAndUidNum(Integer state,String uid) {
        List<ArticleInfo> list = articleRepository.findAllByStateAndUidAndDraft(state,uid,ResultEnum.SUCCESS.getCode());
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 文章审核-数据长度(搜索)
     */
    @Override
    public Integer findStateAndTitleOrKeywordsOrClassifyLikeNum(Integer state,String line,String content) {
        content = "%"+content;
        List<ArticleInfo> list = new ArrayList<ArticleInfo>();
        if(line.equals("title")){
            list = articleRepository.findAllByStateAndTitleLikeAndDraft(state,content,ResultEnum.SUCCESS.getCode());
        }
        if(line.equals("keywords")){
            list = articleRepository.findAllByStateAndKeywordsLikeAndDraft(state,content,ResultEnum.SUCCESS.getCode());
        }
        if(line.equals("classify")){
            List<ArticleInfo> finalList = list;
            classifyRepository.findAllByClassifyLike(content).forEach(l->{
                articleRepository.findAllByClassifyIdAndStateAndDraft(l.getId(),state,ResultEnum.SUCCESS.getCode()).forEach(r->{
                    finalList.add(r);
                });
            });
        }
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 举报处理-数据长度
     */
    @Override
    public Integer findDisposeStateNum(Integer disposeState) {
        List<ArticleReport> list = reportRepository.findAllByDisposeState(disposeState);
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 文章管理-未被安排的数据长度
     */
    @Override
    public Integer findStateAndSidNum(Integer state, Integer manageId,Integer classifyId,Integer typeId,Integer position) {
        List<ArticleInfo> list = new ArrayList<>();
        if (classifyId == 0) {
            if (typeId == 0 ) {
                if (position==0){
                    list = articleRepository.findAllByStateAndManageIdAndDraft(state,manageId,ResultEnum.SUCCESS.getCode());
                }
                if (position==1){
                    list = articleRepository.findAllByStateAndManageIdAndDraftAndSlideState(state,manageId,ResultEnum.SUCCESS.getCode(),1);
                }
                if (position==2){
                    list = articleRepository.findAllByStateAndManageIdAndDraftAndRecommendState(state,manageId,ResultEnum.SUCCESS.getCode(),1);
                }
            }else {
                if (position==0){
                    list = articleRepository.findAllByStateAndManageIdAndDraftAndTypeId(state,manageId,ResultEnum.SUCCESS.getCode(),typeId);
                }
                if (position==1){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndTypeIdAndSlideState(state,manageId,ResultEnum.SUCCESS.getCode(),typeId,1);
                }
                if (position==2){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndTypeIdAndRecommendState(state,manageId,ResultEnum.SUCCESS.getCode(),typeId,1);
                }
            }

        }else {
            if (typeId == 0 ) {
                if (position==0){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyId(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId);
                }
                if (position==1){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndSlideState(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId,1);
                }
                if (position==2){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndRecommendState(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId,1);
                }
            }else {
                if (position==0){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeId(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId,typeId);
                }
                if (position==1){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeIdAndSlideState(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId,typeId,1);
                }
                if (position==2){

                    list = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndTypeIdAndRecommendState(state,manageId,ResultEnum.SUCCESS.getCode(),classifyId,typeId,1);
                }
            }

        }
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }


    /**
     * 文章展示-数据长度
     */
    @Override
    public Integer findStateAndShowNum(Integer state, Integer show,Integer typeId,Integer classifyId) {
        List<ArticleInfo> list = new ArrayList<>();
        if (classifyId == 0) {
            if (typeId == 0) {
                list = articleRepository.findAllByStateAndShowStateAndDraft(state,show,ResultEnum.SUCCESS.getCode());
            }else {
                list = articleRepository.findAllByStateAndShowStateAndDraftAndTypeId(state,show,ResultEnum.SUCCESS.getCode(),typeId);
            }
        }else {
            if (typeId == 0) {
                list = articleRepository.findAllByStateAndShowStateAndDraftAndClassifyId(state,show,ResultEnum.SUCCESS.getCode(),classifyId);
            }else {
                list = articleRepository.findAllByStateAndShowStateAndDraftAndTypeIdAndClassifyId(state,show,ResultEnum.SUCCESS.getCode(),typeId,classifyId);
            }

        }
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }


    /**
     * 后台图片展示
     */
    @Override
    public Map<String,Object> findOneArticleSomeImg(String id) {
        ArticleInfo articleInfo = articleRepository.findOne(id);
        Map<String,Object> map = new HashMap<>();
       if (articleInfo.getImgUrl()!=null){
           map.put("imgList",SplitUtil.splitComme(articleInfo.getImgUrl()));
       }else {
           map.put("imgList",null);
       }
        if(articleInfo.getContent()!=null){
           map.put("contentList",SplitUtil.splitComme(articleInfo.getContent()));
        }else {
            map.put("contentList",null);
        }
        return map;
    }

    /**
     * 改变审核状态
     */
    @Override
    public void updateArticleStateByArticleId(String articleId, Integer state,Integer integral) {

        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        String type = typeRepository.findOne(articleInfo.getTypeId()).getType_name();
        if (state==1){
            userRewardService.addPoints(articleInfo.getUid(),articleInfo.getArticleId(),articleInfo.getTitle(),type,integral);
        }
        articleInfo.setState(state);
        articleInfo.setAuditTime(System.currentTimeMillis());

//        articleRepository.save(articleInfo);
    }

    /**
     * 管理 添加侧边栏
     */
    @Override
    public void updateArticleSpecialByArticleId(String articleId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setManageId(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setRecommendState(ResultEnum.PARAM_NULL.getCode());
    }


    /**
     * 管理 添加轮播图
     */
    @Override
    public void updateSlide(String articleId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setSlideState(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setManageId(ResultEnum.PARAM_NULL.getCode());
    }

    /**
     * 获取轮播图数量
     */
    @Override
    public Integer findAllSlideNum(Integer manageId, Integer slideState) {
        List<ArticleInfo> list = articleRepository.findAllByManageIdAndSlideState(manageId,slideState);
        return list.size();
    }


    /**
     * 通过文章typeId查询文章是否存在
     */
    @Override
    public Boolean findArticleByClassifyId(Integer classifyId) {
        List<ArticleInfo> articleInfoList = articleRepository.findAllByClassifyId(classifyId);
        if (articleInfoList.size()!=0){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 管理文章页面展示
     */
    @Override
    public PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndSid(
            Pageable pageable, Integer state, Integer type, Integer manageId,Integer classifyId,Integer position) {

        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if (classifyId == 0){
            if(type == 0){
                if (position==0) {
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraft(pageable, state,manageId,draft);
                }
                if (position==1){
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraftAndSlideState(pageable, state,manageId,draft,1);
                }
                if (position==2) {
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraftAndRecommendState(pageable, state,manageId,draft,1);
                }
            }else {
                if (position==0) {
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraft(pageable, state, type, manageId,draft);
                }
                if (position==1){
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraftAndSlideState(pageable, state, type, manageId,draft,1);
                }
                if (position==2) {
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraftAndRecommendState(pageable, state, type, manageId,draft,1);
                }

            }
        }else {
            if(type == 0){
                if (position==0) {
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyId(pageable, state,manageId,draft,classifyId);
                }
                if (position==1){
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndSlideState(pageable, state,manageId,draft,classifyId,1);
                }
                if (position==2) {
                    articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraftAndClassifyIdAndRecommendState(pageable, state,manageId,draft,classifyId,1);
                }

            }else {
                if (position==0) {
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyId(pageable, state, type, manageId,draft,classifyId);
                }
                if (position==1){
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyIdAndSlideState(pageable, state, type, manageId,draft,classifyId,1);
                }
                if (position==2) {
                    articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraftAndClassifyIdAndRecommendState(pageable, state, type, manageId,draft,classifyId,1);
                }
            }
        }

        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setContent("");
                    if (l.getTypeId()==3){
                        articleDTO.setContent(UrlConstant.URL+l.getContent());
                    }
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getCrateTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    articleDTO.setAuditTime(GetTimeUtil.getDateFormat(l.getAuditTime()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 管理 撤销侧边栏
     */
    @Override
    public void updateArticleManage(String articleId, Integer manageId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setManageId(manageId);
    }

    /**
     * 展示状态改变
     */
    @Override
    public void updateArticleShow(String articleId, Integer showState) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setShowState(showState);
    }

    /**
     * 文章删除
     */
    @Override
    public void deleteArticleByArticleId(String articleId) {
        articleRepository.delete(articleId);
    }

    /**
     * 文章举报删除
     */
    @Override
    public void deleteArticleReportByArticleId(String articleId) {
        reportRepository.deleteAllByAid(articleId);
    }

    /**
     * 草稿上传
     */
    @Override
    public void saveArticleDraft(ArticleDraftForm articleDraftForm) {
        ArticleInfo articleInfo = new ArticleInfo();

        articleDraftForm.setContent(KeyWordFilter.doFilter(articleDraftForm.getContent()));
        articleDraftForm.setKeywords(KeyWordFilter.doFilter(articleDraftForm.getKeywords()));
        articleDraftForm.setTitle(KeyWordFilter.doFilter(articleDraftForm.getTitle()));
        BeanUtils.copyProperties(articleDraftForm,articleInfo);
        if(articleInfo.getArticleId().equals("")){
            articleInfo.setArticleId(KeyUtil.genUniqueKey());
        }
        Long l = System.currentTimeMillis();


        articleInfo.setCrateTime(l);
        articleInfo.setState(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        articleInfo.setShowState(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setManageId(ResultEnum.SUCCESS.getCode());
        articleInfo.setUid(ShiroGetSession.getUser().getUserId());
        articleInfo.setRecommendState(0);
        articleInfo.setSlideState(0);
        articleInfo.setLookNum(0);
        articleRepository.save(articleInfo);
    }

    @Override
    public ArticleInfo findOneByArticleId(String articleId) {
        return articleRepository.findOne(articleId);
    }

    @Override
    public Map<String, Object> issueContent(String articleId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        Map<String, Object> map = new HashMap<>();
        if (articleInfo==null){
            map.put("code",100);
            map.put("message","处理失败！无该记录！");
            return map;
        }
        articleInfo.setDraft(0);
        articleRepository.save(articleInfo);
        map.put("code",0);
        map.put("message","处理成功！");
        return map;
    }


    /**
     * 已通过的文章的展示与否
     */
    @Override
    public PageDTO<ArticleDTO> findAllByShowAndStateAndType(Pageable pageable, Integer showState, Integer state, Integer type,Integer classifyId) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if (classifyId == 0) {
            if(type == 0){
                articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraft(pageable,state,showState,draft);
            }else {
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndShowStateAndDraft(pageable, state, type,showState,draft);
            }
        }else {
            if(type == 0){
                articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraftAndClassifyId(pageable,state,showState,draft,classifyId);
            }else {
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndShowStateAndDraftAndClassifyId(pageable, state, type,showState,draft,classifyId);
            }
        }


        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setContent("");
                    if (l.getTypeId()==3){
                        articleDTO.setContent(UrlConstant.URL+l.getContent());
                    }
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getCrateTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    articleDTO.setAuditTime(GetTimeUtil.getDateFormat(l.getAuditTime()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;

    }

    /**
     * 文章模糊查询
     */
    @Override
    public PageDTO<ArticleDTO> findAllByTitleOrkeywordsOrClassifyLikeAndStateAndType(
            Pageable pageable,String line,String content,Integer type,Integer state) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        content="%"+content+"%";
        if(type == 0){
            if(line.equals("title")){
                articleInfoPage = articleRepository.findAllByStateAndTitleLikeAndDraft(pageable,state,content,ResultEnum.SUCCESS.getCode());
            }
            if(line.equals("keywords")){
                articleInfoPage = articleRepository.findAllByStateAndKeywordsLikeAndDraft(pageable,state,content,ResultEnum.SUCCESS.getCode());
            }
            if(line.equals("classify")){
                classifyRepository.findAllByClassifyLike(content).forEach(l->{
                    articleRepository.findAllByClassifyIdAndStateAndDraft(pageable,l.getId(),state,ResultEnum.SUCCESS.getCode()).getContent().forEach(r->{
                        ArticleDTO articleDTO = new ArticleDTO();
                        BeanUtils.copyProperties(r,articleDTO);
                        articleDTO.setType(typeRepository.findOne(r.getTypeId()).getType_name());
                        articleDTO.setClassify(classifyRepository.findOne(r.getClassifyId()).getClassify());
                        articleDTO.setImgList(SplitUtil.splitComme(r.getImgUrl()));
                        articleDTO.setKeywords(SplitUtil.splitComme(r.getKeywords()));
                        list.add(articleDTO);
                    });
                });
            }
        }else {
            if(line.equals("title")){
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndTitleLikeAndDraft(pageable,state,type,content,ResultEnum.SUCCESS.getCode());
            }
            if(line.equals("keywords")){
                articleInfoPage = articleRepository.findAllByStateAndTypeIdAndKeywordsLikeAndDraft(pageable,state,type,content,ResultEnum.SUCCESS.getCode());
            }
            if(line.equals("classify")){
                classifyRepository.findAllByClassifyLike(content).forEach(l->{
                    articleRepository.findAllByClassifyIdAndStateAndTypeIdAndDraft(pageable,l.getId(),state,type,ResultEnum.SUCCESS.getCode()).getContent().forEach(r->{
                        ArticleDTO articleDTO = new ArticleDTO();
                        BeanUtils.copyProperties(r,articleDTO);
                        articleDTO.setType(typeRepository.findOne(r.getTypeId()).getType_name());
                        articleDTO.setClassify(classifyRepository.findOne(r.getClassifyId()).getClassify());
                        articleDTO.setImgList(SplitUtil.splitComme(r.getImgUrl()));
                        articleDTO.setKeywords(SplitUtil.splitComme(r.getKeywords()));
                        list.add(articleDTO);
                    });
                });
            }
        }

        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }



    /**
     * 举报管理（标记为已处理，取消展示并标记为处理）
     */
    @Override
    public void updateArticleReportDisposeState(String id, Integer disposeState, String articleId, Integer dispose) {
        ArticleReport articleReport = reportRepository.findOne(id);
        articleReport.setDisposeState(ResultEnum.PARAM_NULL.getCode());
        if(dispose == 1){
            updateArticleShow(articleId,ResultEnum.SUCCESS.getCode());
            reportRepository.findAllByAidAndDisposeState(articleId,ResultEnum.SUCCESS.getCode()).forEach(l->{
                l.setDisposeState(ResultEnum.PARAM_NULL.getCode());
            });
        }

    }

    /**
     * 文章举报展示
     */
    @Override
    public PageDTO<ArticleReportDTO> findAllReportByDisposeState(Pageable pageable,Integer disposeState) {
        PageDTO<ArticleReportDTO> pageDTO = new PageDTO<>();
        List<ArticleReportDTO> list = new ArrayList<>();
        Page<ArticleReport> articleReportPage = reportRepository.findAllByDisposeState(pageable,disposeState);
        pageDTO.setTotalPages(articleReportPage.getTotalPages());
        if (!articleReportPage.getContent().isEmpty()){
            articleReportPage.getContent().forEach(l->{
                ArticleReportDTO articleReportDTO = new ArticleReportDTO();
                BeanUtils.copyProperties(l,articleReportDTO);
                articleReportDTO.setType(reportTypeRepository.findOne(l.getTypeId()).getReportType());
                articleReportDTO.setArticle(articleRepository.findOne(l.getAid()));articleReportDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                articleReportDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                list.add(articleReportDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }


    /**
     * 查找用户的草稿
     */
    @Override
    public PageDTO<ArticleDTO> findAllByUserIdAndDraftAndTypeId(Pageable pageable, String userId, Integer draft,Integer typeId) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();

        if(typeId == 0){
            articleInfoPage = articleRepository.findAllByUidAndDraft(pageable,userId,draft);
        }else {
            articleInfoPage = articleRepository.findAllByUidAndDraftAndTypeId(pageable, userId, draft, typeId);
        }
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(l,articleDTO);
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    if(l.getImgUrl()!=null){
                        articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getCrateTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }


    /**
     * 模糊搜索（title）
     */
    @Override
    public PageDTO<ArticleVO> findArticleTitleLikeAndStateAndShowStateAndDraft(Pageable pageable,Integer state, String content, Integer showState, Integer draft) {
        content = "%"+content+"%";
        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByStateAndTitleLikeAndShowStateAndDraft(pageable,state,content,showState,draft);
        List<ArticleVO> list = new ArrayList<>();
        Long time = System.currentTimeMillis();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    ArticleVO searchVO = new ArticleVO();
                    BeanUtils.copyProperties(l,searchVO);
                    searchVO.setArticleId(l.getArticleId());
                    searchVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    searchVO.setCommentNum(commentRepository.findAllByAid(l.getArticleId()).size());
                    if(l.getImgUrl()!=null){
                        searchVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                        searchVO.setImgNum(SplitUtil.splitComme(l.getImgUrl()).size());
                    }
                    searchVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getCrateTime()));
                    User user = userRepository.findOne(l.getUid());
                    Author author = new Author();
                    author.setAvatar(user.getAvatar());
                    author.setName(user.getNikeName());
                    searchVO.setAuthor(author);
                    list.add(searchVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }



    /**
     * 查找所有已通过且显示的文章
     */
    @Override
    public PageDTO<ArticleVO> findAllArticleByShowStateAndStateAndDraft(Pageable pageable, Integer state, Integer showState, Integer draft) {
        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraft(pageable,state,showState,draft);
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
                    indexVO.setImgNum(SplitUtil.splitComme(l.getImgUrl()).size());
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getAuditTime()));
                    User user = userRepository.findOne(l.getUid());
                    Author author = new Author();
                    author.setAvatar(user.getAvatar());
                    author.setName(user.getNikeName());
                    author.setUserId(user.getUserId());
                    indexVO.setAuthor(author);
                    list.add(indexVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }



    public void addDefaultAuditTime(){
        List<ArticleInfo> articleInfoList = articleRepository.findAllByStateAndShowStateAndDraft(1,1,0);
        articleInfoList.forEach(l->{
            System.out.println(System.currentTimeMillis());
            l.setAuditTime(System.currentTimeMillis());
        });
    }
}
