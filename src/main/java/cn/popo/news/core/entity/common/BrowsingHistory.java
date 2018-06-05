package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="browsingHistory")
public class BrowsingHistory {
    @Id
    @GeneratedValue
    private Integer id;

    private String articleId;

    private String userId;

    private Long time;
}
