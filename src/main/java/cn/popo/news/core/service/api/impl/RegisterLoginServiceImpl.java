package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.constant.CookieConstant;
import cn.popo.news.common.constant.RedisConstant;
import cn.popo.news.common.utils.RedisOperator;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.param.UserParam;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.RegisterLoginService;
import cn.popo.news.core.utils.CookieUtil;
import cn.popo.news.core.utils.Encrypt;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import cn.popo.news.core.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class RegisterLoginServiceImpl implements RegisterLoginService{


    @Autowired
    private RedisOperator redis;

    @Autowired
    private UserRepository userRepository;



    public UserVO setUserRedisSessionTokenAndCookieSession(HttpServletResponse response, User userParam) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(RedisConstant.TOKEN_PREFIX  + userParam.getUserId(), uniqueToken, RedisConstant.EXPIRE);
        CookieUtil.set(response,CookieConstant.USER_ID,userParam.getUserId(),CookieConstant.EXPIRE);
        CookieUtil.set(response, CookieConstant.TOKEN,uniqueToken,CookieConstant.EXPIRE);
        UserVO userVO = UserVO.builder().build();
        BeanUtils.copyProperties(userParam, userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }

    @Override
    public ResultVO<Map<String, Object>> loginByPhoneAndPassword(HttpServletResponse response, String phone, String password) {
           User user =  userRepository.findByPhone(phone);
           if (user==null){
               return ResultVOUtil.error(100,"用户不存在！");
           }
            Map<String, Object> map = new HashMap<>();
           if (user.getPassword().equals(Encrypt.md5(password))){
               if (user.getStatus()==1){
                    map.put("message","登录成功");
                    map.put("userVO",this.setUserRedisSessionTokenAndCookieSession(response,user));
                    return ResultVOUtil.success(map);
               }else {
                   return ResultVOUtil.error(500,"用户已注销！");
               }
           }else {
               return ResultVOUtil.error(100,"密码输入错误！");
           }

    }

    @Override
    public ResultVO<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response,String userId) {
        //1.查询redis
        //2.清除redis
        if ( !redis.get(RedisConstant.TOKEN_PREFIX+userId).equals("")){
            redis.del(RedisConstant.TOKEN_PREFIX+userId);
        }
        //3.查询cookie
        //4.清除cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN+userId);
        if (cookie!=null){
            CookieUtil.set(response, CookieConstant.TOKEN+userId, null, 0);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message","成功退出！");
        return ResultVOUtil.success(map);
    }


}
