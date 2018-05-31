package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DetailsVO implements Serializable {
    private String uid;
    private String nickName;
    private String avatar;
    private String time;
    private String title;
    private String content;
    private List<String> keywordList;
    private List<String> imgList;
    private Integer collectFlag;
    private String reportFlag;
    private List<CommentVO> commentVOList;
}
