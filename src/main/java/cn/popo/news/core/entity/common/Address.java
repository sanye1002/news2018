package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue
    private Integer id;

    //国家
    private String country;

    //省
    private String region;

    //城市
    private String city;

    //区
    private String county;
}
