package cn.popo.news.core.controller.api;

import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.api.UserFriendsVO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.api.UserFriendsService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user/friend")
@RestController
public class UserFriendsController {
    private final UserSessionUtil userSessionUtil;
    private final UserFriendsService userFriendsService;

    @Autowired
    public UserFriendsController(UserSessionUtil userSessionUtil, UserFriendsService userFriendsService) {
        this.userSessionUtil = userSessionUtil;
        this.userFriendsService = userFriendsService;
    }

    /**
     * 用户好友列表查询
     *
     * @param request  r
     * @param response r
     * @return 好友列表
     */
    @PostMapping("/list")
    public ResultVO selectUserFriendList(HttpServletRequest request,
                                         HttpServletResponse response) {

        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
//        String userId = "1527582639993";
        List<UserFriendsVO> userFriendList = userFriendsService.findUserFriendList(userId);
        map.put("userFriendList", userFriendList);
        return ResultVOUtil.success(map);
    }

    /**
     * 查询好友-phone
     *
     * @param request  r
     * @param response r
     * @param phone    手机号
     * @return 返回好友信息
     */
    @PostMapping("/select/phone")
    public ResultVO selectUserByPhone(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "phone") String phone
    ) {

        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
//        String userId = "1527582639993";
        UserFriendsVO userFriendsVO = userFriendsService.findUserFriendByPhone(phone, userId);
        map.put("userFriendsVO", userFriendsVO);
        return ResultVOUtil.success(map);
    }

    /**
     * 查询好友-userId
     *
     * @param request      r
     * @param response     r
     * @param searchUserId 用户id
     * @return 返回好友信息
     */
    @PostMapping("/select/userId")
    public ResultVO selectUserByUserId(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @RequestParam(value = "searchUserId") String searchUserId
    ) {

        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        String userId = userSessionUtil.getUserByCookie(request, response).getUserId();
//        String userId = "1527582639993";
        UserFriendsVO userFriendsVO = userFriendsService.findUserFriendByUserId(searchUserId, userId);
        map.put("userFriendsVO", userFriendsVO);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加好友
     *
     * @param request        r
     * @param response       r
     * @param friendUserId   用户id
     * @param friendNickname 用户昵称
     * @param friendAvatar   用户头像
     * @return 返回成功与否
     */
    @PostMapping("/save")
    public ResultVO saveFriend(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "friendUserId") String friendUserId,
                               @RequestParam(value = "friendNickname") String friendNickname,
                               @RequestParam(value = "friendAvatar") String friendAvatar
    ) {

        if (!userSessionUtil.verifyLoginStatus(request, response)) {
            return ResultVOUtil.error(3, "用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        User user = userSessionUtil.getUserByCookie(request, response);
        String userId = user.getUserId();
//        String userId = "1527582639993";
        userFriendsService.saveFriend(userId, friendUserId, friendNickname, friendAvatar);
//        userFriendsService.saveFriend(friendUserId,userId,user.getNikeName(),user.getAvatar());
        return ResultVOUtil.success(map);
    }

    /**
     * 删除好友
     *
     * @param id id
     * @return 返回成功与否
     */
    @PostMapping("/delete")
    public ResultVO deleteFriend(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        userFriendsService.deleteFriend(id);
        return ResultVOUtil.success(map);
    }


}
