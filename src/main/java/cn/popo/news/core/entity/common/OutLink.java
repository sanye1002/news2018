package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-13 下午 4:26
 * @Description description
 */

@Data
@Entity
@Table(name = "out_link")
public class OutLink {
    @Id
    @GeneratedValue
    private Integer id;

    private String link;

    private String linkDesc;

    private Integer ranking;

    private Long time;
}
