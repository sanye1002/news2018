package cn.popo.news.core.controller.api;

import cn.popo.news.common.controller.BasicController;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.exception.APIException;
import cn.popo.news.core.service.api.RegisterLoginService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 4:24
 * @Description description
 */

@RestController
@RequestMapping("/api/user")
public class RegisterLoginController {

    @Autowired
    private RegisterLoginService loginService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    @PostMapping("/loginAndPhonePassword")
    public ResultVO<Map<String,Object>> loginByPhoneAndPassword(HttpServletResponse response,
                                                                 @RequestParam(value = "phone")String phone,
                                                                 @RequestParam(value = "password")String password){
        if (phone==""){
            return ResultVOUtil.error(100,"手机号码为空！");
        }
        if (password==""){
            return ResultVOUtil.error(100,"密码为空，请输入。。。！");
        }
        return loginService.loginByPhoneAndPassword(response, phone, password);
    }
    @PostMapping("/test")
    public ResultVO<Map<String,Object>> test(HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("message",""+userSessionUtil.verifyLoginStatus(request, response));
        try {
            map.put("userVO",userSessionUtil.getUserByCookie(request, response));
        }catch (APIException a){
            return ResultVOUtil.error(ResultEnum.USER_FAILURE.getCode(),ResultEnum.USER_FAILURE.getMessage());
        }
        return ResultVOUtil.success(map);
    }
    @PostMapping("/logout")
    public ResultVO<Map<String,Object>> logout(HttpServletResponse response,
                                               HttpServletRequest request,
                                               @RequestParam(value = "userId")String userId){
        return loginService.logout(request, response, userId);
    }

}
