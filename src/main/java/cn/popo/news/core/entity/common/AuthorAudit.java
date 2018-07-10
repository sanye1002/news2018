package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 3:50
 * @Description description
 */
@Data
@Entity
@Table(name = "author_audit")
public class AuthorAudit {
    @Id
    @GeneratedValue
    private Integer id;

    private Long time;

    private String userId;

    private String reason;

    private Integer auditState;
}
