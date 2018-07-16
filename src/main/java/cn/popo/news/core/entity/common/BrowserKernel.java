package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-15 下午 8:34
 * @Description description
 */

@Data
@Entity
@Table(name = "browser_kernel")
public class BrowserKernel {
    @Id
    @GeneratedValue
    private Integer id;

    private String kernel;
}
