package cn.popo.news.core.controller.api;


import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.api.UserMsgVO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.api.IMService;
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


@RequestMapping("/api/im")
@RestController
public class IMController {

    @Autowired
    private UserSessionUtil userSessionUtil;
    @Autowired
    private IMService imService;


    /**
     * 查询用户消息列表
     *
     * @param request  r
     * @param response r
     * @return 返回列表
     */
    @PostMapping("/msg/list")
    public ResultVO selectMsgList(HttpServletRequest request,
                                  HttpServletResponse response) {

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
//        String userId = "1527582639993";

        List<UserMsgVO> msgList = imService.findUserList(userId);
        map.put("msgList", msgList);
        return ResultVOUtil.success(map);
    }

    /**
     * msg存储
     * @param request r
     * @param response r
     * @param toUserId 对方userID
     * @param nickname 对方昵称
     * @param avatar 对方头像
     * @return 返回
     */
    @PostMapping("/msg/save")
    public ResultVO saveUserMsg(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "toUserId") String toUserId,
                                @RequestParam(value = "nickname") String nickname,
                                @RequestParam(value = "avatar") String avatar
    ) {

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }

        Map<String, Object> map = new HashMap<>();
        User user = userSessionUtil.getUserByCookie(request,response);
        String userId = user.getUserId();
//        String userId = "1527582639993";
        imService.save(userId, toUserId, avatar, nickname);
        imService.save(toUserId,userId,user.getAvatar(),user.getNikeName());
        return ResultVOUtil.success(map);
    }

    /**
     * 更新 最后时间和msg
     * @param id id
     * @param msg msg
     * @param userId 用户id
     * @param toUserId 对方id
     * @return 返回
     */
    @PostMapping("/msg/update")
    public ResultVO updateUserMsg(@RequestParam(value = "id") Integer id,
                                  @RequestParam(value = "msg") String msg,
                                  @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "toUserId") String toUserId
    ) {
        Map<String, Object> map = new HashMap<>();
        if(id==0){
            id = imService.findUserAndToUserId(userId,toUserId);
        }
        imService.updateLastTime(id);
        imService.updateLastMsg(id, msg);
        Integer toId = imService.findUserAndToUserId(toUserId,userId);
        imService.updateLastMsg(toId,msg);
        imService.updateLastTime(toId);
        return ResultVOUtil.success(map);
    }

    /**
     * 更新未读条数
     * @param userId 用户id
     * @param toUserId 对方id
     * @return 返回
     */
    @PostMapping("/msg/update/unreadNum")
    public ResultVO updateUserMsgUnreadNum(
                                  @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "toUserId") String toUserId
    ) {
        Map<String, Object> map = new HashMap<>();
        imService.updateUnReadNum(userId,toUserId);
        return ResultVOUtil.success(map);
    }


}
