package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.ArticleDTO;
import cn.popo.news.core.dto.ArticleReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.DetailsVO;
import cn.popo.news.core.dto.api.IndexVO;
import cn.popo.news.core.dto.api.SearchVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.ArticleReport;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.ArticleDraftForm;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.ArticleService;
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


    /**
     * 文章上传
     */
    public void articleSave(ArticleForm articleForm){
        System.out.println(articleForm.getArticleId());
        ArticleInfo articleInfo = new ArticleInfo();
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
        Long l = System.currentTimeMillis();
        articleInfo.setTime(l);
        articleRepository.save(articleInfo);
    }


    /**
     * 后台文章审核页面
     */
    @Override
    public PageDTO<ArticleDTO> findAllArticleDTOByStateAndType(Pageable pageable, Integer state, Integer type) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if(type == 0){
            articleInfoPage = articleRepository.findAllByStateAndDraft(pageable,state,draft);
        }else {
            articleInfoPage = articleRepository.findAllByStateAndTypeIdAndDraft(pageable, state, type,draft);
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
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
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
                    articleDTO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    articleDTO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    articleDTO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
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
    public Integer findStateAndSidNum(Integer state, Integer manageId) {
        List<ArticleInfo> list = articleRepository.findAllByStateAndManageIdAndDraft(state,manageId,ResultEnum.SUCCESS.getCode());
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
    public Integer findStateAndShowNum(Integer state, Integer show) {
        List<ArticleInfo> list = articleRepository.findAllByStateAndShowStateAndDraft(state,show,ResultEnum.SUCCESS.getCode());
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
    public void updateArticleStateByArticleId(String articleId, Integer state) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setState(state);
//        articleRepository.save(articleInfo);
    }

    /**
     * 管理 添加头条，侧边栏
     */
    @Override
    public void updateArticleSpecialByArticleId(String articleId, Integer sid) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        articleInfo.setManageId(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setSid(sid);
    }

    /**
     * 管理文章页面展示
     */
    @Override
    public PageDTO<ArticleDTO> findAllArticleDTOByStateAndTypeAndSid(Pageable pageable, Integer state, Integer type, Integer manageId) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if(type == 0){
            articleInfoPage = articleRepository.findAllByStateAndManageIdAndDraft(pageable, state,manageId,draft);
        }else {
            articleInfoPage = articleRepository.findAllByStateAndTypeIdAndManageIdAndDraft(pageable, state, type, manageId,draft);
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
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
                    list.add(articleDTO);
                });
            }
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 管理 撤销头条，侧边栏
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
        BeanUtils.copyProperties(articleDraftForm,articleInfo);
        if(articleInfo.getArticleId().equals("")){
            articleInfo.setArticleId(KeyUtil.genUniqueKey());
        }
        Long l = System.currentTimeMillis();

        articleInfo.setTime(l);
        articleInfo.setState(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        articleInfo.setShowState(ResultEnum.PARAM_NULL.getCode());
        articleInfo.setManageId(ResultEnum.SUCCESS.getCode());
        articleInfo.setUid(ShiroGetSession.getUser().getUserId());
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
    public PageDTO<ArticleDTO> findAllByShowAndStateAndType(Pageable pageable, Integer showState, Integer state, Integer type) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = null;
        List<ArticleDTO> list = new ArrayList<>();
        Integer draft = ResultEnum.SUCCESS.getCode();
        if(type == 0){
            articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraft(pageable,state,showState,draft);
        }else {
            articleInfoPage = articleRepository.findAllByStateAndTypeIdAndShowStateAndDraft(pageable, state, type,showState,draft);
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
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                    articleDTO.setKeywords(SplitUtil.splitComme(l.getKeywords()));
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
                System.out.println(articleInfoPage.getContent().size());
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
     * 文章举报上传
     */
    @Override
    public void articleReportInfoSave(ReprotInfoForm reprotInfoForm) {
        ArticleReport articleReport = new ArticleReport();
        BeanUtils.copyProperties(reprotInfoForm,articleReport);
        Long l = System.currentTimeMillis();
        articleReport.setTime(l);
        articleReport.setId(KeyUtil.genUniqueKey());
        articleReport.setDisposeState(ResultEnum.SUCCESS.getCode());
        reportRepository.save(articleReport);
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
                    articleDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
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
    public PageDTO<SearchVO> findArticleTitleLikeAndStateAndShowStateAndDraft(Pageable pageable,Integer state, String content, Integer showState, Integer draft) {
        content = "%"+content+"%";
        PageDTO<SearchVO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByStateAndTitleLikeAndShowStateAndDraft(pageable,state,content,showState,draft);
        List<SearchVO> list = new ArrayList<>();
        Long time = System.currentTimeMillis();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    SearchVO searchVO = new SearchVO();
                    BeanUtils.copyProperties(l,searchVO);
                    searchVO.setArticleId(l.getArticleId());
                    searchVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    searchVO.setCommentNum(commentRepository.findAllByAid(l.getArticleId()).size());
                    if(l.getImgUrl()!=null){
                        searchVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    searchVO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    searchVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getTime()));
                    User user = userRepository.findOne(l.getUid());
                    searchVO.setAvatar(user.getAvatar());
                    searchVO.setNikeName(user.getNikeName());
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
    public PageDTO<IndexVO> findAllArticleByShowStateAndStateAndDraft(Pageable pageable, Integer state, Integer showState, Integer draft) {
        PageDTO<IndexVO> pageDTO = new PageDTO<>();
        Page<ArticleInfo> articleInfoPage = articleRepository.findAllByStateAndShowStateAndDraft(pageable,state,showState,draft);
        List<IndexVO> list = new ArrayList<>();
        Long time = System.currentTimeMillis();
        if(articleInfoPage != null){
            pageDTO.setTotalPages(articleInfoPage.getTotalPages());
            if (!articleInfoPage.getContent().isEmpty()){
                articleInfoPage.getContent().forEach(l->{
                    IndexVO indexVO = new IndexVO();
                    BeanUtils.copyProperties(l,indexVO);
                    indexVO.setArticleId(l.getArticleId());
                    indexVO.setClassify(classifyRepository.findOne(l.getClassifyId()).getClassify());
                    indexVO.setCommentNum(commentRepository.findAllByAid(l.getArticleId()).size());
                    if(l.getImgUrl()!=null){
                        indexVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    indexVO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getTime()));
                    User user = userRepository.findOne(l.getUid());
                    indexVO.setAvatar(user.getAvatar());
                    indexVO.setNikeName(user.getNikeName());
                    list.add(indexVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    @Override
    public DetailsVO findArticleDetails() {
        return null;
    }

}
