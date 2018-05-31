package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 11:57
 * @Desc    关注
 */

@Entity
@Data
@Table(name="attention")
public class Attention {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 关注者Id
     */
    private String aid;

    /**
     * 被关注者id
     */
    private String fid;
}
