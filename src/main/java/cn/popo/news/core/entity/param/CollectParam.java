package cn.popo.news.core.entity.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class CollectParam {
    @NotEmpty
    private String aid;
    private String uid;
    private Integer typeId;

}
