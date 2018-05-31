package cn.popo.news.core.service.impl;

import cn.popo.news.core.entity.common.Reply;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.ReplyRepository;
import cn.popo.news.core.service.ReplyService;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/28 16:53
 * @Desc    回复逻辑控制中心
 */

@Transactional
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;


    /**
     * 回复上传
     */
    @Override
    public void replySave(ReplyForm replyForm) {
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyForm,reply);
        Long l = System.currentTimeMillis();
        reply.setTime(l);
        reply.setPraiseNum(ResultEnum.SUCCESS.getCode());
        reply.setId(KeyUtil.genUniqueKey());
        reply.setShowState(ResultEnum.PARAM_NULL.getCode());
        replyRepository.save(reply);
    }

    /**
     * 删除某评论的所有回复
     */
    @Override
    public void deleteReplyByCommentId(String commentId) {
        replyRepository.deleteAllByCommId(commentId);
    }

    /**
     * 根据评论id查询所有回复
     */
    @Override
    public List<String> findReplyIdByCommentId(String commentId) {
        List<String> list = new ArrayList<String>();
        replyRepository.findAllByCommId(commentId).forEach(l->{
            list.add(l.getId());
        });
        return list;
    }
}
