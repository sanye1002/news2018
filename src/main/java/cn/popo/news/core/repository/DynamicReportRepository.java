package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.Dynamic;
import cn.popo.news.core.entity.common.DynamicReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface DynamicReportRepository extends JpaRepository<DynamicReport,Integer> {
}
