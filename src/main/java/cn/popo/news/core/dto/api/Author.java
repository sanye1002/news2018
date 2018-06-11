package cn.popo.news.core.dto.api;

import lombok.Data;

@Data
public class Author {
    private String userId;
    private String name;
    private String avatar;
    private String signature;
}
