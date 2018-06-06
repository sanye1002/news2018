package cn.popo.news.core.controller.oa;


import cn.popo.news.core.dto.AccountDTO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.UserForm;
import cn.popo.news.core.service.RolePermissionService;
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

    @Autowired
    private RolePermissionService rolePermissionService;

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
        return new ModelAndView("pages/recoverPassword", map);
    }

    @GetMapping("/register-user")
    public ModelAndView registerUser(Map<String, Object> map) {
        return new ModelAndView("pages/registerUser", map);
    }

    @PostMapping("/checkPhone")
    @ResponseBody
    public ResultVO<Map<String, Object>> checkPhone(HttpServletRequest request,
                                                    @RequestParam(value = "phone") String phone,
                                                    @RequestParam(value = "type") Integer type) {
        HttpSession session = request.getSession();
        User userInfo = userService.findByPhoneAndStatus(phone);
        Map<String, Object> map = new HashMap<>();
        if (type==1){
            if (userInfo == null) {
                return ResultVOUtil.error(100, "查无用户");
            }
            map.put("message", "你好，" + userInfo.getName() + ",验证码已发送！");
        }else {
            map.put("message", "你好,验证码已发送！");
        }


        session.setMaxInactiveInterval(300);
        Integer code = RandomUtils.getRandom4Font();
        if (SendMessageUtil.sendCodeMessage(phone, code + "")) {
            session.setAttribute("code", "" + code);

        } else {
            return ResultVOUtil.error(100, "短信发送失败，请联系管理员！");
        }
        return ResultVOUtil.success(map);


    }

    @PostMapping("/register/user")
    @ResponseBody
    public ResultVO<Map<String, Object>> registerUser(@Valid UserForm userForm,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        userForm.setStatus(1);
        userForm.setShowStatus(1);
        if (rolePermissionService.findRoleByName("文章编辑")==null){
            return ResultVOUtil.error(100,"遇到来着外星的攻击~ 攻城狮正在处理~");
        }
        userForm.setRoleId(rolePermissionService.findRoleByName("文章编辑").getId());
        userForm.setUserId("");
        userService.saveUser(userForm);
        return ResultVOUtil.success();
    }

    @PostMapping("/checkCode")
    @ResponseBody
    public ResultVO<Map<String, Object>> checkCode(HttpServletRequest request, @RequestParam(value = "code") String code) {
        HttpSession session = request.getSession();
        String messageCode = (String) session.getAttribute("code");
        Map<String, Object> map = new HashMap<>();
        if (messageCode.equals(code)) {
            map.put("message", "请设置以下内容~");
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
