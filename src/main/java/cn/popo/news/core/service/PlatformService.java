package cn.popo.news.core.service;

import ch.qos.logback.classic.spi.PlatformInfo;
import cn.popo.news.core.entity.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 12:24
 */
public interface PlatformService {
    /**
     *
     * @param platformInfo
     * @return
     */
    PlatformInfo save(PlatformInfo platformInfo);

    /**
     * 查询
     * @param pageable
     * @return
     */
    Page<PlatformInfo> findAll(Pageable pageable);

    /**
     *
     * @return
     */
    List<PlatformInfo> findAll();

    /**
     * 删除
     * @param id
     */
    Map<String,Object> delete(Integer id);

    /**
     * id查找
     * @param id
     * @return
     */
    PlatformInfo findOne(Integer id);

    List<User> findAllUserInfoByPlatformId(Integer platformId);
}
