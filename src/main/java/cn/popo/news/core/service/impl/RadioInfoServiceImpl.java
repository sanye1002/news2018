package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.RadioAnchorLetterDTO;
import cn.popo.news.core.entity.common.RadioAnchorLetter;
import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.RadioInfoForm;
import cn.popo.news.core.entity.param.RadioAnchorLetterParam;
import cn.popo.news.core.repository.RadioAnchorLetterRepository;
import cn.popo.news.core.repository.RadioInfoRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.RadioInfoService;
import cn.popo.news.core.utils.GetTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class RadioInfoServiceImpl implements RadioInfoService {

    @Autowired
    private RadioInfoRepository radioInfoRepository;

    @Autowired
    private RadioAnchorLetterRepository radioAnchorLetterRepository;

    @Autowired
    private UserRepository userRepository;

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

    /**
     * 改变在线状态
     * @param id
     * @param showState
     */
    @Override
    public void updateShowState(Integer id, Integer showState) {
        RadioInfo radioInfo = radioInfoRepository.findOne(id);
        radioInfo.setShowState(showState);
    }

    /**
     * 查询所有电台
     * @return
     */
    @Override
    public List<RadioInfo> findAllRadioInfo() {
        return radioInfoRepository.findAll();
    }

    /**
     * 通过id查询电台
     * @param id
     * @return
     */
    @Override
    public RadioInfo findOneRadio(Integer id) {
        return radioInfoRepository.findOne(id);
    }

    /**
     * 查找所有电台
     * @param pageable
     * @return
     */
    @Override
    public Page<RadioInfo> findAllRadioInfo(Pageable pageable) {
        Page<RadioInfo> radioInfoPage = radioInfoRepository.findAll(pageable);
        return radioInfoPage;
    }

    /**
     * 删除电台
     * @param id
     */
    @Override
    public void deleteRadio(Integer id) {
        radioInfoRepository.delete(id);
    }

    /**
     * 分页查询在线电台
     * @param pageable
     * @param showState
     * @return
     */
    @Override
    public Page<RadioInfo> findRadioByShowState(Pageable pageable, Integer showState) {
        Page<RadioInfo> radioInfoPage = radioInfoRepository.findAllByShowState(pageable,showState);
        return radioInfoPage;
    }

    /**
     * 添加用户给电台主播发送的留言
     * @param radioAnchorLetterParam
     */
    @Override
    public void addRadioAnchorLetter(RadioAnchorLetterParam radioAnchorLetterParam) {
        Long time = System.currentTimeMillis()/1000;
        RadioAnchorLetter radioAnchorLetter = new RadioAnchorLetter();
        BeanUtils.copyProperties(radioAnchorLetterParam,radioAnchorLetter);
        radioAnchorLetter.setTime(System.currentTimeMillis());
        radioAnchorLetter.setDay(GetTimeUtil.getZeroDateFormat(time));
        radioAnchorLetterRepository.save(radioAnchorLetter);
    }

    /**
     * 通过时间和电台查询留言内容
     * @param day
     * @param radioId
     * @return
     */
    @Override
    public PageDTO<RadioAnchorLetterDTO> findAllByTimeAndRadioId(Pageable pageable,String day, Integer radioId) {
        Page<RadioAnchorLetter> anchorLetters = null;
        if (radioId==0){
            anchorLetters = radioAnchorLetterRepository.findAllByDay(pageable,day);
        }else {
            anchorLetters  = radioAnchorLetterRepository.findAllByDayAndRadioId(pageable,day,radioId);
        }

        PageDTO<RadioAnchorLetterDTO> pageDTO = new PageDTO<>();
        List<RadioAnchorLetterDTO> list = new ArrayList<>();
        if (!anchorLetters.getContent().isEmpty()) {
            pageDTO.setTotalPages(anchorLetters.getTotalPages());
            anchorLetters.getContent().forEach(l->{
                RadioAnchorLetterDTO radioAnchorLetterDTO = new RadioAnchorLetterDTO();
                BeanUtils.copyProperties(l,radioAnchorLetterDTO);
                User user = userRepository.findOne(l.getUserId());
                RadioInfo radioInfo = radioInfoRepository.findOne(l.getRadioId());
                radioAnchorLetterDTO.setRadioName(radioInfo.getRadioName());
                radioAnchorLetterDTO.setUserName(user.getNikeName());
                radioAnchorLetterDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                list.add(radioAnchorLetterDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }
}
