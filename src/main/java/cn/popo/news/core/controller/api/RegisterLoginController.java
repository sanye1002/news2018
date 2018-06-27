package cn.popo.news.core.controller.api;

import cn.popo.news.common.controller.BasicController;
import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.exception.APIException;
import cn.popo.news.core.service.api.RegisterLoginService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 4:24
 * @Description description
 */

@Api(tags = {"用户登录注册"})
@RestController
@RequestMapping(value = "/api/user")
public class RegisterLoginController {

    @Autowired
    private RegisterLoginService loginService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    @PostMapping("/loginAndPhonePassword")
    @ApiOperation(value = "根据用户手机和密码登录")
    public ResultVO<Map<String, Object>> loginByPhoneAndPassword(HttpServletResponse response,
                                                                 @RequestParam(value = "phone") @ApiParam(value = "用户手机号码", required = true) String phone,
                                                                 @RequestParam(value = "password") @ApiParam(value = "用户密码", required = true) String password) {
        if (phone == "") {
            return ResultVOUtil.error(100, "手机号码为空！");
        }
        if (password == "") {
            return ResultVOUtil.error(100, "密码为空，请输入。。。！");
        }
        return loginService.loginByPhoneAndPassword(response, phone, password);
    }

    @PostMapping("/valid")
    @ApiOperation(value = "判断是否登录", notes = "判断用户是否登录，如果已登录，返回对象，否则重新登录")
    public ResultVO<Map<String, Object>> test(HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            map.put("userVO", userSessionUtil.getUserByCookie(request, response));
        } catch (APIException a) {
            return ResultVOUtil.error(ResultEnum.USER_FAILURE.getCode(), ResultEnum.USER_FAILURE.getMessage());
        }
        return ResultVOUtil.success(map);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出用户", notes = "清除cookie值，和redis缓存！")
    public ResultVO<Map<String, Object>> logout(HttpServletResponse response,
                                                HttpServletRequest request,
                                                @RequestParam(value = "userId") @ApiParam(value = "用户id唯一标示", required = true) String userId) {
        return loginService.logout(request, response, userId);
    }

    @PostMapping("/loginByPhoneAndCode")
    @ApiOperation(value = "根据手机号加验证码登录", notes = "根据手机号加验证码登录,登录成功 在从cookie中添加user-cookie-token和user-cookie-id值")
    public ResultVO<Map<String, Object>> loginByPhoneAndCode(HttpServletResponse response,
                                                             HttpServletRequest request,
                                                             @RequestParam(value = "phone") @ApiParam(value = "手机号码", required = true) String phone,
                                                             @RequestParam(value = "code") @ApiParam(value = "手机验证码", required = true) String code) {
        return loginService.loginByPhoneAndCode(request, response, phone, code);
    }

    @PostMapping("/checkPhoneToCode")
    @ApiOperation(value = "验证手机号码发送验证码", notes = "验证手机号码和验证类型")
    public ResultVO<Map<String, Object>> checkPhone(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @RequestParam(value = "phone") @ApiParam(value = "手机号码", required = true) String phone,
                                                    @RequestParam(value = "type") @ApiParam(value = "操作类型（0 登录或者找回密码，1 注册）", required = true) Integer type) {
        return loginService.checkPhone(request, response, phone, type);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户", notes = "注册用户，需传入手机号码、验证码、密码")
    public ResultVO<Map<String, Object>> register(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "phone") @ApiParam(value = "手机号码", required = true) String phone,
                                                  @RequestParam(value = "code") @ApiParam(value = "验证码", required = true) String code,
                                                  @RequestParam(value = "password") @ApiParam(value = "手机密码", required = true) String password) {
        return loginService.register(request, response, phone, code, password);
    }
    @PostMapping("/oauthQQ")
    @ApiOperation(value = "QQ第三方登录", notes = "QQ第三方登录，QQAccessToken、QQOpenID，来获取用户信息")
    public ResultVO<Map<String, Object>> oauthQQ(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "QQAccessToken") @ApiParam(value = "QQAccessToken", required = true) String QQAccessToken,
                                                  @RequestParam(value = "QQOpenID") @ApiParam(value = "QQOpenID", required = true) String QQOpenID) {
            return loginService.oauthQQ(request, response,QQAccessToken, QQOpenID);
    }

    @PostMapping("/forgetPassword")
    @ApiOperation(value = "忘记密码", notes = "忘记密码，需传入手机号码、验证码、密码")
    public ResultVO<Map<String, Object>> forgetPassword(HttpServletRequest request, HttpServletResponse response,
                                                        @RequestParam(value = "phone") @ApiParam(value = "手机号码", required = true) String phone,
                                                        @RequestParam(value = "code") @ApiParam(value = "验证码", required = true) String code,
                                                        @RequestParam(value = "password") @ApiParam(value = "手机密码", required = true) String password) {
        return loginService.forgetPassword(request, response, phone, code, password);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码，用户id、验证码、新旧密码")
    public ResultVO<Map<String, Object>> updatePassword(HttpServletRequest request, HttpServletResponse response,
                                                        @RequestParam(value = "userId") @ApiParam(value = "用户id", required = true) String userId,
                                                        @RequestParam(value = "code") @ApiParam(value = "验证码", required = true) String code,
                                                        @RequestParam(value = "newPassword") @ApiParam(value = "新密码", required = true) String newPassword,
                                                        @RequestParam(value = "oldPassword") @ApiParam(value = "旧密码", required = true) String oldPassword) {
        return loginService.updatePassword(request, response, userId, oldPassword, newPassword);
    }

    @PostMapping("/updatePhone")
    @ApiOperation(value = "修改用户手机号码", notes = "新手机号码、验证码、旧手机号码")
    public ResultVO<Map<String, Object>> updatePhone(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(value = "code") @ApiParam(value = "验证码", required = true) String code,
                                                     @RequestParam(value = "newPhone") @ApiParam(value = "新手机号码", required = true) String newPhone,
                                                     @RequestParam(value = "oldPhone") @ApiParam(value = "旧手机号码", required = true) String oldPhone) {
        return loginService.updatePhone(request, response, oldPhone, newPhone, code);
    }

    @PostMapping("/checkCode")
    @ApiOperation(value = "验证验证码", notes = "验证验证码是否正确！")
    public ResultVO<Map<String, Object>> checkCode(HttpServletRequest request,

                                                   @RequestParam(value = "code") @ApiParam(value = "验证码", required = true) String code) {
        if (loginService.checkCode(request, code)) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(401, "验证码错误！");
    }

    @PostMapping("/checkPassword")
    @ApiOperation(value = "验证原密码", notes = "修改密码的时候判断当前输入的密码是否和原密码相同")
    public ResultVO<Map<String, Object>> checkPassword(HttpServletRequest request,
                                                       @RequestParam(value = "userId") @ApiParam(value = "用户ID", required = true) String userId,
                                                       @RequestParam(value = "password") @ApiParam(value = "要判断的密码", required = true) String password) {
        if (loginService.checkPassword(userId, password)) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(401, "原密码错误！");
    }

    @PostMapping("/checkPhone")
    @ApiOperation(value = "验证新手机号码", notes = "修改手机号码的时候判断当前修改新手机号码是否已注册")
    public ResultVO<Map<String, Object>> checkPhone(@RequestParam(value = "oldPhone") @ApiParam(value = "要判断的原手机号码", required = true) String oldPhone) {
        if (loginService.checkPhone(oldPhone)) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(401, "手机号码已注册！");
    }


}
