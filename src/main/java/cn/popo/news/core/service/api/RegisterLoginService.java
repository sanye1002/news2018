package cn.popo.news.core.service.api;

import cn.popo.news.core.vo.ResultVO;
import cn.popo.news.core.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 5:07
 * @Description 登录注册
 */
public interface RegisterLoginService {
    /**
     * 根据手机号码+密码
     * @param response
     * @param phone
     * @param password
     * @return
     */
    ResultVO<Map<String,Object>> loginByPhoneAndPassword(HttpServletResponse response, String phone, String password);

    /**
     * 注销
     * @param request
     * @param userId
     * @param response
     * @return
     */
    ResultVO<Map<String,Object>> logout(HttpServletRequest request,HttpServletResponse response,String userId);



}
