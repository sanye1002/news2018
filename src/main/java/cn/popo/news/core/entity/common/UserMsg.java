package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="user_msg")
public class UserMsg {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String toUserId;

    //对方用户的头像
    private String toUserAvatar;

    //对方用户的昵称
    private String toUserName;

    //是否在消息列表显示 默认state=1表示显示 0表示不显示
    private Integer state=1;

    //最后一次发送消息的时间
    private Long lastTime;

    private String lastMsg;

    private Integer unReadNum=0;

}
