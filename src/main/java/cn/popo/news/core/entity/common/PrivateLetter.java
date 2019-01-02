package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:36
 * @Desc    通信
 */

@Data
@Entity
@Table(name="private_letter")
public class PrivateLetter {
    @Id
    private String id;

    /**
     * 发送者id
     */
    private String userId;

    /**
     * 接收者id
     */
    private String uid;

    /**
     * 发送的消息
     */
    @Column(columnDefinition = "TEXT")
    private String sendMessage;

    /**
     * 发送时间
     */
    private Long time;

    /**
     * 状态 0：未阅读 1：已阅读
     */
    private Integer state;

    //内容分类：type=text(文本) type=sound(语音) type=image(图片) type=video(视频)
    private String type;
}
