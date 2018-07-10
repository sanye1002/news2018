package cn.popo.news.core.service.impl;

import cn.popo.news.common.utils.GetAddressUtil;
import cn.popo.news.core.entity.common.Address;
import cn.popo.news.core.entity.common.IPStatistics;
import cn.popo.news.core.entity.common.IpTime;
import cn.popo.news.core.repository.AddressRepository;
import cn.popo.news.core.repository.IPStatisticsRepository;
import cn.popo.news.core.repository.IpTimeRepository;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.utils.GetTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IPStatisticsServiceImpl implements IPStatisticsService {
    @Autowired
    private IPStatisticsRepository ipStatisticsRepository;

    @Autowired
    private IpTimeRepository ipTimeRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * 保存当天用户访问ip
     */
    @Override
    public void saveIP(String ip, String util) {
        IPStatistics ipStatisticsTemp = ipStatisticsRepository.findAllByIpOrderByNewestTimeDesc(ip);
        IPStatistics ipStatistics = new IPStatistics();
        Long nowTime = System.currentTimeMillis()/1000;
        IpTime ipTime = new IpTime();
        ipTime.setIp(ip);
        ipTime.setTime(GetTimeUtil.getZeroDateFormat(nowTime));
        ipTime.setAddressId(addAddress(ip));
//        System.out.println(addAddress(ip));
//        ipStatistics.setId(KeyUtil.genUniqueKey());
        ipStatistics.setIp(ip);
        ipStatistics.setUtil(util);
        ipStatistics.setNewestTime(nowTime);
        if (ipStatisticsTemp!=null){
            long timesMorning = GetTimeUtil.getTimesmorning();
            long time = ipStatisticsTemp.getNewestTime();
            if (time<timesMorning) {
                ipStatisticsTemp.setNewestTime(nowTime);
                ipTimeRepository.save(ipTime);
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

    /**
     * 地址添加
     * @param ip
     */
    @Override
    public Integer addAddress(String ip) {
        Map<String,String> map = GetAddressUtil.getAddress(ip);
        Address address = new Address();
        String country = map.get("country");
        String region = map.get("region");
        String city = map.get("city");
        String county = map.get("county");
       /* if ("".equals(country)){
            country = "未知";
        }
        if ("".equals(region)){
            region = "未知";
        }
        if ("".equals(city)){
            city = "未知";
        }
        if ("".equals(county)){
            county = "未知";
        }*/
        Address addressTemp = addressRepository.findAddressByCountryAndRegionAndCityAndCounty(country,region,city,county);
        if (addressTemp==null) {
            address.setCountry(country);
            address.setRegion(region);
            address.setCity(city);
            address.setCounty(county);
            addressRepository.save(address);
            return address.getId();
        }else {
            return addressTemp.getId();
        }
    }

    /**
     * 给ip加上地址
     */
    @Override
    public void findAfterIp(Integer a,Integer b) {
        List<IpTime> list = ipTimeRepository.findAll();
        for (int i=a;i<b;i++){
            System.out.println(i);
            IpTime ipTime = list.get(i);
            ipTime.setAddressId(addAddress(ipTime.getIp()));
        }
//        ipTimeRepository.findOne(2).setAddressId(addAddress("182.148.59.37"));

    }


}
