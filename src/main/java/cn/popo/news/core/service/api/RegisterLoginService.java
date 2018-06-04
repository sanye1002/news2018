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

    /**
     *  按验证码登录
     * @param request
     * @param phone
     * @param code
     * @return
     */
    ResultVO<Map<String,Object>> loginByPhoneAndCode(HttpServletRequest request,HttpServletResponse response,String phone,String code);

    /**
     *  验证手机号码
     * @param request
     * @param phone
     * @param type 0 登录   1 注册
     * @return
     */
    ResultVO<Map<String,Object>> checkPhone(HttpServletRequest request,String phone,Integer type);


    /**
     * 注册
     * @param request
     * @param response
     * @param phone
     * @param code
     * @param password
     * @return
     */
    ResultVO<Map<String,Object>> register(HttpServletRequest request,HttpServletResponse response,String phone,String code,String password);


    /**
     * 忘记密码
     * @param request
     * @param response
     * @param phone
     * @param code
     * @param password
     * @return
     */
    ResultVO<Map<String,Object>> forgetPassword(HttpServletRequest request,HttpServletResponse response,String phone,String code,String password);


    /**
     * 修改密码
     * @param request
     * @param response
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ResultVO<Map<String,Object>> updatePassword(HttpServletRequest request,HttpServletResponse response,String userId,String oldPassword,String newPassword);


    /**
     *
     * @param request
     * @param code
     * @return
     */
    Boolean checkCode(HttpServletRequest request,String code);

    /**
     *
     * @param userId
     * @param password
     * @return
     */
    Boolean checkPassword(String userId,String password);







}
