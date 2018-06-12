package cn.popo.news.core.controller.api;


import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.common.Attention;
import cn.popo.news.core.entity.common.DynamicPraise;
import cn.popo.news.core.entity.form.DynamicReportForm;
import cn.popo.news.core.entity.param.PersonalParam;
import cn.popo.news.core.repository.AttentionRepository;
import cn.popo.news.core.repository.DynamicPraiseRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoAttentionService;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {

    @Autowired
    private AgoAttentionService agoAttentionService;

    @Autowired
    private AgoArticleService agoArticleService;

    @Autowired
    private AgoPersonalService agoPersonalService;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private DynamicPraiseRepository dynamicPraiseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionUtil userSessionUtil;

    /**
     * @param fid
     * @return
     * @desc 添加关注
     */
    @PostMapping("/user/attention")
    public ResultVO<Map<String, Object>> addAttention(Map<String, Object> map,
                                                      @RequestParam(value = "fid") String fid,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        String aid = userSessionUtil.getUserByCookie(request,response).getUserId();
        Attention attention = attentionRepository.findAllByAidAndFid(aid, fid);
        if (attention != null) {
            return ResultVOUtil.error(100, "已关注");
        }
        AttentionVO attentionVO = agoAttentionService.addAttention(aid, fid);
        map.put("attention", attentionVO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param attentionId
     * @return
     * @desc 取消关注
     */
    @PostMapping("/user/attention/delete")
    public ResultVO<Map<String, Object>> deleteAttention(Map<String, Object> map,
                                                         @RequestParam(value = "attentionId") Integer attentionId,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        agoAttentionService.deleteAttention(attentionId);
        return ResultVOUtil.success(map);
    }


    /**
     * @param page size
     * @return
     * @desc 用户关注列表
     */
    @PostMapping("/user/attention/list")
    public ResultVO<Map<String, Object>> userAttentionList(Map<String, Object> map,
                                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();

        //评论
        PageRequest pageRequest = new PageRequest(page - 1, size);
        PageDTO<AttentionVO> pageDTO = agoAttentionService.findAllAttention(pageRequest, userId);
        pageDTO.setCurrentPage(page);
        map.put("attention", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return
     * @desc 用户粉丝列表
     */
    @PostMapping("/user/fans/list")
    public ResultVO<Map<String, Object>> userFansList(Map<String, Object> map,
                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
        PageRequest pageRequest = new PageRequest(page - 1, size);
        PageDTO<AttentionVO> pageDTO = agoAttentionService.findAllFans(pageRequest, userId);
        pageDTO.setCurrentPage(page);
        map.put("fans", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param typeId page size
     * @return List<ArticleVO>
     * @desc 用户文章
     */
    @PostMapping("/user/collected/article")
    public ResultVO<Map<String, Object>> search(Map<String, Object> map,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                @RequestParam(value = "typeId") Integer typeId,
                                                @RequestParam(value = "userId") String userId,
                                                HttpServletRequest request,
                                                HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("desc", "time"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest, userId, typeId);
        pageDTO.setCurrentPage(page);
        map.put("pageContent", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param content imgUrl userId
     * @return
     * @desc 发表动态
     */
    @PostMapping("/user/dynamic/save")
    public ResultVO<Map<String, Object>> addDynamic(Map<String, Object> map,
                                                    @RequestParam(value = "content") String content,
                                                    @RequestParam(value = "imgUrl") String imgUrl,
                                                    @RequestParam(value = "userId") String userId,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        agoPersonalService.saveDynamic(userId, content, imgUrl);

        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return
     * @desc 用户动态
     */
    @PostMapping("/user/dynamic/list")
    public ResultVO<Map<String, Object>> userDynamicList(Map<String, Object> map,
                                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                         @RequestParam(value = "userId") String userId,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("desc", "time"));
        PageDTO<DynamicVO> pageDTO = agoPersonalService.findAllDynamicByUserId(pageRequest, userId);
        pageDTO.setCurrentPage(page);
        map.put("dynamic", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param page size
     * @return
     * @desc 浏览记录
     */
    @PostMapping("/user/history/list")
    public ResultVO<Map<String, Object>> userBrowsingHistoryList(Map<String, Object> map,
                                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                 @RequestParam(value = "userId") String userId,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("desc", "time"));
        PageDTO<LookVO> pageDTO = agoPersonalService.findSixBrowsingHistory(pageRequest, userId);
        pageDTO.setCurrentPage(page);
        map.put("look", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param page size
     * @return
     * @desc 个人首页
     */
    @PostMapping("/user/index")
    public ResultVO<Map<String, Object>> userIndex(Map<String, Object> map,
                                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                   @RequestParam(value = "userId1", defaultValue = "0") String userId1,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response

    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();

        if(!userId1.equals("0")){
            userId = userId1;
        }



        //用户信息
        PersonalVO personalVO = agoPersonalService.findUserInfoByUserId(userId);
        map.put("user", personalVO);

        //动态
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("desc", "time"));
        PageDTO<DynamicVO> pageDTO = agoPersonalService.findAllDynamicByUserId(pageRequest, userId);
        pageDTO.setCurrentPage(page);
        map.put("dynamic", pageDTO);

        //浏览记录
        PageDTO<LookVO> lookPageDTO = agoPersonalService.findSixBrowsingHistory(pageRequest, userId);
        lookPageDTO.setCurrentPage(page);
        map.put("look", lookPageDTO);

        //粉丝和关注数量
        Integer fans = agoAttentionService.findFansNum(userId);
        Integer attentionNum = agoAttentionService.findAttentionNum(userId);
        map.put("fansNum", fans);
        map.put("attentionNum", attentionNum);

        //图文
        PageDTO<ArticleVO> articlePageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest, userId, 1);
        articlePageDTO.setCurrentPage(page);
        map.put("article", articlePageDTO);
        //多图
        PageDTO<ArticleVO> imgsPageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest, userId, 2);
        imgsPageDTO.setCurrentPage(page);
        map.put("imgs", imgsPageDTO);
        //视频
        PageDTO<ArticleVO> videoPageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest, userId, 3);
        videoPageDTO.setCurrentPage(page);
        map.put("video", videoPageDTO);

        //粉丝
        PageDTO<AttentionVO> fansPageDTO = agoAttentionService.findAllFans(pageRequest, userId);
        fansPageDTO.setCurrentPage(page);
        map.put("fans", fansPageDTO);

        //关注
        PageDTO<AttentionVO> attentionVOPageDTO = null;
        if (userId1.equals("0")) {
            attentionVOPageDTO = agoAttentionService.findAllAttention(pageRequest, userId);
        } else {
            attentionVOPageDTO = agoAttentionService.findOtherUserAttention(
                    pageRequest, userSessionUtil.getUserByCookie(request,response).getUserId(), userId1);
        }

        attentionVOPageDTO.setCurrentPage(page);
        map.put("attention", attentionVOPageDTO);

        return ResultVOUtil.success(map);
    }

    /**
     * @param personalParam
     * @return
     * @desc 用户信息更新
     */
    @PostMapping("/user/update")
    public ResultVO<Map<String, Object>> updateUserInfo(@Valid PersonalParam personalParam,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        personalParam.setUserId(userId);
        if (personalParam.getNikeName() != null) {
            if (userRepository.findOne(userId).getNikeName().equals(personalParam.getNikeName())) {
                return ResultVOUtil.error(100, "昵称已存在！！！！！");
            }
        }

        Map<String, Object> map = new HashMap<>();
        agoPersonalService.updateUserInfo(personalParam);
        return ResultVOUtil.success(map);
    }

    /**
     * @param dynamicId
     * @return
     * @desc 动态点赞
     */
    @PostMapping("/dynamic/praise")
    public ResultVO<Map<String, Object>> dynamicPraise(
            @RequestParam(value = "dynamicId") String dynamicId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        DynamicPraise dynamicPraise = dynamicPraiseRepository.findAllByUidAndDynamicId(userId, dynamicId);
        if (dynamicPraise != null) {
            return ResultVOUtil.error(100, "已赞");
        }
        agoPersonalService.dynamicPraise(userId, dynamicId);
        Map<String, Object> map = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param uid sendMessage
     * @return
     * @desc 用户通信
     */
    @PostMapping("/user/communication/save")
    public ResultVO<Map<String, Object>> communication(
            @RequestParam(value = "sendMessage") String sendMessage,
            @RequestParam(value = "uid") String uid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (userSessionUtil.verifyLoginStatus(request, response)) {
            String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
            agoPersonalService.saveCommunication(uid, userId, sendMessage);
        } else {
            return ResultVOUtil.error(3, "用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return
     * @desc 通信记录
     */
    @PostMapping("/user/communication/list")
    public ResultVO<Map<String, Object>> userCommunicationList(Map<String, Object> map,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                               @RequestParam(value = "uid") String uid,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response
    ) {
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }
        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
        agoPersonalService.updateCommunicationLookState(uid, userId);
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("asc", "time"));
        PageDTO<PrivateLetterVO> pageDTO = agoPersonalService.findUserCommunication(pageRequest, uid, userId);
        pageDTO.setCurrentPage(page);
        map.put("chatMessages", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param dynamicReportForm
     * @return
     * @desc 动态举报上传
     */
    @PostMapping("/dynamic/report")
    public ResultVO<Map<String,Object>> commentReportSave(@Valid DynamicReportForm dynamicReportForm,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        dynamicReportForm.setUid(userSessionUtil.getUserByCookie(request,response).getUserId());
        String content = dynamicReportForm.getContent();
        if (!KeyWordFilter.checkWords(content).equals("")){
            return ResultVOUtil.error(100,"举报内容违规："+KeyWordFilter.checkWords(content));
        }
        agoPersonalService.dynamicReportSave(dynamicReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


}
