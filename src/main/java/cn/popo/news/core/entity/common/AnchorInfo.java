package cn.popo.news.core.entity.common;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="anchor_info")
public class AnchorInfo {
    @Id
    private String id;

    //用户id
    private String userId;

    //等级
    private Integer grad;

    //所有礼物
    private Integer allGift;

    //当前礼物
    private Integer nowGift;

    //名称
    private String username;

    //性别
    private Integer gender;

    //年龄
    private Integer age;

    //介绍
    private String info;

    //头像
    private String avatar;



}
