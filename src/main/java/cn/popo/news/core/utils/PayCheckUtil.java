package cn.popo.news.core.utils;

import cn.popo.news.core.entity.common.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-03 下午 6:35
 * @Description description
 */
@Slf4j
public class PayCheckUtil {
    public static Boolean check(){
        User userInfo = ShiroGetSession.getUser();
        if (userInfo.getBankUserName()==null||userInfo.getBankType()==null||userInfo.getBankCardNumber()==null||userInfo.getAliPay()==null){
            return false;
        }
        if (MobileExactUtil.isMobileExact(userInfo.getPhone())){
            return true;
        }
        return false;

    }
}
