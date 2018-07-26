package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="radio_info")
public class RadioInfo {
    @Id
    @GeneratedValue
    private Integer id;

    private String url;

    private String radioName;

    private Integer showState;
}
