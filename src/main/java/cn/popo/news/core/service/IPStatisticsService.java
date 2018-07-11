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
    void addArticleIssueNum(Integer type);
    void addArticleAuditNum(Integer auditState,Integer type);
    Integer findArticleIssueNumByDay(String time,Integer type);
    Integer findArticleAuditNumByDay(String time,Integer auditState,Integer type);
    void addUserIssueNum(String userId,Integer type);
    Integer findUserIssueNumByDay(String time,String userId,Integer type);
}
