package cn.popo.news.core.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-09 下午 3:50
 * @Description description
 */
@Data
public class AuthorAuditDTO {

    public Integer id;

    public Long time;

    public String nikeName;

    public String name;

    public String reason;

}
