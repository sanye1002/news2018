package cn.popo.news.core.controller.oa;

import cn.popo.news.core.config.UploadConfig;
import cn.popo.news.core.dto.ArticleDTO;
import cn.popo.news.core.dto.ArticleReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.form.ArticleDraftForm;
import cn.popo.news.core.entity.form.ArticleForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.service.*;
import cn.popo.news.core.service.impl.ClassifyServiceImpl;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.ShiroGetSession;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.utils.SplitUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-22 下午 4:04
 * @Description 文章控制中心
 */

@Slf4j
@Controller
@RequestMapping("/oa/article")
public class ArticleController {
    @Autowired
    private ClassifyServiceImpl classifyService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CommentReportService commentReportService;
    @Autowired
    private ReplyReportService replyReportService;

    /**
     * 文章发布页面展示
     */
    @GetMapping("/issue")
    @RequiresPermissions("userArticle:add")
    public ModelAndView index1(Map<String,Object> map,
                               @RequestParam(value = "id", defaultValue = "") String id
    ){
        List<Classify> list = classifyService.findAllClassify();
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleDTO articleDTO = new ArticleDTO();
        if (!id.equals("")){
            articleInfo = articleService.findOneByArticleId(id);
            if(articleInfo != null){
                BeanUtils.copyProperties(articleInfo,articleDTO);
                articleDTO.setKeyword(articleInfo.getKeywords());
                if (articleInfo.getImgUrl()!=null){
                    articleDTO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
                }
                map.put("pageTitle","文章编辑");
                map.put("draftList",1);
            }

        }else {
            articleDTO.setOriginal(0);
            articleDTO.setClassifyId(list.get(0).getId());
            map.put("pageTitle","文章发布");
            map.put("draftList",0);
        }
        map.put("articleId",id);
        map.put("pageId",104);
        map.put("classify",list);
        map.put("article",articleDTO);
        map.put("user",ShiroGetSession.getUser());
        return new ModelAndView("pages/article-issue",map);
    }

    /**
     * 多图发布页面展示
     */
    @GetMapping("/issue/img")
    public ModelAndView imgIssue(Map<String,Object> map,
                               @RequestParam(value = "id", defaultValue = "") String id
    ){
        List<Classify> list = classifyService.findAllClassify();
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleDTO articleDTO = new ArticleDTO();
        if (!id.equals("")){
            articleInfo = articleService.findOneByArticleId(id);
            if(articleInfo != null){
                BeanUtils.copyProperties(articleInfo,articleDTO);
                articleDTO.setKeyword(articleInfo.getKeywords());
                if(articleInfo.getContent()!=null){
                    articleDTO.setManyImgDesc(SplitUtil.splitComme(articleInfo.getContent()));
                }
                if (articleInfo.getImgUrl()!=null){
                    articleDTO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
                }

                map.put("pageTitle","文章编辑");
                map.put("draftList",1);
            }

        }else {
            articleDTO.setOriginal(0);
            articleDTO.setClassifyId(list.get(0).getId());
            map.put("pageTitle","文章发布");
            map.put("draftList",0);
        }
        map.put("articleId",id);
        map.put("pageId",121);
        map.put("classify",list);
        map.put("article",articleDTO);
        map.put("user",ShiroGetSession.getUser());
        return new ModelAndView("pages/article-issue-img",map);
    }

