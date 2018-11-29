package cn.popo.news.core.controller.app;

import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/app/login")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/qq")
    public ResultVO<Map<String, Object>> loginQQ(HttpServletResponse response,
                                                                 @RequestParam(value = "openId")  String openId,
                                                                 @RequestParam(value = "avatar")  String avatar,
                                                                 @RequestParam(value = "name") String name) {
        if(openId==null){
         return ResultVOUtil.error(11,"登录失败");
        }

        return userService.findUserByQQ(openId,name,avatar,response);
    }

    @PostMapping("/weChat")
    public ResultVO<Map<String, Object>> loginWeChat(HttpServletResponse response,
                                                                 @RequestParam(value = "openId")  String openId,
                                                                 @RequestParam(value = "avatar")  String avatar,
                                                                 @RequestParam(value = "name") String name) {
        if(openId==null){
            return ResultVOUtil.error(11,"登录失败");
        }

        return userService.findUserByWeChatId(openId,name,avatar,response);
    }
}
