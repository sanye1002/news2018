package cn.popo.news.core.service.impl;

import cn.popo.news.core.entity.common.IPStatistics;
import cn.popo.news.core.entity.common.IpTime;
import cn.popo.news.core.repository.IPStatisticsRepository;
import cn.popo.news.core.repository.IpTimeRepository;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IPStatisticsServiceImpl implements IPStatisticsService {
    @Autowired
    IPStatisticsRepository ipStatisticsRepository;

    @Autowired
    IpTimeRepository ipTimeRepository;

    /**
     * 保存当天用户访问ip
     */
    @Override
    public void saveIP(String ip, String util) {
        IPStatistics ipStatisticsTemp = ipStatisticsRepository.findAllByIp(ip);
        IPStatistics ipStatistics = new IPStatistics();
        long nowTime = System.currentTimeMillis()/1000;
        IpTime ipTime = new IpTime();
        ipTime.setIp(ip);
        ipTime.setTime(GetTimeUtil.getZeroDateFormat(nowTime));
//        ipStatistics.setId(KeyUtil.genUniqueKey());
        ipStatistics.setIp(ip);
        ipStatistics.setUtil(util);
        ipStatistics.setNewestTime(nowTime);
        if (ipStatisticsTemp!=null){
            long timesMorning = GetTimeUtil.getTimesmorning();
            long time = ipStatisticsTemp.getNewestTime();
            if (time<timesMorning) {

                ipTimeRepository.save(ipTime);
                ipStatisticsRepository.save(ipStatistics);
            }else {
                ipStatisticsTemp.setNewestTime(nowTime);
            }
        }else {
            ipTimeRepository.save(ipTime);
            ipStatisticsRepository.save(ipStatistics);
        }





    }

    /**
     * 查找某天的访问量
     */
    @Override
    public Integer findDayCount(String time) {
        return ipTimeRepository.findAllByTime(time).size();
    }

    /**
     * 查找某月的访问量
     */
    @Override
    public Integer findMonthCount(String month) {
        return ipTimeRepository.findAllByTimeEndingWith(month).size();
    }

    /**
     * 查找某周的访问量
     */
    @Override
    public Integer findWeekCount(String week) {
        return null;
    }


}
