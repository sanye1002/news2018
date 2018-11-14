package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2018/11/13
 * @Desc
 */
@Data
@Entity
@Table(name = "newslogs")
public class NewsLogs {
    @Id
    private String id;

    private String userId;

    private String articleId;

    private Date viewTime;

    private Integer preferDegree;

    private String title;

    private String ip;
}
