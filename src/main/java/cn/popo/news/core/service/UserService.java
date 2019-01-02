package cn.popo.news.core.service;

import cn.popo.news.core.dto.AuthorDTO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.UserForm;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/28 19:10
 * @Desc
 */
public interface UserService {
    Page<User> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable, String userType, Integer status);

    User findOne(String id);

    void saveUser(UserForm userForm);

    void deleteUser(String userId);

    User findByPhoneAndStatus(String phone);

    User findByUserIdAndState(String userId);

    Map<String,Object> login(HttpServletRequest request, String phone, String password);

    User saveByUser(User user);

    Map<String, Object> findPhoneIsEmpty(String phone);

    Map<String, Object> findAllByPhone(String phone);

    List<AuthorDTO> findAllAuthor();

    ResultVO<Map<String, Object>> findUserByQQ(String openID, String name,String avatar, HttpServletResponse response);

    User registerUserInfo(String openID,String name,String avatar,HttpServletResponse response,Integer type);

    ResultVO<Map<String, Object>> findUserByWeChatId(String weChatOpenId,String name,String avatar,HttpServletResponse response);

}
