package cn.popo.news.core.controller.app;


import cn.popo.news.core.entity.app.MineSections;
import cn.popo.news.core.service.app.MineService;
import cn.popo.news.core.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = {"app Mine 列表"})
@RestController
@RequestMapping(value = "/app/mine")
@Slf4j
public class MineController {

    @Autowired
    private MineService mineService;


    @GetMapping("/sections")
    @ApiOperation(value = "个人中心列表")
    public ResultVO<Map<String,Object>> getMineSections(){
        return mineService.getMineList();
    }

}
