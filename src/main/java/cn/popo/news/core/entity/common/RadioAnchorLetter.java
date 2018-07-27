package cn.popo.news.core.entity.common;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="radio_anchor_letter")
public class RadioAnchorLetter {
    @Id
    @GeneratedValue
    private Integer id;

    //内容
    private String letter;

    //时间（yyyy-MM-dd）
    private String day;

    //时间戳
    private Long time;

    //发送信息的用户id
    private String userId;

    //接受电台id
    private Integer radioId;


}
