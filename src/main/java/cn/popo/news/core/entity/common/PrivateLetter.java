package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @GeneratedValue
    private Integer id;

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
    private String sendMessage;

    /**
     * 发送时间
     */
    private Long time;
}
