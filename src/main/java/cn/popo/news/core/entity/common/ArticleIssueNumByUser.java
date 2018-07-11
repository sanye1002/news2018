package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="article_issue_num_by_user")
public class ArticleIssueNumByUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String time;

    private Integer count;

    private Integer type;

    private String userId;

}
