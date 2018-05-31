package cn.popo.news.core.service;

import cn.popo.news.core.entity.common.Comment;
import cn.popo.news.core.entity.form.CommentForm;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/25 18:17
 * @Desc
 */
public interface CommentService {
    void commontSave(CommentForm commentForm);
    void deleteComment(String articleId);
    List<String> findCommentIdByArticleId(String articleId);
}
