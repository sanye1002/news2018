package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.ArticleReport;
import cn.popo.news.core.entity.common.Collect;
import cn.popo.news.core.entity.common.User;
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
    private TypeRepository typeRepository;

    @Autowired
    private CommentRepository commentRepository;


    /**
     * 通过文章id查找文章详情
     */
    @Override
    public ArticleDetailsVO findArticleDetails(String articleId,String userId) {
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        ArticleDetailsVO articleDetailsVO = new ArticleDetailsVO();
        if(articleInfo!=null){
            BeanUtils.copyProperties(articleInfo,articleDetailsVO);
            User user = userRepository.findOne(articleInfo.getUid());
            articleDetailsVO.setAvatar(user.getAvatar());
            articleDetailsVO.setNickName(user.getNikeName());
            articleDetailsVO.setKeywordList(SplitUtil.splitComme(articleInfo.getKeywords()));
            articleDetailsVO.setTime(GetTimeUtil.getDateFormat(articleInfo.getTime()));
            if(articleInfo.getImgUrl()!=null){
                articleDetailsVO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
            }
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
     * 文章收藏（添加）
     */
    @Override
    public void articleCollect(CollectParam collectParam) {
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectParam,collect);
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
                    indexVO.setType(typeRepository.findOne(l.getTypeId()).getType_name());
                    indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,articleInfo.getTime()));
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

}
