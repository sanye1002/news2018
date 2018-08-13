package cn.popo.news.core.service.app;

import cn.popo.news.core.vo.ResultVO;

import java.util.Map;

public interface MineService {

    ResultVO<Map<String,Object>> getMineList();
}
