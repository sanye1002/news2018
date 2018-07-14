package cn.popo.news.core.dto;

import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-14 下午 4:37
 * @Description description
 */

@Data
public class IpDataDTO {
    private Integer id;

    private String ip;

    private String time;

    private String browser;

    private String address;

    private String util;


}
