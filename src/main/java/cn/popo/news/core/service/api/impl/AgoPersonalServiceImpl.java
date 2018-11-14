package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.common.*;
import cn.popo.news.core.entity.form.DynamicReportForm;
import cn.popo.news.core.entity.param.PersonalParam;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import cn.popo.news.core.utils.SplitUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private DynamicPraiseRepository dynamicPraiseRepository;

    @Autowired
    private PrivateLetterRepository privateLetterRepository;

    @Autowired
    private DynamicReportRepository dynamicReportRepository;

    @Autowired
    private TypeRepository typeRepository;

    /**
     * 动态保存
     */
    @Override
    public void saveDynamic(String userId, String content, String imgUrl,String videoUrl) {
        Long time = System.currentTimeMillis();
        Dynamic dynamic = new Dynamic();
        dynamic.setId(KeyUtil.genUniqueKey());
        dynamic.setContent(content);
        dynamic.setTime(time);
        dynamic.setImgUrl(imgUrl);
        dynamic.setUserId(userId);
        dynamic.setPraiseNum(0);
        dynamic.setVideoUrl(videoUrl);
        dynamicRepository.save(dynamic);
    }


    /**
     * 用户动态查询
     */
    @Override
    public PageDTO<DynamicVO> findAllDynamicByUserId(Pageable pageable, String userId) {

        PageDTO<DynamicVO> pageDTO = new PageDTO<>();
        Page<Dynamic> dynamicPage = null;
        if(!"all".equals(userId)){
            dynamicPage = dynamicRepository.findAllByUserId(pageable, userId);
        }else {
            dynamicPage = dynamicRepository.findAll(pageable);
        }

        Long time = System.currentTimeMillis();
        List<DynamicVO> list = new ArrayList<DynamicVO>();
        if (dynamicPage != null) {
            pageDTO.setTotalPages(dynamicPage.getTotalPages());
            if (!dynamicPage.getContent().isEmpty()) {
                dynamicPage.getContent().forEach(l -> {
                    DynamicVO dynamicVO = new DynamicVO();
                    BeanUtils.copyProperties(l,dynamicVO);
                    User user = userRepository.findOne(l.getUserId());
                    dynamicVO.setUsername(user.getNikeName());
                    dynamicVO.setAvatar(user.getAvatar());

                    if(l.getImgUrl()!=null){
                        dynamicVO.setImgList(SplitUtil.splitComme(l.getImgUrl()));
                    }

                    if (l.getVideoUrl()!=null){
                        dynamicVO.setVideoList(SplitUtil.splitComme(l.getVideoUrl()));
                    }
                    dynamicVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time, l.getTime()));
                    DynamicPraise dynamicPraise = dynamicPraiseRepository.findAllByUidAndDynamicId(userId,l.getId());
                    if (dynamicPraise!=null){
                        dynamicVO.setGoodFlag(dynamicPraise.getId());
                    }else {
                        dynamicVO.setGoodFlag(0);
                    }
                    dynamicVO.setGood(l.getPraiseNum());
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
    public PageDTO<LookVO> findSixBrowsingHistory(Pageable pageable, String userId) {
        PageDTO<LookVO> pageDTO = new PageDTO<>();
        Page<BrowsingHistory> browsingHistories = browsingHistoryRepository.findAllByUserId(pageable,userId);
        List<LookVO> list = new ArrayList<>();
        if (browsingHistories != null) {
            pageDTO.setTotalPages(browsingHistories.getTotalPages());
            if (!browsingHistories.getContent().isEmpty()) {
                browsingHistories.getContent().forEach(l -> {
                    LookVO lookVO = new LookVO();
                    ArticleInfo articleInfo = articleRepository.findOne(l.getArticleId());
                    lookVO.setId(articleInfo.getArticleId());
                    lookVO.setType(articleInfo.getTypeId());
                    lookVO.setTitle(articleInfo.getTitle());
                    list.add(lookVO);
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

        if(personalParam.getNikeName()!=null){
            user.setNikeName(personalParam.getNikeName());
        }

        user.setUpdateDate(GetTimeUtil.getTime());

        if(personalParam.getQQ()!=null){
            user.setQQ(personalParam.getQQ());
        }


        if(personalParam.getSex()!=null){
            user.setSex(personalParam.getSex());
        }

        if(personalParam.getEmail()!=null){
            user.setEmail(personalParam.getEmail());
        }

        if(personalParam.getBirthday()!=null){
            user.setBirthday(personalParam.getBirthday());
        }

        if(personalParam.getSignature()!=null){
            user.setSignature(personalParam.getSignature());
        }

        if(personalParam.getBackgroundImg()!=null){
            user.setBackgroundImg(personalParam.getBackgroundImg());
        }

        if(personalParam.getAvatar()!=null){
            user.setAvatar(personalParam.getAvatar());
        }

    }

    /**
     * 点赞
     */
    @Override
    public void dynamicPraise(String userId, String dynamicId) {
        Dynamic dynamic = dynamicRepository.findOne(dynamicId);
        Integer praiseNum = dynamic.getPraiseNum()+1;
        dynamic.setPraiseNum(praiseNum);
        DynamicPraise dynamicPraise = new DynamicPraise();
        dynamicPraise.setDynamicId(dynamicId);
        dynamicPraise.setUid(userId);
        dynamicPraiseRepository.save(dynamicPraise);
    }


    /**
     * 查找用户（用户名模糊查找）
     */
    @Override
    public PageDTO<Author> findUserByUserTypeAndNickNameLike(Pageable pageable,String userType, String content) {
        PageDTO<Author> pageDTO = new PageDTO<>();
        Page<User> userPage = userRepository.findAllByUserTypeAndNikeNameContaining(pageable,userType,content);
        List<Author> list = new ArrayList<Author>();
        if (userPage != null) {
            pageDTO.setTotalPages(userPage.getTotalPages());
            if (!userPage.getContent().isEmpty()) {
                userPage.getContent().forEach(l -> {
                    Author author = new Author();
                    BeanUtils.copyProperties(l,author);
                    author.setName(l.getNikeName());
                    list.add(author);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }


    /**
     * 通信保存
     */
    @Override
    public void saveCommunication(String uid, String userId, String sendMessage,Integer state) {
        PrivateLetter privateLetter = new PrivateLetter();
//        privateLetter.setId(KeyUtil.genUniqueKey());
        privateLetter.setTime(System.currentTimeMillis());
        privateLetter.setUid(uid);
        privateLetter.setUserId(userId);
        privateLetter.setSendMessage(sendMessage);
        privateLetter.setId(KeyUtil.genUniqueKey());
        privateLetter.setState(state);
        privateLetterRepository.save(privateLetter);

    }

    /**
     * 查看用户通信
     */
    @Override
    public PageDTO<PrivateLetterVO> findUserCommunication(Pageable pageable, String uid, String userId) {
        PageDTO<PrivateLetterVO> pageDTO = new PageDTO<>();
        Page<PrivateLetter> privateLetterPage = privateLetterRepository.findAllByUidAndUserIdOrUidAndUserId(pageable,uid,userId,userId,uid);

        List<PrivateLetterVO> list = new ArrayList<PrivateLetterVO>();
        if (privateLetterPage != null) {
            pageDTO.setTotalPages(privateLetterPage.getTotalPages());
            if (!privateLetterPage.getContent().isEmpty()) {
                privateLetterPage.getContent().forEach(l -> {
                    PrivateLetterVO privateLetterVO = new PrivateLetterVO();
                    privateLetterVO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                    privateLetterVO.setMessage(l.getSendMessage());
                    privateLetterVO.setId(l.getUserId());
                    privateLetterVO.setAvatar(userRepository.findOne(l.getUserId()).getAvatar());
                    privateLetterVO.setUsername(userRepository.findOne(l.getUserId()).getNikeName());
                    list.add(privateLetterVO);
                });
            }
        }

        Collections.reverse(list);
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 改变通信状态
     */
    @Override
    public void updateCommunicationLookState(String uid, String userId) {
        List<PrivateLetter> privateLetterList = privateLetterRepository.findAllByUidAndUserIdAndState(userId,uid,0);
        if(privateLetterList!=null){
            privateLetterList.forEach(l->{
                l.setState(1);
            });
        }
    }


    /**
     * 动态举报上传
     */
    @Override
    public void dynamicReportSave(DynamicReportForm dynamicReportForm) {
        DynamicReport dynamicReport = new DynamicReport();
        dynamicReportForm.setContent(KeyWordFilter.doFilter(dynamicReportForm.getContent()));
        BeanUtils.copyProperties(dynamicReportForm, dynamicReport);
        dynamicReport.setInfo(dynamicReportForm.getContent());
        dynamicReport.setDid(dynamicReportForm.getDynamicId());
        Long l = System.currentTimeMillis();
        dynamicReport.setTime(l);
//        dynamicReport.setId(KeyUtil.genUniqueKey());
        dynamicReport.setDisposeState(ResultEnum.SUCCESS.getCode());
        dynamicReportRepository.save(dynamicReport);
    }

    /**
     * 添加匿名用户
     * @return
     */
    @Override
    public String DefaultUser() {
        List<String> nameList = new ArrayList<>();
        nameList.add("匿名");
        User user = new User();
        String id = KeyUtil.genUniqueKey();
        user.setUserId(id);
        user.setUserType("1");
        user.setAvatar("https://p0.cdrysj.com/po/read/img/user/model.png");
        user.setName(nameList.get(0)+id);
        user.setNikeName(nameList.get(0)+id);
        userRepository.save(user);
        return id;
    }

    /**
     * 添加默认用户
     * @return
     */
    @Override
    public String addDefaultUser(String userName,String userImg){
        User user = new User();
        String id = KeyUtil.genUniqueKey();
        user.setUserId(id);
        user.setUserType("1");
        user.setAvatar(userImg);
        user.setName(userName);
        user.setNikeName(userName);
        userRepository.save(user);
        return id;
    }

    @Override
    public List<Type_info> findAllType() {
        return typeRepository.findAll();
    }
}
