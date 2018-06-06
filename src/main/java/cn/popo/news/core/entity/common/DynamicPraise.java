package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:11
 * @Desc    评论赞
 */

@Data
@Entity
@Table(name="dynamic_praise")
public class DynamicPraise {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 被赞动态id
     */
    private String dynamicId;

    /**
     * 赞用户id
     */
    private String uid;

}
