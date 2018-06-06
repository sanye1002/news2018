package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 7:04
 * @Description 积分奖励
 */
@Entity
@Data
@Table(name = "points_reward")
public class PointsReward {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String username;

    private String phone;

    //总积分
    private Integer AllIntegral=0;

    //剩余积分
    private Integer laveIntegral=0;

    //剩余可提现金额
    private BigDecimal laveSalary=new BigDecimal(0.00);


}
