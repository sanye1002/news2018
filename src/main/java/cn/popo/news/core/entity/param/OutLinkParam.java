package cn.popo.news.core.entity.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-13 下午 4:49
 * @Description description
 */

@Data
public class OutLinkParam {
    private Integer id;

    @NotEmpty
    private String link;

    @NotEmpty
    private String linkDesc;

    private Integer ranking;
}
