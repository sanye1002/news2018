package cn.popo.news.core.dto.api;

import lombok.Data;

@Data
public class PrivateLetterVO {
    private String id;
    private String username;
    private String avatar;
    private String message;
    private String time;
    private String type;

}
