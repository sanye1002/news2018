package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="article_audit_num")
public class ArticleAuditNum {
    @Id
    @GeneratedValue
    private Integer id;

    private String time;

    private Integer count;

    private Integer auditState;

    private Integer type;
}
