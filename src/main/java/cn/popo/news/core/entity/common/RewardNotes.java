package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 7:54
 * @Description 奖励记录
 */
@Entity
@Data
@Table(name = "reward_notes")
public class RewardNotes {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String articleId;

    private String articleTitle;

    private String articleType;

    private Integer rewardIntegral;

    private String createTime;


}
