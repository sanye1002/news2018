package cn.popo.news.core.controller.seo.pc;

import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.dto.api.UserVO;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoCommentService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SEOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhaoxiang
 * @Date 2018/09/06
 * @Desc
 */


@Slf4j
@RestController
@RequestMapping("/pc")
public class DetailController {
    @Autowired
    private AgoCommentService agoCommentService;

    @Autowired
    private AgoArticleService agoArticleService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    @Autowired
    private ClassifyService classifyService;

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;

    @GetMapping("/details")
    public ModelAndView articleDetails(Map<String,Object> map,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "12") Integer size,
                                       @RequestParam(value = "articleId") String articleId,
                                       HttpServletRequest request,
                                       HttpServletResponse response){

        String userId = "";
        if (userSessionUtil.verifyLoginStatus(request,response)){
            userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        }




        //文章详情
        ArticleDetailsVO articleDetailsVO = agoArticleService.findArticleDetails(articleId,userId);
        map.put("articleDetails",articleDetailsVO);
        List<String> content = articleDetailsVO.getKeywordList();

        //用户信息及用户文章推荐
        UserVO userVO = agoArticleService.findArticleDetailsUser(articleId,userId);
        map.put("author",userVO);

        //图文
        List<ArticleVO> realTimeNews = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,ONE,ONE);
        PageDTO<ArticleVO> realTimeNewsPage = new PageDTO<>();
        realTimeNewsPage.setPageContent(realTimeNews);
        map.put("realTimeNews",realTimeNewsPage);
        //多图
        List<ArticleVO> imgs = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,TWO,ONE);
        PageDTO<ArticleVO> imgsPage = new PageDTO<>();
        imgsPage.setPageContent(imgs);
        map.put("recommendImgs",imgsPage);
        //视频
        List<ArticleVO> videos = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,THREE,ONE);
        PageDTO<ArticleVO> videosPage = new PageDTO<>();
        videosPage.setPageContent(videos);
        map.put("recommendVideos",videosPage);

        /*List<ArticleVO> list = new ArrayList<>();
        content.forEach(l->{
            List<ArticleVO> articleVOList = agoArticleService.findAllArticleByKeywordsLike(ONE,ZERO,ONE,l);
            List<ArticleVO> temp = new ArrayList<>(articleVOList);
            temp.retainAll(list);
            articleVOList.removeAll(temp);
            list.addAll(articleVOList);
        });
        double d = size;
        double l = list.size()/d;
        Integer totalPages = (int)Math.ceil(l);
        PageDTO<ArticleVO> pageDTO1 = new PageDTO<>();
        if(list.size()!=0){
            if (totalPages == page){
                pageDTO1.setPageContent(list.subList((page-1)*size,list.size()));
            }else {
                pageDTO1.setPageContent(list.subList((page-1)*size,size*page));
            }
        }
        pageDTO1.setCurrentPage(page);
        pageDTO1.setTotalPages(totalPages);*/
//        map.put("article", pageDTO1);

        List<Classify> cla = classifyService.findAllClassify();
        map.put("indexNavigation",cla);

        map.put("desc",SEOUtil.getDescription(articleDetailsVO.getContent()));
        map.put("keyword",SEOUtil.getKeywordsByList(articleDetailsVO.getKeywordList()));


        map.put("pageId", 1000000);
        map.put("pageTitle", "数据查看");

        if (articleDetailsVO.getTypeId()==1){
            return new ModelAndView("seo/pc/page/index",map);
        }else if(articleDetailsVO.getTypeId()==2){
            return new ModelAndView("seo/pc/page/img_index",map);
        }else {
            return new ModelAndView("seo/pc/page/video_index",map);
        }

    }

    @PostMapping("/comment")
    public ResultVO<Map<String,Object>> articleSeoComment(Map<String,Object> map,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                       @RequestParam(value = "articleId") String articleId,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response){


        String userId = "";
        if (userSessionUtil.verifyLoginStatus(request,response)){
            userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        }

        //评论
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","praiseNum"));
        PageDTO<CommentVO> pageDTO = agoCommentService.findComment(pageRequest,articleId,userId,ONE);
        pageDTO.setCurrentPage(page);
        Integer commentNum = agoCommentService.findCommentNumByArticleId(articleId,ONE);
        pageDTO.setCommentNum(commentNum);
        map.put("comment", pageDTO);


        return ResultVOUtil.success(map);
    }


}
