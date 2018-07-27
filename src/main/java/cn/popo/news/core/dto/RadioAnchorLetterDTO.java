package cn.popo.news.core.dto;

import lombok.Data;

@Data
public class RadioAnchorLetterDTO {
    private Integer id;

    //内容
    private String letter;

    //时间戳
    private String time;

    private String userName;

    private String radioName;


}
