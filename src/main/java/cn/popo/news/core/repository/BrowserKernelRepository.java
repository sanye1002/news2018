package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.BrowserKernel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-15 下午 8:37
 * @Description description
 */
public interface BrowserKernelRepository extends JpaRepository<BrowserKernel,Integer> {
    BrowserKernel findAllByKernel(String browser);
}
