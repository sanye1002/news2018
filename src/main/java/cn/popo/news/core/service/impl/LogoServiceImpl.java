package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.Logo;
import cn.popo.news.core.repository.LogoRepository;
import cn.popo.news.core.service.LogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LogoServiceImpl implements LogoService {
    @Autowired
    private LogoRepository logoRepository;

    /**
     * 上传logo
     */
    @Override
    public void saveLogo(String logoUrl, Integer showState) {
        logoRepository.findAllByShowState(1).setShowState(0);
        logoRepository.save(new Logo(logoUrl,showState));
    }

    /**
     * 查找所有logo
     */
    @Override
    public PageDTO<Logo> findAllLogo(Pageable pageable) {
        PageDTO pageDTO = new PageDTO();
        Page<Logo> logoPage = logoRepository.findAll(pageable);
        pageDTO.setTotalPages(logoPage.getTotalPages());
        List<Logo> list = new ArrayList<>();
        if (!logoPage.getContent().isEmpty()){
            logoPage.getContent().forEach( l->{
                list.add(l);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     *  查找显示的logo
     */
    @Override
    public Logo findShowLogo() {
        return logoRepository.findAllByShowState(1);
    }
}