    /**
     * 视频上传页面展示
     */
    @GetMapping("/video/index")
    @RequiresPermissions("userArticle:add")
    public ModelAndView videoIndex(Map<String,Object> map,
    @RequestParam(value = "id", defaultValue = "") String id){

        List<Classify> list = classifyService.findAllClassify();
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleDTO articleDTO = new ArticleDTO();
        if (!id.equals("")){
            articleInfo = articleService.findOneByArticleId(id);
            if(articleInfo != null){
                BeanUtils.copyProperties(articleInfo,articleDTO);
                articleDTO.setKeyword(articleInfo.getKeywords());
                if (articleInfo.getImgUrl()!=null){
                    articleDTO.setImgList(SplitUtil.splitComme(articleInfo.getImgUrl()));
                }
                map.put("pageTitle","文章编辑");
                map.put("draftList",1);
            }

        }else {
            articleDTO.setOriginal(0);
            articleDTO.setClassifyId(list.get(0).getId());
            map.put("pageTitle","文章发布");
            map.put("draftList",0);
        }
        map.put("articleId",id);
        map.put("pageId",120);
        map.put("classify",list);
        map.put("article",articleDTO);
        map.put("user",ShiroGetSession.getUser());
        return new ModelAndView("pages/videoindex",map);
    }

