package cn.popo.news.core.shiro;

import cn.popo.news.core.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by 超级战机
 * 2018-04-14 16:18
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        Object tokenCredentials = Encrypt.md5(String.valueOf(authcToken.getPassword()));
        Object accountCredentials = getCredentials(info);
        if(!accountCredentials.equals(tokenCredentials)){
            throw new DisabledAccountException("密码不正确！");
        }
        return true;
    }
}
