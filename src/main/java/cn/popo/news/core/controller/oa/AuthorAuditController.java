package cn.popo.news.core.controller.oa;

import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.AuthorAuditDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.service.AuthorAuditService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 4:04
 * @Description description
 */
@Slf4j
@Controller
@RequestMapping("/oa/author")
public class AuthorAuditController {

    @Autowired
    private AuthorAuditService authorAuditService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    @ResponseBody
    @PostMapping("/apply")
    public ResultVO<Map<String, String>> apply(
            @RequestParam(value = "reason") String reason,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Map<String,Object> map  = new HashMap<>();

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        authorAuditService.addAudit(reason,userId);

        return ResultVOUtil.success(map);
    }

    @ResponseBody
    @PostMapping("/audit")
    public ResultVO<Map<String, String>> audit(
            @RequestParam(value = "auditState") Integer auditState,
            @RequestParam(value = "id") Integer id
    ){
        Map<String,Object> map  = new HashMap<>();

        authorAuditService.updateAudit(auditState,id);

        return ResultVOUtil.success(map);
    }

    @GetMapping("/apply/list")
    public ModelAndView list(Map<String,Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size
    ){
        map.put("pageId",133);
        map.put("pageTitle","申请作者审核");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<AuthorAuditDTO> pageDTO = authorAuditService.findAllByAuditState(pageRequest,2);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/author/apply/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("pages/author-apply-audit-list",map);
    }
}
