package cn.popo.news.core.entity.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:30
 * @Desc    公告
 */

@Data
@Entity
@Table(name="t_notice")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 发布公告者id
     */
    private String uid;

    /**
     * 收公告者id，id=0所有人
     */
    private String nid;

    /**
     * 发布公告时间
     */
    private Long time;

    /**
     * 公告内容
     */
    private String noticeInfo;


}
