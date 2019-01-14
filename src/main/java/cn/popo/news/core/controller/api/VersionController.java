package cn.popo.news.core.controller.api;

import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/version")
@RestController
public class VersionController {

    private static final String VERSION="1.1.1";
    private static final String NEWESTAPKTENCENT="" +
            "http://wxz.myapp.com/16891/950C8C8B61E9BC0AFC2A0F8D4598DA96.apk?" +
            "fsname=io.dcloud.H5C872CC1_1.1.0_84.apk&hsr=4d5s";

    //判断app是否为最新版本
    @PostMapping("/flag")
    public ResultVO selectUserFriendList(@RequestParam(value = "version") String version,
                                         @RequestParam(value = "client") String client) {
        Map<String, Object> map = new HashMap<>();
        map.put("url",NEWESTAPKTENCENT);
        if("android".equals(client)){
            map.put("flag",VERSION.equals(version));
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.success(map);
    }
}
