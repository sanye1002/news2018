package cn.popo.news.core.service;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.Logo;
import org.springframework.data.domain.Pageable;

public interface LogoService {
    void saveLogo(String logoUrl,Integer showState);
    PageDTO<Logo> findAllLogo(Pageable pageable);
    Logo findShowLogo();
}
