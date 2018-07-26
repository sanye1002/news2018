package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.form.RadioInfoForm;
import cn.popo.news.core.repository.RadioInfoRepository;
import cn.popo.news.core.service.RadioInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class RadioInfoServiceImpl implements RadioInfoService {

    @Autowired
    private RadioInfoRepository radioInfoRepository;

    /**
     * 添加
     * @param radioInfoForm
     */
    @Override
    public void addRadio(RadioInfoForm radioInfoForm) {
        RadioInfo radioInfo = new RadioInfo();
        if(radioInfoForm.getId()==null){
            BeanUtils.copyProperties(radioInfoForm,radioInfo);
            radioInfoRepository.save(radioInfo);
        }else {
            radioInfo = radioInfoRepository.findOne(radioInfoForm.getId());
            radioInfo.setUrl(radioInfoForm.getUrl());
            radioInfo.setRadioName(radioInfoForm.getRadioName());
            radioInfo.setShowState(radioInfoForm.getShowState());
        }

    }

    @Override
    public RadioInfo findOneRadio(Integer id) {
        return radioInfoRepository.findOne(id);
    }

    @Override
    public Page<RadioInfo> findAllRadioInfo(Pageable pageable) {
        Page<RadioInfo> radioInfoPage = radioInfoRepository.findAll(pageable);
        return radioInfoPage;
    }

    @Override
    public void deleteRadio(Integer id) {
        radioInfoRepository.delete(id);
    }

    @Override
    public Page<RadioInfo> findRadioByShowState(Pageable pageable, Integer showState) {
        Page<RadioInfo> radioInfoPage = radioInfoRepository.findAllByShowState(pageable,showState);
        return radioInfoPage;
    }
}
