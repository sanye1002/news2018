package cn.popo.news.core.service.impl;

import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.UserForm;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.Encrypt;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/29 14:17
 * @Desc    用户-逻辑控制中心
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    /**
     * 查找用户根据状态等
     */
    @Override
    public Page<User> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable, String userType, Integer status) {
        return userRepository.findAllByUserTypeAndStatusAndShowStatus(pageable, userType, status, 1);
    }

    /**
     * 根据id查找用户
     */
    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    /**
     * 保存更新用户资料
     */
    @Override
    public void saveUser(UserForm userForm) {
        System.out.println(userForm.getUserId());
        User user = new User();
        if (userForm.getUserId() != "") {
            user = userRepository.findOne(userForm.getUserId());
            user.setUpdateDate(GetTimeUtil.getTime());
        }
        BeanUtils.copyProperties(userForm, user);
        if (userForm.getUserId().equals("")) {
            user.setUserId(KeyUtil.genUniqueKey());
            user.setUserType("0");
            user.setCreateDate(GetTimeUtil.getTime());
        }



        userRepository.save(user);
    }


    /**
     * 根据id删除用户
     */
    @Override
    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }

    /**
     *
     * @param phone
     * @return
     */
    @Override
    public User findByPhoneAndStatus(String phone) {
        User user = userRepository.findByPhoneAndStatus(phone,1);
        if (user==null){
            return null;
        }else{
            return  user;
        }


    }

    @Override
    public Map<String, Object> login(HttpServletRequest request, String phone, String password) {
        Map<String, Object> map = new HashMap<>();

        User userInfo = userRepository.findByPhoneAndStatus(phone,1);
        if (userInfo == null) {
            map.put("code", 100);
            map.put("message", "用户名不存在");
            return map;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            subject.getSession().setTimeout(-10001); //session永不超时
            if (subject.isAuthenticated()) {
                Session session = subject.getSession();
                User user = userRepository.findByPhoneAndPassword(phone, Encrypt.md5(password));
                session.setAttribute("user", user);
                User login = (User) session.getAttribute("user");
                map.put("code", 0);
                map.put("message", "登录成功！使用愉快！");
                request.getSession().setAttribute("user", login);
                return map;
            }
            map.put("code", 100);
            map.put("message", "登录失败!");
            return map;

        } catch (DisabledAccountException e) {
            map.put("code", 100);
            map.put("message", e.getMessage());
            return map;
        } catch (Exception e) {
            map.put("code", 100);
            map.put("message", "登录失败!");
            return map;
        }
    }

    @Override
    public User saveByUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Map<String, Object> findPhoneIsEmpty(String phone) {
        List<User> userInfoList = userRepository.findAllByPhone(phone);
        Map<String, Object> map = new HashMap<>();
        if (userInfoList.isEmpty()) {
            map.put("code", 0);
            map.put("message", "手机号码可以使用！");
            return map;
        }
        map.put("code", 100);
        map.put("message", "手机号码已存在！");
        return map;
    }

    @Override
    public Map<String, Object> findAllByPhone(String phone) {
        List<User> userInfoList = userRepository.findAllByPhone(phone);
        Map<String, Object> map = new HashMap<>();
        if (userInfoList.isEmpty()) {
            map.put("code", 0);
            map.put("message", "手机号码可以使用！");
            return map;
        }
        map.put("code", 100);
        map.put("message", "手机号码已存在！");
        return map;
    }


}
