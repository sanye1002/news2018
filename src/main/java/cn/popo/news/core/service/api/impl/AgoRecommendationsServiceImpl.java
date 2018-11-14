package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.Author;
import cn.popo.news.core.entity.common.*;
import cn.popo.news.core.repository.ArticleRepository;
import cn.popo.news.core.repository.ClassifyRepository;
import cn.popo.news.core.repository.RecommendationsRepository;
import cn.popo.news.core.service.api.AgoRecommendationsService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.SplitUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @Author zhaoxiang
 * @Date 2018/11/14
 * @Desc
 */
@Service
public class AgoRecommendationsServiceImpl implements AgoRecommendationsService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ClassifyRepository classifyRepository;
    @Autowired
    private RecommendationsRepository recommendationsRepository;

    @Override
    public PageDTO<ArticleVO> findTodayRecommendArticle(Pageable pageable, String userId, Integer day) {
        PageDTO<ArticleVO> articleInfoPage = new PageDTO<>();
        Page<Recommendations> recommendationsPage = recommendationsRepository
                .findAllByUserIdAndDeriveTimeBetween(pageable, userId
                        , GetTimeUtil.getZeroDateByDays(day), GetTimeUtil.getZeroDateByDays(day + 1));
        articleInfoPage.setTotalPages(recommendationsPage.getTotalPages());
        articleInfoPage.setCurrentPage(pageable.getPageNumber()+1);
        if (!recommendationsPage.getContent().isEmpty()) {
            // 设置文章详情
            articleInfoPage.setPageContent(
                    recommendationsPage.getContent().stream().map(e ->
                            //查出文章详情
                            setVO(articleRepository.findOne(e.getArticleId()))

                    ).collect(Collectors.toList())
            );
        }
        return articleInfoPage;
    }

    private ArticleVO setVO(ArticleInfo articleInfo) {
        Long time = System.currentTimeMillis();
        ArticleVO indexVO = new ArticleVO();
        BeanUtils.copyProperties(articleInfo, indexVO);
        indexVO.setArticleId(articleInfo.getArticleId());
        indexVO.setClassify(classifyRepository.findOne(articleInfo.getClassifyId()).getClassify());
        if (articleInfo.getImgUrl() != null) {
            indexVO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
            indexVO.setImgNum(SplitUtil.splitComme(articleInfo.getImgUrl()).size());
        }
        indexVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time, articleInfo.getCrateTime()));
        Author author = new Author();
        author.setAvatar(articleInfo.getAvatar());
        author.setName(articleInfo.getUsername());
        author.setUserId(articleInfo.getUid());
        indexVO.setAuthor(author);
        return indexVO;
    }

    ;
}
