package cn.popo.news.core.controller;

import cn.popo.news.common.constant.UrlConstant;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.utils.ImgVOUtil;
import cn.popo.news.core.vo.ImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-16 下午 6:38
 */
@Controller
@RequestMapping("/layer")
public class LayuiPhotos {

    @Autowired
    private ArticleService articleService;
    @GetMapping("/article")
    @ResponseBody
    public ImgVO<List<Map<String,Object>>> photos(@RequestParam(value = "id")String id,
                                                  @RequestParam(value = "type")Integer type){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map =articleService.findOneArticleSomeImg(id);
        List<String> contentList= (List<String>) map.get("contentList");
        List<String> imgList= (List<String>) map.get("imgList");
        if (!imgList.isEmpty()){
            if(type == ResultEnum.PLATFORM_BOOS_NULL.getCode()){
                for (int i=0;i<imgList.size();i++){
                    Map<String,Object> result = new HashMap<>();
                    result.put("alt",contentList.get(i));
                    result.put("pid",""+i);
                    result.put("src",UrlConstant.URL+imgList.get(i));
                    result.put("thumb",UrlConstant.URL+imgList.get(i));
                    list.add(result);
                }
            }else {
                for (int i=0;i<imgList.size();i++){
                    Map<String,Object> result = new HashMap<>();
                    result.put("alt",i);
                    result.put("pid",""+i);
                    result.put("src",UrlConstant.URL+imgList.get(i));
                    result.put("thumb",UrlConstant.URL+imgList.get(i));
                    list.add(result);
                }
            }
        }
        return ImgVOUtil.success(1,list);
    }

}
