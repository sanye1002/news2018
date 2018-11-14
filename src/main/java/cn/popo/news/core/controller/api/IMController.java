package cn.popo.news.core.controller.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@RequestMapping("/api/im")
@Controller
public class IMController {

    @GetMapping("/index")
    public ModelAndView imIndex(Map<String, Object> map){
        map.put("pageId", 11111);
        map.put("pageTitle", "im及时通讯");
        return new ModelAndView("pages/im", map);
    }


}
