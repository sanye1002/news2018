package cn.popo.news.core.controller.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.AttentionVO;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoAttentionService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {

    @Autowired
    private AgoAttentionService agoAttentionService;

    @Autowired
    private AgoArticleService agoArticleService;

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
        map.put("attention", pageDTO);
        map.put("size", size);
        map.put("currentPage", page);
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
        PageDTO<AttentionVO> pageDTO = agoAttentionService.findAllAttention(pageRequest,"1527582639993");
        map.put("fans", pageDTO);
        map.put("size", size);
        map.put("currentPage", page);
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
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByUserCollect(pageRequest,"1527582639993",typeId);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("pageContent", pageDTO);
        return ResultVOUtil.success(map);
    }

}
