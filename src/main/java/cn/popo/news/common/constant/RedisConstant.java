package cn.popo.news.common.constant;

/**
 * redis常量
 * Created by 廖师兄
 * 2017-07-30 16:22
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "user-redis-session:";

    Integer EXPIRE = 604800; //1周
}
