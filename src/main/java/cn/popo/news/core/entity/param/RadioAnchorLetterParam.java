package cn.popo.news.core.entity.param;

import lombok.Data;

@Data
public class RadioAnchorLetterParam {
    //内容
    private String letter;
    //发送信息的用户id
    private String userId;
    //接受电台id
    private Integer radioId;
}
