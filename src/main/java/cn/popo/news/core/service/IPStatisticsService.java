package cn.popo.news.core.service;

import cn.popo.news.core.entity.common.IPStatistics;

import java.util.List;

public interface IPStatisticsService {
    void saveIP(String ip,String util);
    Integer findDayCount(String time);
    Integer findMonthCount(String month);
    Integer findWeekCount(String week);
    Integer addAddress(String ip);
    void findAfterIp(Integer a,Integer b);
}
