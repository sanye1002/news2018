package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="live_room")
public class LiveRoom {
    @Id
    private String id;
    //直播id
    private String liveId;

    //直播类型id
    private String typeId;

    //类型名称
    private String typeName;

    //礼物数
    private Integer giftNumber;

    //观看人数
    private Integer lookNum;

    //直播状态
    private Integer liveStatus;

    //封面
    private String cover;

    //直播地址
    private String liveAddress;

    //用户id
    private String userId;

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
