package cn.popo.news.core.service.impl;

import cn.popo.news.common.utils.GetAddressUtil;
import cn.popo.news.common.utils.StatisticsInfoGetUtil;
import cn.popo.news.core.dto.IpDataDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.*;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.utils.GetTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    private ArticleIssueNumRepository articleIssueNumRepository;

    @Autowired
    private ArticleAuditNumRepository articleAuditNumRepository;

    @Autowired
    private ArticleIssueNumByUserRepository articleIssueNumByUserRepository;

    /**
     * 保存当天用户访问ip
     */
    @Override
    public void saveIP(String ip, String util,String browser) {
        IPStatistics ipStatisticsTemp = ipStatisticsRepository.findAllByIpOrderByNewestTimeDesc(ip);
        IPStatistics ipStatistics = new IPStatistics();
        Long nowTime = System.currentTimeMillis()/1000;
        IpTime ipTime = new IpTime();
        ipTime.setIp(ip);
        ipTime.setTime(GetTimeUtil.getZeroDateFormat(nowTime));
        ipTime.setAddressId(addAddress(ip));
        ipTime.setBrowser(browser);
        ipTime.setUtil(util);
        ipTime.setFullTime(nowTime);
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
                ipTime.setUtil(util);
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
     * 查询某天的访问数据
     * @param time
     * @return
     */
    @Override
    public PageDTO<IpDataDTO> findIpInfoByDay(Pageable pageable, String time) {
        PageDTO<IpDataDTO> pageDTO = new PageDTO<>();
        Page<IpTime> ipTime =  ipTimeRepository.findAllByTime(pageable,time);
        List<IpDataDTO> list = new ArrayList<>();
        if (!ipTime.getContent().isEmpty()){
            ipTime.getContent().forEach(l->{
                IpDataDTO ipDataDTO = new IpDataDTO();
                BeanUtils.copyProperties(l,ipDataDTO);
                if (l.getFullTime()!=null){
                    ipDataDTO.setTime(GetTimeUtil.getDateFormatE(l.getFullTime()));
                }
                ipDataDTO.setAddress(addressRepository.findOne(l.getAddressId()).getCity());
                list.add(ipDataDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
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

    /**
     * 添加当天发布文章数量
     */
    @Override
    public void addArticleIssueNum(Integer type) {
        String time = GetTimeUtil.getTime();
        ArticleIssueNum articleIssueNumTemp = new ArticleIssueNum();
        ArticleIssueNum articleIssueNumAllTemp = new ArticleIssueNum();
        ArticleIssueNum articleIssueNum = articleIssueNumRepository.findAllByTimeAndType(time,type);
        ArticleIssueNum articleIssueNumAll = articleIssueNumRepository.findAllByTimeAndType(time,100);


        if (articleIssueNumAll!=null) {
            articleIssueNumAll.setCount(articleIssueNumAll.getCount()+1);
        }else {
            articleIssueNumAllTemp.setCount(1);
            articleIssueNumAllTemp.setTime(time);
            articleIssueNumAllTemp.setType(100);
            articleIssueNumRepository.save(articleIssueNumAllTemp);
        }

        if (articleIssueNum!=null){
            articleIssueNum.setCount(articleIssueNum.getCount()+1);
        }else {
            articleIssueNumTemp.setCount(1);
            articleIssueNumTemp.setTime(time);
            articleIssueNumTemp.setType(type);
            articleIssueNumRepository.save(articleIssueNumTemp);
        }
    }

    /**
     * 添加当天文章审核数量
     */
    @Override
    public void addArticleAuditNum(Integer auditState,Integer type) {
        String time = GetTimeUtil.getTime();
        ArticleAuditNum articleAuditNumTemp = new ArticleAuditNum();
        ArticleAuditNum articleAuditNumAllTemp = new ArticleAuditNum();
        ArticleAuditNum articleAuditNum = articleAuditNumRepository.findAllByTimeAndAuditStateAndType(time,auditState,type);
        ArticleAuditNum articleAuditNumAll = articleAuditNumRepository.findAllByTimeAndAuditStateAndType(time,2,100);

        if (articleAuditNumAll!=null) {
            articleAuditNumAll.setCount(articleAuditNumAll.getCount()+1);
        }else {
            articleAuditNumAllTemp.setCount(1);
            articleAuditNumAllTemp.setTime(time);
            articleAuditNumAllTemp.setType(100);
            articleAuditNumAllTemp.setAuditState(2);
            articleAuditNumRepository.save(articleAuditNumAllTemp);
        }

        if (articleAuditNum!=null){
            articleAuditNum.setCount(articleAuditNum.getCount()+1);
        }else {
            articleAuditNumTemp.setCount(1);
            articleAuditNumTemp.setTime(time);
            articleAuditNumTemp.setAuditState(auditState);
            articleAuditNumTemp.setType(type);
            articleAuditNumRepository.save(articleAuditNumTemp);
        }
    }

    /**
     * 每天文章发布数量查询
     * @return
     */
    @Override
    public Integer findArticleIssueNumByDay(String time,Integer type) {
//        ArticleIssueNum  articleIssueNum = articleIssueNumRepository.findAllByTime(time);
        ArticleIssueNum articleIssueNum = articleIssueNumRepository.findAllByTimeAndType(time,type);
        if (articleIssueNum!=null){
            return articleIssueNum.getCount();
        }else {
            return 0;
        }

    }

    /**
     * 每天文章审核数量查询
     * @return
     */
    @Override
    public Integer findArticleAuditNumByDay(String time,Integer auditState,Integer type) {
        ArticleAuditNum articleAuditNum = articleAuditNumRepository.findAllByTimeAndAuditStateAndType(time,auditState,type);
        if (articleAuditNum!=null){
            return articleAuditNum.getCount();
        }else {
            return 0;
        }

    }

    /**
     * 用户当天发布文章统计
     * @param userId
     * @param type
     */
    @Override
    public void addUserIssueNum(String userId, Integer type) {
        String time = GetTimeUtil.getTime();
        ArticleIssueNumByUser articleIssueNumByUserTemp = new ArticleIssueNumByUser();
        ArticleIssueNumByUser articleIssueNumByUser = articleIssueNumByUserRepository.findAllByTimeAndUserIdAndType(time,userId,type);
        ArticleIssueNumByUser articleIssueNumByUserAllTemp = new ArticleIssueNumByUser();
        ArticleIssueNumByUser articleIssueNumByUserAll = articleIssueNumByUserRepository.findAllByTimeAndUserIdAndType(time,userId,100);

        if(articleIssueNumByUserAll!=null){
            articleIssueNumByUserAll.setCount(articleIssueNumByUserAll.getCount()+1);
        }else {
            articleIssueNumByUserAllTemp.setCount(1);
            articleIssueNumByUserAllTemp.setTime(time);
            articleIssueNumByUserAllTemp.setUserId(userId);
            articleIssueNumByUserAllTemp.setType(100);
            articleIssueNumByUserRepository.save(articleIssueNumByUserAllTemp);
        }

        if(articleIssueNumByUser!=null){
            articleIssueNumByUser.setCount(articleIssueNumByUser.getCount()+1);
        }else {
            articleIssueNumByUserTemp.setCount(1);
            articleIssueNumByUserTemp.setTime(time);
            articleIssueNumByUserTemp.setUserId(userId);
            articleIssueNumByUserTemp.setType(type);
            articleIssueNumByUserRepository.save(articleIssueNumByUserTemp);
        }



    }

    /**
     * 用户一天文章发布量查询
     * @param userId
     * @param type
     * @return
     */
    @Override
    public Integer findUserIssueNumByDay(String time,String userId, Integer type) {
        ArticleIssueNumByUser articleIssueNumByUser = articleIssueNumByUserRepository.findAllByTimeAndUserIdAndType(time,userId,type);
        if (articleIssueNumByUser!=null){
            return articleIssueNumByUser.getCount();
        }else {
            return 0;
        }
    }

}
