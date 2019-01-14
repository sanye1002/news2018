package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "live_column")
public class LiveColumn {
    @Id
    private String id;

    //栏目名称
    private String columnName;

    //封面图
    private String cover;
}
