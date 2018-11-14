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
@Table(name = "recommendations")
public class Recommendations {
    @Id
    private String id;

    //    用户id
    private String userId;
    //    文章id
    private String articleId;
    //   推荐时间
    private Date deriveTime;

    private Integer feedback;
    //  推荐方法
    private Integer deriveAlgorithm;
}
