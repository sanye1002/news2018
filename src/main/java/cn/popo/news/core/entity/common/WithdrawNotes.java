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
 * @create 2018-06-05 下午 7:23
 * @Description 提现记录
 */
@Entity
@Data
@Table(name = "withdraw_notes")
public class WithdrawNotes {

    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String createTime;

    private String checkTime;

    private BigDecimal withdrawSalary;

    private Integer checkStatus=0;

    private Integer resultStatus=0;



}
