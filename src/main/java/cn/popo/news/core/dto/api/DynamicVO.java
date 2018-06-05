package cn.popo.news.core.dto.api;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
public class DynamicVO {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String content;

    private List<String> imgList;

    private String ManyTimeAgo;
}
