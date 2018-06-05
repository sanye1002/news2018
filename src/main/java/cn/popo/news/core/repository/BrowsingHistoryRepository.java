package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.BrowsingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory,Integer> {
    Page<BrowsingHistory> findAllByUserId(Pageable pageable,String userId);
    BrowsingHistory findAllByUserIdAndArticleId(String userId,String articleId);
}
