package cn.popo.news.core.entity.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class CollectParam {
    @NotEmpty
    private String aid;
    @NotEmpty
    private String uid;

    private Integer typeId;

}
