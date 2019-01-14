package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="live_type")
public class LiveType {
    @Id
    private String id;

    //直播栏目id
    private String columnId;

    //直播栏目名称
    private String columnName;

    //类型名称
    private String typeName;

    //封面图
    private String cover;
}
