package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVO implements Serializable {
    private String id;
    private String commentInfo;
    private String nickName;
    private String avatar;
    private String manyTimeAgo;
    private Integer praiseNum;
    private Integer replyNum;
    private Integer commentPraiseId;

}
