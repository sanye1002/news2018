package cn.popo.news.core.service;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.form.RadioInfoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadioInfoService {
    void addRadio(RadioInfoForm radioInfoForm);
    RadioInfo findOneRadio(Integer id);
    Page<RadioInfo> findAllRadioInfo(Pageable pageable);
    void deleteRadio(Integer id);
    Page<RadioInfo> findRadioByShowState(Pageable pageable,Integer showState);
}
