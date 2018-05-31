package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.ArticleReport;
import cn.popo.news.core.entity.common.Collect;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.ArticleRepository;
import cn.popo.news.core.repository.CollectRepository;
import cn.popo.news.core.repository.ReportRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import cn.popo.news.core.utils.SplitUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * @Author  Administrator
 * @Date    2018/5/31 18:21
 * @Desc    前台文章逻辑控制中心
 */


@Transactional
@Service
public class AgoArticleServiceImpl implements AgoArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectRepository collectRepository;

    @Autowired
    ReportRepository reportRepository;


    /**
     * 通过文章id查找文章详情
     */
    @Override
    public ArticleDetailsVO findArticleDetails(String articleId) {
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
                articleDetailsVO.setCollect(collect.getId());
            }else {
                articleDetailsVO.setCollect(0);
            }
        }


        return articleDetailsVO;
    }


    /**
     * 文章收藏（添加）
     */
    @Override
    public void articleCollect(String articleId, String userId) {
        Collect collect = new Collect();
        collect.setAid(articleId);
        collect.setUid(userId);
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

}
