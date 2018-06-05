package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.DynamicVO;
import cn.popo.news.core.dto.api.PersonalVO;
import cn.popo.news.core.entity.common.BrowsingHistory;
import cn.popo.news.core.entity.common.Dynamic;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.param.PersonalParam;
import cn.popo.news.core.entity.param.UserParam;
import cn.popo.news.core.repository.ArticleRepository;
import cn.popo.news.core.repository.BrowsingHistoryRepository;
import cn.popo.news.core.repository.DynamicRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.SplitUtil;
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
public class AgoPersonalServiceImpl implements AgoPersonalService {

    @Autowired
    private DynamicRepository dynamicRepository;

    @Autowired
    private BrowsingHistoryRepository browsingHistoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 动态保存
     */
    @Override
    public void saveDynamic(String userId, String content, String imgUrl) {
        Long time = System.currentTimeMillis();
        Dynamic dynamic = new Dynamic();
        dynamic.setContent(content);
        dynamic.setTime(time);
        dynamic.setImgUrl(imgUrl);
        dynamic.setUserId(userId);
        dynamicRepository.save(dynamic);
    }


    /**
     * 用户动态查询
     */
    @Override
    public PageDTO<DynamicVO> findAllDynamicByUserId(Pageable pageable, String userId) {

        PageDTO<DynamicVO> pageDTO = new PageDTO<>();
        Page<Dynamic> dynamicPage = dynamicRepository.findAllByUserId(pageable, userId);
        Long time = System.currentTimeMillis();
        List<DynamicVO> list = new ArrayList<DynamicVO>();
        if (dynamicPage != null) {
            pageDTO.setTotalPages(dynamicPage.getTotalPages());
            if (!dynamicPage.getContent().isEmpty()) {
                dynamicPage.getContent().forEach(l -> {
                    DynamicVO dynamicVO = new DynamicVO();
                    BeanUtils.copyProperties(l,dynamicVO);
                    if(l.getImgUrl()!=null){
                        dynamicVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }
                    dynamicVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time, l.getTime()));
                    list.add(dynamicVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 浏览记录小于等于6条
     */
    @Override
    public PageDTO<String> findSixBrowsingHistory(Pageable pageable, String userId) {
        PageDTO<String> pageDTO = new PageDTO<>();
        Page<BrowsingHistory> browsingHistories = browsingHistoryRepository.findAllByUserId(pageable,userId);
        List<String> list = new ArrayList<>();
        if (browsingHistories != null) {
            pageDTO.setTotalPages(browsingHistories.getTotalPages());
            if (!browsingHistories.getContent().isEmpty()) {
                browsingHistories.getContent().forEach(l -> {
                    list.add(articleRepository.findOne(l.getArticleId()).getTitle());
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }


    /**
     * 查找个人中心页面信息
     */
    @Override
    public PersonalVO findUserInfoByUserId(String userId) {
        PersonalVO personalVO = new PersonalVO();
        User user = userRepository.findOne(userId);
        BeanUtils.copyProperties(user,personalVO);
        return personalVO;
    }

    /**
     * 修改用户信息
     */
    @Override
    public void updateUserInfo(PersonalParam personalParam) {
        User user = userRepository.findOne(personalParam.getUserId());
        user.setNikeName(personalParam.getNikeName());
        user.setUpdateDate(GetTimeUtil.getTime());
        user.setQQ(personalParam.getQQ());
        user.setSex(personalParam.getSex());
        user.setEmail(personalParam.getEmail());
        user.setBirthday(personalParam.getBirthday());
        user.setSignature(personalParam.getSignature());
        user.setBackgroundImg(personalParam.getBackgroundImg());
    }


}
