package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RadioInfoForm {
    @NotEmpty
    private String url;
    @NotEmpty
    private String radioName;

    private Integer showState;
    private Integer id;
}
