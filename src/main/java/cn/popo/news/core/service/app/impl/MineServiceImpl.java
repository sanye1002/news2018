package cn.popo.news.core.service.app.impl;

import cn.popo.news.core.entity.app.MineSections;
import cn.popo.news.core.service.app.MineService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MineServiceImpl implements MineService {

    @Override
    public ResultVO<Map<String, Object>> getMineList() {
        Map<String, Object> map = new HashMap<>();
        List<List<MineSections>>  sectionsList = new ArrayList<>();
        List<MineSections>  sectionsList1 = new ArrayList<>();
        List<MineSections>  sectionsList2 = new ArrayList<>();
        List<MineSections>  sectionsList3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MineSections mineSections1 = new MineSections();
            switch (i) {
                case 0:
                    mineSections1.setText("我的动态");
                    sectionsList1.add(mineSections1);
                    sectionsList.add(sectionsList1);
                    break;
                case 1:
                    mineSections1.setText("我的收藏");
                    sectionsList1.add(mineSections1);
                    sectionsList.add(sectionsList1);
                    break;
                case 2:
                    mineSections1.setText("关注");
                    sectionsList2.add(mineSections1);
                    sectionsList.add(sectionsList2);
                    break;
                case 3:
                    mineSections1.setText("粉丝");
                    sectionsList2.add(mineSections1);
                    sectionsList.add(sectionsList2);
                    break;
                case 4:
                    mineSections1.setText("个人信息");
                    sectionsList3.add(mineSections1);
                    sectionsList.add(sectionsList3);
                    break;
                default:
                    break;
            }


        }

        map.put("sections", sectionsList);
        return ResultVOUtil.success(map);
    }
}
