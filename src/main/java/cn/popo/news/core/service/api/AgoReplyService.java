package cn.popo.news.core.service.api;

import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;

public interface AgoReplyService {
    void replySave(ReplyForm replyForm);
    void replyReportSave(ReplyReportForm replyReportForm);
}
