package cn.popo.news.core.controller.api;


import cn.popo.news.common.utils.WordParticipleUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.param.PersonalParam;
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

    /**
     * @param fid
     * @return
     * @desc 添加关注
     */
    @PostMapping("/user/attention")
    public ResultVO<Map<String,Object>> addAttention(Map<String,Object> map,
                                                  @RequestParam(value = "fid") String fid){

        agoAttentionService.addAttention(fid,fid);
        return ResultVOUtil.success(map);
    }

    /**
     * @param attentionId
     * @return
     * @desc 取消关注
     */
    @PostMapping("/user/attention/delete")
    public ResultVO<Map<String,Object>> deleteAttention(Map<String,Object> map,
                                                  @RequestParam(value = "attentionId") Integer attentionId){

        agoAttentionService.deleteAttention(attentionId);
        return ResultVOUtil.success(map);
    }


    /**
     * @param page size
     * @return
     * @desc 用户关注列表
     */
    @PostMapping("/user/attention/list")
    public ResultVO<Map<String,Object>> userAttentionList(Map<String,Object> map,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "12") Integer size){
        //评论
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<AttentionVO> pageDTO = agoAttentionService.findAllAttention(pageRequest,"1527582639993");
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
    public ResultVO<Map<String,Object>> userFansList(Map<String,Object> map,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "12") Integer size){
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<AttentionVO> pageDTO = agoAttentionService.findAllFans(pageRequest,"1527582639993");
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
    public ResultVO<Map<String,Object>> search(Map<String,Object> map,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                               @RequestParam(value = "typeId") Integer typeId
    ){
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest,"1527582639993",typeId);
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
    public ResultVO<Map<String,Object>> addDynamic(Map<String,Object> map,
                                                   @RequestParam(value = "content") String content,
                                                   @RequestParam(value = "imgUrl") String imgUrl,
                                                   @RequestParam(value = "userId") String userId
                                                   ){
        agoPersonalService.saveDynamic(userId,content,imgUrl);

        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return
     * @desc 用户动态
     */
    @PostMapping("/user/dynamic/list")
    public ResultVO<Map<String,Object>> userDynamicList(Map<String,Object> map,
                                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                          @RequestParam(value = "userId") String userId
    ){
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<DynamicVO> pageDTO = agoPersonalService.findAllDynamicByUserId(pageRequest,userId);
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
    public ResultVO<Map<String,Object>> userBrowsingHistoryList(Map<String,Object> map,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                        @RequestParam(value = "userId") String userId
    ){
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<LookVO> pageDTO = agoPersonalService.findSixBrowsingHistory(pageRequest,userId);
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
    public ResultVO<Map<String,Object>> userIndex(Map<String,Object> map,
                                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                @RequestParam(value = "userId") String userId
    ){
        //用户信息
        PersonalVO personalVO = agoPersonalService.findUserInfoByUserId(userId);
        map.put("user",personalVO);

        //动态
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<DynamicVO> pageDTO = agoPersonalService.findAllDynamicByUserId(pageRequest,userId);
        pageDTO.setCurrentPage(page);
        map.put("dynamic", pageDTO);

        //浏览记录
        PageDTO<LookVO> lookPageDTO = agoPersonalService.findSixBrowsingHistory(pageRequest,userId);
        lookPageDTO.setCurrentPage(page);
        map.put("look", lookPageDTO);

        //粉丝和关注数量
        Integer fans = agoAttentionService.findFansNum(userId);
        Integer attentionNum = agoAttentionService.findAttentionNum(userId);
        map.put("fansNum",fans);
        map.put("attentionNum",attentionNum);

        //图文
        PageDTO<ArticleVO> articlePageDTO= agoArticleService.findAllArticleByUserCollect(pageRequest,"1527582639993",1);
        articlePageDTO.setCurrentPage(page);
        map.put("article", articlePageDTO);
        //多图
        PageDTO<ArticleVO> imgsPageDTO= agoArticleService.findAllArticleByUserCollect(pageRequest,"1527582639993",2);
        imgsPageDTO.setCurrentPage(page);
        map.put("imgs", imgsPageDTO);
        //视频
        PageDTO<ArticleVO> videoPageDTO= agoArticleService.findAllArticleByUserCollect(pageRequest,"1527582639993",3);
        videoPageDTO.setCurrentPage(page);
        map.put("video", videoPageDTO);

        //粉丝
        PageDTO<AttentionVO> fansPageDTO = agoAttentionService.findAllFans(pageRequest,"1527582639993");
        fansPageDTO.setCurrentPage(page);
        map.put("fans", fansPageDTO);

        //关注
        PageDTO<AttentionVO> attentionVOPageDTO = agoAttentionService.findAllAttention(pageRequest,"1527582639993");
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
    public ResultVO<Map<String,Object>> updateUserInfo(@Valid PersonalParam personalParam
                                                   ){
        Map<String,Object> map  = new HashMap<>();
        agoPersonalService.updateUserInfo(personalParam);
        return ResultVOUtil.success(map);
    }

    /**
     * @param userId dynamicId
     * @return
     * @desc 动态点赞
     */
    @PostMapping("/dynamic/praise")
    public ResultVO<Map<String,Object>> dynamicPraise(
            @RequestParam(value = "dynamicId") String dynamicId,
            @RequestParam(value = "userId") String userId){
        agoPersonalService.dynamicPraise(userId,dynamicId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }



}
