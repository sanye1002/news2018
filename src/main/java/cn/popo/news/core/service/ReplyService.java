package cn.popo.news.core.service;

import cn.popo.news.core.entity.form.ReplyForm;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/25 18:17
 * @Desc
 */
public interface ReplyService {
    void replySave(ReplyForm replyForm);
    void deleteReplyByCommentId(String commentId);
    List<String> findReplyIdByCommentId(String commentId);
}
