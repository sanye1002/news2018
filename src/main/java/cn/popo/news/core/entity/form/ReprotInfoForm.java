package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/25 15:06
 * @Desc    文章举报参数
 */

@Data
public class ReprotInfoForm {
    /**
     * 用户id
     */
    private String uid;

    /**
     * 文章id
     */
    @NotEmpty
    private String aid;

    /**
     * 举报类型id
     */
    private Integer typeId;

    /**
     * 举报内容
     */
    @NotEmpty
    private String info;
}
