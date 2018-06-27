package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.constant.CookieConstant;
import cn.popo.news.common.constant.RedisConstant;
import cn.popo.news.common.utils.RedisOperator;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.exception.APIException;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.RegisterLoginService;
import cn.popo.news.core.utils.*;
import cn.popo.news.core.vo.ResultVO;
import cn.popo.news.core.vo.UserVO;
import com.google.gson.Gson;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 5:07
 * @Description description
 */
@Service
public class RegisterLoginServiceImpl implements RegisterLoginService {


    @Autowired
    private RedisOperator redis;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionUtil userSessionUtil;


    public UserVO setUserRedisSessionTokenAndCookieSession(HttpServletResponse response, User userParam) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(RedisConstant.TOKEN_PREFIX + userParam.getUserId(), uniqueToken, RedisConstant.EXPIRE);

        CookieUtil.set(response, CookieConstant.USER_ID, userParam.getUserId(), CookieConstant.EXPIRE);
        CookieUtil.set(response, CookieConstant.TOKEN, uniqueToken, CookieConstant.EXPIRE);
        String jsonUser = new Gson().toJson(userParam);

        redis.set(RedisConstant.VO_PREFIX + userParam.getUserId(), jsonUser, RedisConstant.EXPIRE);
        UserVO userVO = UserVO.builder().build();
        BeanUtils.copyProperties(userParam, userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }

