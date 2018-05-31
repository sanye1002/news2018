package cn.popo.news.common.controller;

import cn.popo.news.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 4:18
 * @Description description
 */
@RestController
public class BasicController {
    @Autowired
    private RedisOperator redisOperator;
    public static final String USER_REDIS_SESSION = "USER_REDIS_SESSION";

}
