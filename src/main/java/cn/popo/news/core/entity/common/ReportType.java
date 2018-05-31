package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @Author  Administrator
 * @Date    2018/5/25 14:47
 * @Desc    举报类型
 */

@Data
@Entity
@Table(name = "report_type")
public class ReportType {
    /**
     * 举报类型id
     */
    @Id
    @GeneratedValue
    private Integer reportId;

    /**
     * 举报类型
     */
    private String reportType;
}
