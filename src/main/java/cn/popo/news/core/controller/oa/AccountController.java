package cn.popo.news.core.controller.oa;


import cn.popo.news.core.dto.AccountDTO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.*;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 超级战机
 * 2018-04-14 15:18
 */
@Controller
@RequestMapping("/")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(Map<String, Object> map) {
        if (ShiroGetSession.getUser().getPhone() != null) {
            return new ModelAndView("redirect:/oa/user/index.html", map);
        }
        return new ModelAndView("view/login", map);
    }

    @GetMapping("/")
    public ModelAndView loginPage(Map<String, Object> map) {
        if (ShiroGetSession.getUser().getPhone() != null) {
            return new ModelAndView("redirect:/oa/user/index.html", map);
        }
        return new ModelAndView("redirect:/login.html", map);
    }

    @PostMapping("/account/sign-in")
    @ResponseBody
    public ResultVO<Map<String, Object>> signIn(@Valid AccountDTO accountDTO,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        map = userService.login(request, accountDTO.getPhone(), accountDTO.getPassword());

        return ResultVOUtil.success(map);
    }

    @GetMapping("/login-out")
    public ModelAndView loginOut(Map<String, Object> map) {
        ShiroGetSession.removeUser();
        return new ModelAndView("redirect:/login.html", map);
    }

    @GetMapping("/recover-password")
    public ModelAndView recoverPassword(Map<String, Object> map) {
        return new ModelAndView("view/recoverPassword", map);
    }

    @PostMapping("/checkPhone")
    @ResponseBody
    public ResultVO<Map<String, Object>> checkPhone(HttpServletRequest request, @RequestParam(value = "phone") String phone) {
        HttpSession session = request.getSession();
        User userInfo = userService.findByPhoneAndStatus(phone);
        Map<String, Object> map = new HashMap<>();
        if (userInfo == null) {
            return ResultVOUtil.error(100, "查无用户");
        } else {

            session.setMaxInactiveInterval(300);
            Integer code = RandomUtils.getRandom4Font();
            if (SendMessageUtil.sendCodeMessage(phone, code + "")) {
                session.setAttribute("code", "" + code);
                map.put("message", "你好，" + userInfo.getName() + ",验证码已发送！");
            } else {
                return ResultVOUtil.error(100, "短信发送失败，请联系管理员！");
            }
            return ResultVOUtil.success(map);
        }

    }

    @PostMapping("/checkCode")
    @ResponseBody
    public ResultVO<Map<String, Object>> checkCode(HttpServletRequest request, @RequestParam(value = "code") String code) {
        HttpSession session = request.getSession();
        String messageCode = (String) session.getAttribute("code");
        Map<String, Object> map = new HashMap<>();
        if (messageCode.equals(code)) {
            map.put("message", "请设置你的密码");
            session.removeAttribute("code");
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(100, "验证码错误");
        }

    }

    @PostMapping("/setPassword")
    @ResponseBody
    public ResultVO<Map<String, Object>> setPassword(@RequestParam(value = "password") String password,
                                                     @RequestParam(value = "phone") String phone) {
        User userInfo = userService.findByPhoneAndStatus(phone);
        userInfo.setPassword(Encrypt.md5(password));
        if (userInfo == null) {
            return ResultVOUtil.error(100, "查无用户");
        }
        userService.saveByUser(userInfo);
        return ResultVOUtil.success();
    }


}
