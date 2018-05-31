package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 11:42
 * @Desc    文章封面图
 */
@Data
@Entity
@Table(name = "img_url")
public class ImgUrl {
    @Id
    private Integer id;

    /**
     * 文章id
     */
    private String aid;

    /**
     * 文章封面图路径
     */
    private String imgUrl;
}
