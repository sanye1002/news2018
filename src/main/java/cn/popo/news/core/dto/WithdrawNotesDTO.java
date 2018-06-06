package cn.popo.news.core.dto;

import cn.popo.news.core.entity.common.User;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-06 下午 12:22
 * @Description description
 */
@Data
public class WithdrawNotesDTO {

    private Integer id;

    private User user;

    private String createTime;

    private String checkTime;

    private BigDecimal withdrawSalary;

    private Integer checkStatus=0;

    private Integer resultStatus=0;

    private String remark;
}