    @Override
    public ResultVO<Map<String, Object>> loginByPhoneAndPassword(HttpServletResponse response, String phone, String password) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            return ResultVOUtil.error(100, "用户不存在！");
        }
        Map<String, Object> map = new HashMap<>();
        if (user.getPassword().equals(Encrypt.md5(password))) {
            if (user.getStatus() == 1) {
                map.put("message", "登录成功");
                map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, user));
                return ResultVOUtil.success(map);
            } else {
                return ResultVOUtil.error(500, "用户已注销！");
            }
        } else {
            return ResultVOUtil.error(100, "密码输入错误！");
        }

    }

    @Override
    public ResultVO<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response, String userId) {
        //1.查询redis
        //2.清除redis
        if (!redis.get(RedisConstant.TOKEN_PREFIX + userId).equals("")) {
            redis.del(RedisConstant.TOKEN_PREFIX + userId);
        }
        if (!redis.get(RedisConstant.VO_PREFIX + userId).equals("")) {
            redis.del(RedisConstant.VO_PREFIX + userId);
        }
        //3.查询cookie
        //4.清除cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
            CookieUtil.set(response, CookieConstant.USER_ID, null, 0);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "成功退出！");
        return ResultVOUtil.success(map);
    }

    @Override
    public ResultVO<Map<String, Object>> loginByPhoneAndCode(HttpServletRequest request, HttpServletResponse response, String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        String messageCode = (String) session.getAttribute("code");
        if (messageCode.equals(code)) {
            session.removeAttribute("code");
            User user = userRepository.findByPhoneAndStatus(phone, 1);
            if (user == null) {
                return ResultVOUtil.error(100, "查无用户");
            }
            map.put("message", "登录成功");
            map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, user));
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(100, "验证码错误");
        }


    }


    /**
     * 验证手机号码
     * type 0 登录找回密码   1 注册
     */

    @Override
    public ResultVO<Map<String, Object>> checkPhone(HttpServletRequest request, HttpServletResponse response, String phone, Integer type) {
        Map<String, Object> map = new HashMap<>();

        if (type == 0) {
            User user = userRepository.findByPhoneAndStatus(phone, 1);
            if (user == null) {
                return ResultVOUtil.error(402, "查无用户");
            }
        }
        Integer code = RandomUtils.getRandom4Font();
        if (SendMessageUtil.sendCodeMessage(phone, code + "")) {
            CookieUtil.set(response, CookieConstant.CODE_YZM, code + "", 300);
            map.put("message", "验证码发送成功！");
        } else {
            return ResultVOUtil.error(100, "短信发送失败，请稍后重试！");
        }
        return ResultVOUtil.success(map);
    }

    @Override
    public ResultVO<Map<String, Object>> register(HttpServletRequest request, HttpServletResponse response, String phone, String code, String password) {
        Map<String, Object> map = new HashMap<>();
        if (userRepository.findByPhone(phone) != null) {
            return ResultVOUtil.error(403, "用户已存在~");
        }
        if (checkCode(request, code)) {
            User user = new User();
            user.setPassword(Encrypt.md5(password));
            user.setPhone(phone);
            user.setNikeName("用户" + KeyUtil.genUniqueKey());
            user.setAvatar("");
            user.setCreateDate(GetTimeUtil.getTime());
            user.setAvatar("/read/img/user/model.png");
            user.setUpdateDate(GetTimeUtil.getTime());
            user.setUserType("1");
            user.setUserId(KeyUtil.genUniqueKey());
            user.setStatus(1);
            User userParam = userRepository.save(user);
            map.put("message", "登录成功");
            map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, userParam));
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.error(401, "验证码错误！");
    }

    @Override
    public ResultVO<Map<String, Object>> forgetPassword(HttpServletRequest request, HttpServletResponse response, String phone, String code, String password) {
        Map<String, Object> map = new HashMap<>();
        if (checkCode(request, code)) {
            User user = userRepository.findByPhoneAndStatus(phone, 1);
            if (user == null) {
                return ResultVOUtil.error(402, "查无用户");
            }
            user.setPassword(Encrypt.md5(password));
            map.put("message", "密码修改成功~");
            map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, userRepository.save(user)));
            return ResultVOUtil.success(map);

        }
        return ResultVOUtil.error(401, "验证码错误！");
    }

    @Override
    public ResultVO<Map<String, Object>> updatePassword(HttpServletRequest request, HttpServletResponse response, String userId, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            ResultVOUtil.error(ResultEnum.USER_FAILURE.getCode(), ResultEnum.USER_FAILURE.getMessage());
        }
        if (checkPassword(userId, oldPassword)) {
            User user = userRepository.findOne(userId);
            if (user == null) {
                return ResultVOUtil.error(402, "查无用户");
            }
            user.setPassword(Encrypt.md5(newPassword));
            map.put("message", "修改成功");
            map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, userRepository.save(user)));
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.error(401, "原密码错误！");
    }

    @Override
    public ResultVO<Map<String, Object>> updatePhone(HttpServletRequest request, HttpServletResponse response, String oldPhone, String newPhone, String code) {
        Map<String, Object> map = new HashMap<>();
        if (userSessionUtil.verifyLoginStatus(request, response)) {
            if (checkPhone(newPhone)) {
                User user = userSessionUtil.getUserByCookie(request, response);
                if (user.getPhone().equals(oldPhone)) {
                    if (checkCode(request, code)) {
                        user.setPhone(newPhone);
                        map.put("message", "修改成功");
                        map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, userRepository.save(user)));
                        return ResultVOUtil.success(map);
                    }
                    return ResultVOUtil.error(407, "验证码错误！");
                }
                return ResultVOUtil.error(401, "原手机号码错误！");
            }
            return ResultVOUtil.error(401, "手机号码已注册！");
        }
        return ResultVOUtil.error(ResultEnum.USER_FAILURE.getCode(), ResultEnum.USER_FAILURE.getMessage());
    }

    @Override
    public Boolean checkCode(HttpServletRequest request, String code) {
        Cookie cookie = CookieUtil.get(request, CookieConstant.CODE_YZM);
        String messageCode = (String) cookie.getValue();
        if (messageCode.equals(code)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(Encrypt.md5(password))) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean QQLogin(String accessToken, String openID) {
        User user = userRepository.findByQQOpenIDAndQQAccessToken(accessToken, openID);
        if (user == null) {
            UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
            user = new User();
            user.setQQAccessToken(accessToken);
            user.setQQOpenID(openID);
            UserInfoBean userInfo = null;
            try {
                userInfo = qzoneUserInfo.getUserInfo();
            } catch (QQConnectException e) {

                e.printStackTrace();
                return false;
            }
            String nickName = userInfo.getNickname();
            while (!checkNickName(nickName)) {
                nickName = userInfo.getNickname() + (int) (1 + Math.random() * (10 - 1 + 1));
            }
            user.setNikeName(nickName);
            user.setName(nickName);
            user.setAvatar(userInfo.getAvatar().getAvatarURL50());
            user.setCreateDate(GetTimeUtil.getTime());
            user.setUpdateDate(GetTimeUtil.getTime());
            user.setUserType("1");
            user.setUserId(KeyUtil.genUniqueKey());
            user.setStatus(1);
            userRepository.save(user);
        }
        return true;
    }

    @Override
    public ResultVO<Map<String, Object>> oauthQQ(HttpServletRequest request, HttpServletResponse response, String QQAccessToken, String QQOpenID) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findByQQOpenIDAndQQAccessToken(QQOpenID,QQAccessToken);
        if (user==null){
           return ResultVOUtil.error(403,"授权失效~");
        }
        map.put("message", "登录成功");
        map.put("userVO", this.setUserRedisSessionTokenAndCookieSession(response, user));
        return ResultVOUtil.success(map);
    }

    private Boolean checkNickName(String nickName){
        if (userRepository.findAllByNikeName(nickName) == null){
            return true;
        }
        return false;
    }


}
