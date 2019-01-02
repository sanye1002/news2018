package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.AuthorDTO;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.UserForm;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.service.api.RegisterLoginService;
import cn.popo.news.core.service.api.impl.RegisterLoginServiceImpl;
import cn.popo.news.core.utils.*;
import cn.popo.news.core.vo.ResultVO;
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
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @Date 2018/5/29 14:17
 * @Desc 用户-逻辑控制中心
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private RegisterLoginServiceImpl registerLoginService;

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
            user.setUpdateDate(GetTimeUtil.getTime());
            List<Classify> classifies = classifyService.findAllClassify();
            if (!classifies.isEmpty()) {
                user.setPrefList(PrefListUtil.set(classifies.stream().map(e ->
                        e.getId()
                ).collect(Collectors.toList())));
            }

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
     * @param phone
     * @return
     */
    @Override
    public User findByPhoneAndStatus(String phone) {
        User user = userRepository.findByPhoneAndStatus(phone, 1);
        if (user == null) {
            return null;
        } else {
            return user;
        }


    }

    /**
     * 通过userId查找用户
     * @param userId id
     * @return user
     */
    @Override
    public User findByUserIdAndState(String userId) {
        User user = userRepository.findByUserIdAndStatus(userId, 1);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public Map<String, Object> login(HttpServletRequest request, String phone, String password) {
        Map<String, Object> map = new HashMap<>();

        User userInfo = userRepository.findByPhoneAndStatus(phone, 1);
        if (userInfo == null) {
            map.put("code", 100);
            map.put("message", "用户名不存在");
            return map;
        }
        if (!userInfo.getUserType().equals("0")) {
            map.put("code", 100);
            map.put("message", "无权限访问~");
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
            map.put("message", "手机 号码可以使用！");
            return map;
        }
        map.put("code", 100);
        map.put("message", "手机号码已存在！");
        return map;
    }

    /**
     * 查询所有作者
     *
     * @return
     */
    @Override
    public List<AuthorDTO> findAllAuthor() {
        List<User> user = userRepository.findAllByRoleId(4);
        List<AuthorDTO> list = new ArrayList<>();
        if (!user.isEmpty()) {
            user.forEach(l -> {
                AuthorDTO authorDTO = new AuthorDTO();
                BeanUtils.copyProperties(l, authorDTO);
                list.add(authorDTO);
            });
        }
        return list;
    }

    /**
     * 查找是否有该qq的用户
     * @param openID
     * @return
     */
    @Override
    public ResultVO<Map<String, Object>> findUserByQQ(String openID, String name,String avatar, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findByQqOpenID(openID);
        System.out.println(user);
        if (user==null){
            //type=1,表示qq用户
            user = registerUserInfo(openID,name,avatar,response,1);
        }
        map.put("message", "登录成功");
        map.put("userVO", registerLoginService.setUserRedisSessionTokenAndCookieSession(response, user));
        return ResultVOUtil.success(map);
    }

    /**
     * 查找是否有该微信的用户
     * @param weChatOpenId
     * @param name
     * @param avatar
     * @param response
     * @return
     */
    @Override
    public ResultVO<Map<String, Object>> findUserByWeChatId(String weChatOpenId, String name, String avatar, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findByWeChatOpenID(weChatOpenId);
        System.out.println(user);
        if (user==null){
            //type=1,表示微信用户
            user = registerUserInfo(weChatOpenId,name,avatar,response,2);
        }
        map.put("message", "登录成功");
        map.put("userVO", registerLoginService.setUserRedisSessionTokenAndCookieSession(response, user));
        return ResultVOUtil.success(map);
    }

    /**
     * 注册qq用户
     */
    @Override
    public User registerUserInfo(String openID,String name,String avatar,HttpServletResponse response,Integer type) {
        User user = new User();
        if(type==1){
            user.setQqOpenID(openID);
        }else {
            user.setWeChatOpenID(openID);
        }
        user.setNikeName(name);
        user.setName(name);
        user.setAvatar("");
        user.setCreateDate(GetTimeUtil.getTime());
        user.setAvatar(avatar);
        user.setUpdateDate(GetTimeUtil.getTime());
        user.setUserType("1");
        user.setUserId(KeyUtil.genUniqueKey());
        user.setStatus(1);
        User userParam = userRepository.save(user);
        return userParam;
    }




}
