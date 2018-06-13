package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "logo")
public class Logo {
    @Id
    @GeneratedValue
    private Integer id;

    private String logoUrl;

    private Integer showState;

    public Logo(String logoUrl, Integer showState) {
        this.logoUrl = logoUrl;
        this.showState = showState;
    }

    public Logo() {
    }
}
