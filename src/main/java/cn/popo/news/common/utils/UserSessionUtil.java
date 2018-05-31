package cn.popo.news.common.utils;

import cn.popo.news.common.constant.CookieConstant;
import cn.popo.news.common.constant.RedisConstant;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.utils.CookieUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 6:50
 * @Description description
 */
@Component
@Slf4j
public class UserSessionUtil {
    @Autowired
    private RedisOperator redis;

    /**
     * 检测用户是否登录
     * @param request
     * @param response
     * @return
     */
    public Boolean verifyLoginStatus(HttpServletRequest request, HttpServletResponse response){
        //1.从cookie中获取userToken
        Cookie userIdCookie = CookieUtil.get(request, CookieConstant.USER_ID);
        Cookie userTokenCookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (userIdCookie==null){
            return false;
        }
        if (userTokenCookie==null){
            return false;
        }
        String userId=userIdCookie.getValue();
        String userToken =userTokenCookie.getValue();

        if (userId==null&&userToken==null){
            return false;
        }
        //2.判断userToken是否等于redis
        String redisToken = redis.get(RedisConstant.TOKEN_PREFIX+userId);

        if (!redisToken.equals(userToken)){
            return false;
        }
        return true;
    }
}
