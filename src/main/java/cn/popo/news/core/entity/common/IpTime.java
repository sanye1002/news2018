package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="ip_time")
public class IpTime {

    @Id
    @GeneratedValue
    private Integer id;

    private String ip;

    private Long time;
}
