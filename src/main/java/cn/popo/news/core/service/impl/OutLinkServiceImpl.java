package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.OutLink;
import cn.popo.news.core.entity.param.OutLinkParam;
import cn.popo.news.core.repository.OutLinkRepository;
import cn.popo.news.core.service.OutLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-13 下午 4:44
 * @Description description
 */

@Service
@Transactional
public class OutLinkServiceImpl implements OutLinkService {

    @Autowired
    private OutLinkRepository outLinkRepository;

    /**
     * 添加链接
     * @param outLinkParam
     */
    @Override
    public void addLink(OutLinkParam outLinkParam) {
        OutLink outLink = new OutLink();
        if(outLinkParam.getId() == null){
            BeanUtils.copyProperties(outLinkParam,outLink);
            outLinkRepository.save(outLink);
        }else {
            outLink = outLinkRepository.findOne(outLinkParam.getId());
            outLink.setLink(outLinkParam.getLink());
            outLink.setLinkDesc(outLinkParam.getLinkDesc());
            outLink.setRanking(outLinkParam.getRanking());
        }
    }

    /**
     * 查询所有链接
     * @param pageable
     * @return
     */
    @Override
    public Page<OutLink> findAllOutLink(Pageable pageable) {
        Page<OutLink> outLinkPage = outLinkRepository.findAll(pageable);

        return outLinkPage;
    }

    /**
     * 查询所有链接
     * @return
     */
    @Override
    public List<OutLink> findAll() {
        return outLinkRepository.findAll();
    }

    /**
     * 通过id查链接
     * @param id
     * @return
     */
    @Override
    public OutLink findOneLine(Integer id) {
        return outLinkRepository.findOne(id);
    }

    /**
     * 通过id删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        outLinkRepository.delete(id);
    }
}
