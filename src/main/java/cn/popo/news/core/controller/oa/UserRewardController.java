package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.WithdrawNotesDTO;
import cn.popo.news.core.entity.common.PointsReward;
import cn.popo.news.core.entity.common.RewardNotes;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.common.WithdrawNotes;
import cn.popo.news.core.service.UserRewardService;
import cn.popo.news.core.utils.PayCheckUtil;
import cn.popo.news.core.utils.ShiroGetSession;
import cn.popo.news.core.vo.ResultVO;
import io.swagger.models.Model;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 9:22
 * @Description description
 */
@Controller
@RequestMapping("/oa/reward")
public class UserRewardController {

    @Autowired
    private UserRewardService userRewardService;

    @GetMapping("/notes/user/list")
    public ModelAndView selectWithdrawNotes(Map<String,Object> map,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "12") Integer size){
        if (!PayCheckUtil.check()){
            return new ModelAndView("redirect:/oa/user/info.html");
        }
        PageRequest pageRequest =  new PageRequest(page-1,size);
        User user = ShiroGetSession.getUser();
        Page<WithdrawNotes> withdrawNotesPage = userRewardService.findAllWithdrawNotesByUserId(pageRequest, user.getUserId());
        PointsReward pointsReward = userRewardService.findPointsRewardByUserId(user.getUserId());
        map.put("pointsReward",pointsReward);
        map.put("user",user);
        map.put("pageId", 200);
        map.put("pageTitle", "个人积分");
        map.put("pageContent", withdrawNotesPage);
        map.put("url", "/oa/reward/notes/user/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("pages/userWithdrawList",map);
    }
    @PostMapping("/withdraw")
    @ResponseBody
    public ResultVO<Map<String,Object>> withdraw(@RequestParam(value = "userId") String userId, @RequestParam(value = "withdrawSalary") BigDecimal withdrawSalary){
        return userRewardService.WithdrawIntegral(userId, withdrawSalary);
    }
    @PostMapping("/check")
    @ResponseBody
    public ResultVO<Map<String,Object>> checkWithdraw(@RequestParam(value = "withdrawId") Integer withdrawId,
                                                      @RequestParam(value = "resultStatus") Integer resultStatus,
                                                      @RequestParam(value = "remark") String remark){
        return userRewardService.checkWithdraw(withdrawId, remark,resultStatus);
    }

    @GetMapping("/notes/list")
    @RequiresPermissions("checkWithdraw:list")
    public ModelAndView WithdrawNotesList(Map<String,Object> map,
                                            @RequestParam(value = "checkStatus", defaultValue = "1") Integer checkStatus,
                                            @RequestParam(value = "resultStatus", defaultValue = "1") Integer resultStatus,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "12") Integer size){
        PageRequest pageRequest =  new PageRequest(page-1,size);
        User user = ShiroGetSession.getUser();
        PageDTO<WithdrawNotesDTO> withdrawNotesPage = userRewardService.findAllWithdrawNotesByCheckStatusAndResultStatus(pageRequest,checkStatus, resultStatus);
        Integer checked = userRewardService.findAllWithdrawNotesSize(1);
        Integer noChecked = userRewardService.findAllWithdrawNotesSize(0);
        map.put("checked",checked);
        map.put("noChecked",noChecked);
        map.put("user",user);
        map.put("pageId", 201);
        map.put("pageTitle", "积分提现审核");
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pageContent", withdrawNotesPage);
        map.put("url", "/oa/reward/notes/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("pages/withdrawCheckList",map);
    }

    @GetMapping("/rewardNotes/list")
    public ModelAndView WithdrawNotesList(Map<String,Object> map,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "12") Integer size){
        PageRequest pageRequest =  new PageRequest(page-1,size);
        User user = ShiroGetSession.getUser();
        Page<RewardNotes> rewardNotes = userRewardService.findAllRewardNotesByUserId(pageRequest,user.getUserId());
        map.put("user",user);
        map.put("pageId", 202);
        map.put("pageTitle", "积分奖励列表");
        map.put("pageContent", rewardNotes);
        map.put("url", "/oa/reward/rewardNotes/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("pages/userRewardNotesList",map);
    }





}
