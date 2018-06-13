package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.AttentionVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.Attention;
import cn.popo.news.core.entity.common.PrivateLetter;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.repository.AttentionRepository;
import cn.popo.news.core.repository.PrivateLetterRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.AgoAttentionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/6/1 16:58
 * @Desc   前台关注逻辑控制中心
 */


@Transactional
@Service
public class   AgoAttentionServiceImpl implements AgoAttentionService {

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivateLetterRepository privateLetterRepository;

    /**
     *添加关注
     */
    @Override
    public AttentionVO addAttention(String aid, String fid) {
        //添加关注人数，粉丝人数
        User user1 = userRepository.findOne(fid);
        Integer fansCounts = user1.getFansCounts();
        user1.setFansCounts(fansCounts+1);
        User user2 = userRepository.findOne(aid);
        Integer followCounts = user2.getFollowCounts();
        user2.setFansCounts(followCounts+1);

        Attention attention = new Attention();
        attention.setAid(aid);
        attention.setFid(fid);
        attention.setTime(System.currentTimeMillis());
        attentionRepository.save(attention);
        Attention attention1 = attentionRepository.findAllByAidAndFid(aid,fid);
        AttentionVO attentionVO = new AttentionVO();
        BeanUtils.copyProperties(attention1,attentionVO);
        attentionVO.setAttention(attention1.getId());
        attentionVO.setAttentionToo(1);
        return attentionVO;
    }


    /**
     * 取消关注
     */
    @Override
    public void deleteAttention(Integer attentionId) {
        //添加关注人数，粉丝人数
        Attention attention = attentionRepository.getOne(attentionId);
        User user1 = userRepository.findOne(attention.getFid());
        Integer fansCounts = user1.getFansCounts();
        user1.setFansCounts(fansCounts-1);
        User user2 = userRepository.findOne(attention.getAid());
        Integer followCounts = user2.getFollowCounts();
        user2.setFansCounts(followCounts-1);

        attentionRepository.delete(attentionId);

    }


    /**
     * 查看是否关注
     */
    @Override
    public Integer findAttention(String aid, String fid) {
        Attention attention = attentionRepository.findAllByAidAndFid(aid,fid);
        if(attention!=null){
            return attention.getId();
        }else {
            return 0;
        }
    }


    /**
     * 关注人数
     */
    @Override
    public Integer findAttentionNum(String aid) {
        userRepository.findOne(aid).setFollowCounts(attentionRepository.findAllByAid(aid).size());
        return attentionRepository.findAllByAid(aid).size();
    }


    /**
     * 粉丝人数
     */
    @Override
    public Integer findFansNum(String fid) {
        userRepository.findOne(fid).setFansCounts(attentionRepository.findAllByFid(fid).size());
        return attentionRepository.findAllByFid(fid).size();
    }


    /**
     * 关注查询
     */
    @Override
    public PageDTO<AttentionVO> findAllAttention(Pageable pageable, String aid) {
        PageDTO<AttentionVO> pageDTO = new PageDTO<>();
        Page<Attention> attentionPage = attentionRepository.findAllByAid(pageable, aid);
        List<AttentionVO> list = new ArrayList<AttentionVO>();
        if (attentionPage != null) {
            pageDTO.setTotalPages(attentionPage.getTotalPages());
            if (!attentionPage.getContent().isEmpty()) {
                attentionPage.getContent().forEach(l -> {
                    AttentionVO attentionVO = new AttentionVO();
                    BeanUtils.copyProperties(l,attentionVO);
                    List<PrivateLetter> privateLetterList = privateLetterRepository.findAllByUidAndUserIdAndState(aid,l.getFid(),0);
                    attentionVO.setUnread(privateLetterList.size());
                    User user = userRepository.findOne(l.getFid());
                    if(user.getAvatar()!=null){
                        attentionVO.setByAvatar(user.getAvatar());
                    }
                    attentionVO.setByNickName(user.getNikeName());
                    Attention attention = attentionRepository.findAllByAidAndFid(l.getFid(),aid);
                    if (attention!=null){
                        attentionVO.setAttentionToo(1);
                    }else {
                        attentionVO.setAttentionToo(0);
                    }
                    Attention attention1 = attentionRepository.findAllByAidAndFid(aid,l.getFid());
                    if (attention1!=null){
                        attentionVO.setAttention(attention1.getId());
                    }else {
                        attentionVO.setAttention(0);
                    }
                    list.add(attentionVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 其他用户关注查询
     */
    @Override
    public PageDTO<AttentionVO> findOtherUserAttention(Pageable pageable, String aid,String fid) {
        PageDTO<AttentionVO> pageDTO = new PageDTO<>();
        Page<Attention> attentionPage = attentionRepository.findAllByAid(pageable, aid);
        List<Attention> attentionList = attentionRepository.findAllByAid(aid);
        List<AttentionVO> list = new ArrayList<AttentionVO>();
        if (attentionPage != null) {
            pageDTO.setTotalPages(attentionPage.getTotalPages());
            if (!attentionPage.getContent().isEmpty()) {
                attentionPage.getContent().forEach(l -> {

                    AttentionVO attentionVO = new AttentionVO();
                    BeanUtils.copyProperties(l,attentionVO);
                    if (attentionList.contains(l)){
                        attentionVO.setAttention(1);
                        Attention attention = attentionRepository.findAllByAidAndFid(l.getFid(),fid);
                        if (attention!=null){
                            attentionVO.setAttentionToo(1);
                        }else {
                            attentionVO.setAttentionToo(0);
                        }
                    }else {
                        attentionVO.setAttention(0);
                    }



                    User user = userRepository.findOne(l.getFid());
                    attentionVO.setByAvatar(user.getAvatar());
                    attentionVO.setByNickName(user.getNikeName());

                    list.add(attentionVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }


    /**
     * 粉丝查询
     */
    @Override
    public PageDTO<AttentionVO> findAllFans(Pageable pageable,String fid) {
        PageDTO<AttentionVO> pageDTO = new PageDTO<>();
        Page<Attention> attentionPage = attentionRepository.findAllByFid(pageable, fid);
        List<AttentionVO> list = new ArrayList<AttentionVO>();
        if (attentionPage != null) {
            pageDTO.setTotalPages(attentionPage.getTotalPages());
            if (!attentionPage.getContent().isEmpty()) {
                attentionPage.getContent().forEach(l -> {
                    AttentionVO attentionVO = new AttentionVO();
                    BeanUtils.copyProperties(l,attentionVO);
                    List<PrivateLetter> privateLetterList = privateLetterRepository.findAllByUidAndUserIdAndState(fid,l.getAid(),0);
                    attentionVO.setUnread(privateLetterList.size());
                    User user = userRepository.findOne(l.getAid());
                    attentionVO.setByAvatar(user.getAvatar());
                    attentionVO.setByNickName(user.getNikeName());
                    Attention attention = attentionRepository.findAllByAidAndFid(fid,l.getAid());
                    if (attention!=null){
                        attentionVO.setAttentionToo(1);
                    }else {
                        attentionVO.setAttentionToo(0);
                    }

                    Attention attention1 = attentionRepository.findAllByAidAndFid(fid,l.getAid());
                    if (attention1!=null){
                        attentionVO.setAttention(attention1.getId());
                    }else {
                        attentionVO.setAttention(0);
                    }

                    list.add(attentionVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

}
