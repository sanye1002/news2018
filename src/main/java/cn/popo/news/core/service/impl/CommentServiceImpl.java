package cn.popo.news.core.service.impl;

import cn.popo.news.core.entity.common.Comment;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.CommentRepository;
import cn.popo.news.core.service.CommentService;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/29 14:15
 * @Desc    评论逻辑控制中心
 */

@Transactional
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;



    /**
     * 删除评论-根据文章id
     */
    @Override
    public void deleteComment(String articleId) {
        commentRepository.deleteAllByAid(articleId);
    }

    /**
     * 根据文章id查询所有评论的id
     */
    @Override
    public List<String> findCommentIdByArticleId(String articleId) {
        List<String> list = new ArrayList<String>();
        commentRepository.findAllByAid(articleId).forEach(l->{
            list.add(l.getId());
        });
        return list;
    }
}