    /**
     * 文章上传
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, String>> save(@Valid ArticleForm articleForm){
        articleService.articleSave(articleForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 文章草稿上传
     */
    @PostMapping("/draft/save")
    @ResponseBody
    public ResultVO<Map<String, String>> saveDraft(@Valid ArticleDraftForm articleDraftForm){
        articleService.saveArticleDraft(articleDraftForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }



    /**
     * 用户文章删除
     */
    @PostMapping("/user/delete")
    @ResponseBody
    public ResultVO<Map<String, String>> userArticleDelete(@RequestParam(value = "articleId", defaultValue = "") String articleId){
        articleService.deleteArticleByArticleId(articleId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 文章审核展示
     */
    @GetMapping("/auditlist")
    @RequiresPermissions("articleCheck:list")
    public ModelAndView list(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "state", defaultValue = "2") Integer state,
                             @RequestParam(value = "type", defaultValue = "0") Integer type
    ){
        map.put("pageId",101);
        map.put("pageTitle","文章审核");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleDTO> pageDTO = articleService.findAllArticleDTOByStateAndType(pageRequest,state,type);
        Integer noPass = articleService.findStateNum(ResultEnum.SUCCESS.getCode());
        Integer pass = articleService.findStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer noAudit = articleService.findStateNum(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/auditlist.html");
        map.put("size", size);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("noAudit",noAudit);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-audit-list",map);
    }

    /**
     * 文章草稿展示
     */
    @GetMapping("/draft/list")
    public ModelAndView draftList(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "state", defaultValue = "2") Integer state,
                             @RequestParam(value = "type", defaultValue = "0") Integer type
    ){
        map.put("pageId",109);
        map.put("pageTitle","文章草稿");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        String userId = ShiroGetSession.getUser().getUserId();
        PageDTO<ArticleDTO> pageDTO = articleService.findAllByUserIdAndDraftAndTypeId(pageRequest,userId,ResultEnum.PARAM_NULL.getCode(),type);
        Integer noPass = articleService.findStateNum(ResultEnum.SUCCESS.getCode());
        Integer pass = articleService.findStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer noAudit = articleService.findStateNum(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/draft/list.html");
        map.put("size", size);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("noAudit",noAudit);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-draft-list",map);
    }


    /**
     * 用户文章展示
     */
    @GetMapping("/user/articlelist")
    @RequiresPermissions("userArticle:list")
    public ModelAndView userlist(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "state", defaultValue = "2") Integer state,
                             @RequestParam(value = "type", defaultValue = "0") Integer type
    ){
        map.put("pageId",108);
        map.put("pageTitle","用户文章");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        String uid = ShiroGetSession.getUser().getUserId();
        PageDTO<ArticleDTO> pageDTO = articleService.findAllArticleDTOByStateAndTypeAndUid(pageRequest,state,type,uid);
        Integer noPass = articleService.findStateAndUidNum(ResultEnum.SUCCESS.getCode(),uid);
        Integer pass = articleService.findStateAndUidNum(ResultEnum.PARAM_NULL.getCode(),uid);
        Integer noAudit = articleService.findStateAndUidNum(ResultEnum.PLATFORM_BOOS_NULL.getCode(),uid);
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/user/articlelist.html");
        map.put("size", size);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("noAudit",noAudit);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-user-list",map);
    }

    /**
     * 文章审核展示(搜索)
     */
    @GetMapping("/selectlist")
    public ModelAndView selectList(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "state", defaultValue = "2") Integer state,
                             @RequestParam(value = "type", defaultValue = "0") Integer type,
                             @RequestParam(value = "line", defaultValue = "title") String line,
                             @RequestParam(value = "content", defaultValue = "") String content
    ){
        map.put("pageId",1);
        map.put("pageTitle","文章发布");
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<ArticleDTO> pageDTO = articleService.findAllByTitleOrkeywordsOrClassifyLikeAndStateAndType(pageRequest,line,content,type,state);
        Integer noPass = articleService.findStateNum(ResultEnum.SUCCESS.getCode());
        Integer pass = articleService.findStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer noAudit = articleService.findStateNum(ResultEnum.PLATFORM_BOOS_NULL.getCode());
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/selectlist.html");
        map.put("size", size);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("noAudit",noAudit);
        map.put("line",line);
        map.put("content",content);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-select-list",map);
    }

    /**
     * 文章审核
     */
    @PostMapping("/audit")
    @ResponseBody
    public ResultVO<Map<String, String>> articleAudit(@RequestParam(value = "articleId") String articleId,
                            @RequestParam(value = "state") Integer state,
                            @RequestParam(value = "integral") Integer integral
    ){

        articleService.updateArticleStateByArticleId(articleId,state,integral);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 文章及所有关联内容全部删除
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String, String>> articleDelete(
            @RequestParam(value = "articleId", defaultValue = "") String articleId){

        if (articleId==""){
            return ResultVOUtil.error(100,"数据为空~");
        }
        commentService.findCommentIdByArticleId(articleId).forEach(l->{
            //回复举报删除
            replyService.findReplyIdByCommentId(l).forEach(r->{
                replyReportService.deleteReplyReportByReplyId(r);
            });
            //回复删除
            replyService.deleteReplyByCommentId(l);
            //评论举报删除
            commentReportService.deleteCommentReportByCommentId(l);
        });
        //评论删除
        commentService.deleteComment(articleId);
        //文章举报删除
        articleService.deleteArticleReportByArticleId(articleId);
        //文章删除
        articleService.deleteArticleByArticleId(articleId);

        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 特殊板块页面展示（头条，侧边栏）
     */
    @GetMapping("/managelist")
    @RequiresPermissions("articleDispose:list")
    public ModelAndView articleManageList(Map<String,Object> map,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "12") Integer size,
                                      @RequestParam(value = "state", defaultValue = "1") Integer state,
                                      @RequestParam(value = "type", defaultValue = "0") Integer type,
                                      @RequestParam(value = "manageId", defaultValue = "0") Integer manageId
                                      ){
        map.put("pageId",103);
        map.put("pageTitle","文章管理");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleDTO> pageDTO = articleService.findAllArticleDTOByStateAndTypeAndSid(pageRequest,state,type,manageId);
        Integer passNum = articleService.findStateAndSidNum(state,ResultEnum.SUCCESS.getCode());
        Integer onNum = articleService.findStateAndSidNum(state,ResultEnum.PARAM_NULL.getCode());
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/managelist.html");
        map.put("size", size);
        map.put("manageId",manageId);
        map.put("passNum",passNum);
        map.put("onNum",onNum);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-manage-list",map);
    }


    /**
     * 管理 添加侧边栏
     */
    @PostMapping("/recomment")
    @ResponseBody
    public ResultVO<Map<String, String>> articleManage(@RequestParam(value = "articleId") String articleId
                                                        ){
        articleService.updateArticleSpecialByArticleId(articleId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }



    /**
     * 管理 添加轮播图
     */
    @PostMapping("/slide")
    @ResponseBody
    public ResultVO<Map<String, String>> articleSlide(@RequestParam(value = "articleId") String articleId
    ){
        Map<String,Object> map  = new HashMap<>();
        Integer slideNum = articleService.findAllSlideNum(ResultEnum.PARAM_NULL.getCode(),ResultEnum.PARAM_NULL.getCode());
        if (slideNum<6){
            articleService.updateSlide(articleId);

            return ResultVOUtil.success(map);
        }else {
            map.put("message","轮播图已达上限");
            return ResultVOUtil.success(map);
        }


    }

    /**
     * 管理 撤销侧边栏,轮播图
     */
    @PostMapping("/managedelete")
    @ResponseBody
    public ResultVO<Map<String, String>>articleManageDelete(@RequestParam(value = "articleId") String articleId,
                                                      @RequestParam(value = "manageId") Integer manageId
    ){
        articleService.updateArticleManage(articleId,manageId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


    /**
     * 文章前台显示否
     */
    @GetMapping("/showlist")
    @RequiresPermissions("articleDispose:list")
    public ModelAndView listShow(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "state", defaultValue = "1") Integer state,
                             @RequestParam(value = "type", defaultValue = "0") Integer type,
                             @RequestParam(value = "showState", defaultValue = "1") Integer showState
    ){
        map.put("pageId",102);
        map.put("pageTitle","文章展示");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleDTO> pageDTO = articleService.findAllByShowAndStateAndType(pageRequest,showState,state,type);
        Integer showY = articleService.findStateAndShowNum(ResultEnum.PARAM_NULL.getCode(),ResultEnum.PARAM_NULL.getCode());
        Integer showN = articleService.findStateAndShowNum(ResultEnum.PARAM_NULL.getCode(),ResultEnum.SUCCESS.getCode());
        map.put("state", state);
        map.put("type",type);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/showlist.html");
        map.put("size", size);
        map.put("showY", showY);
        map.put("showN", showN);
        map.put("showState",showState);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-show-list",map);
    }

    /**
     * 展示状态改变
     */
    @PostMapping("/show")
    @ResponseBody
    public ResultVO<Map<String, String>>articleShow(@RequestParam(value = "articleId") String articleId,
                                                      @RequestParam(value = "showState") Integer showState
    ){
        articleService.updateArticleShow(articleId,showState);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


    /**
     * 文章举报展示
     */
    @GetMapping("/reportlist")
    @RequiresPermissions("articleReport:list")
    public ModelAndView reportlist(Map<String,Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                 @RequestParam(value = "disposeState", defaultValue = "0") Integer disposeState
    ){
        map.put("pageId",105);
        map.put("pageTitle","文章举报");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<ArticleReportDTO> pageDTO = articleService.findAllReportByDisposeState(pageRequest,disposeState);
        Integer OnDispose = articleService.findDisposeStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer UnDispose = articleService.findDisposeStateNum(ResultEnum.SUCCESS.getCode());
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/reportlist.html");
        map.put("size", size);
        map.put("disposeState",disposeState);
        map.put("OnDispose",OnDispose);
        map.put("UnDispose",UnDispose);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-report-list",map);
    }

    /**
     * 举报管理（标记为已处理，取消展示并标记为处理）
     */
    @PostMapping("/reportdispose")
    @ResponseBody
    public ResultVO<Map<String, String>>articlereportdispose(
                                                    @RequestParam(value = "articleId") String articleId,
                                                    @RequestParam(value = "disposeState") Integer disposeState,
                                                    @RequestParam(value = "dispose") Integer dispose,
                                                    @RequestParam(value = "id") String id
    ){
        articleService.updateArticleReportDisposeState(id,disposeState,articleId,dispose);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
    @PostMapping("/issue/content")
    @ResponseBody
    public ResultVO<Map<String, String>>issueContent(@RequestParam(value = "articleId") String articleId){

        Map<String,Object> map  = articleService.issueContent(articleId);

        return ResultVOUtil.success(map);
    }


}
