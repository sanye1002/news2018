package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2018/11/09
 * @Desc
 */
@Data
@Entity
@Table(name="article_view_user")
public class ArticleViewUser implements Serializable {

    @Id
    private String id;

    private Date viewTime;

    private String userId;

    private String articleId;

    private String title;

    private String ip;
}
