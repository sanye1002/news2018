package cn.popo.news.core.service;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.RadioAnchorLetterDTO;
import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.form.RadioInfoForm;
import cn.popo.news.core.entity.param.RadioAnchorLetterParam;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RadioInfoService {
    void addRadio(RadioInfoForm radioInfoForm);
    void updateShowState(Integer id, Integer showState);
    List<RadioInfo> findAllRadioInfo();
    RadioInfo findOneRadio(Integer id);
    Page<RadioInfo> findAllRadioInfo(Pageable pageable);
    void deleteRadio(Integer id);
    Page<RadioInfo> findRadioByShowState(Pageable pageable,Integer showState);
    void addRadioAnchorLetter(RadioAnchorLetterParam radioAnchorLetterParam);
    PageDTO<RadioAnchorLetterDTO> findAllByTimeAndRadioId(Pageable pageable,String day,Integer radioId);
}
