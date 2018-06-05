package cn.popo.news.core.dto.api;

import lombok.Data;

import java.util.List;

@Data
public class UserReCommentVO {
    private String title;
    private List<String> imgList;
    private Integer imgNum;
}
