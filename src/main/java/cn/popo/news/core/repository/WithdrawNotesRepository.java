package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.WithdrawNotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 7:27
 * @Description description
 */
public interface WithdrawNotesRepository extends JpaRepository<WithdrawNotes,Integer> {

    Page<WithdrawNotes> findAllByCheckStatusAndResultStatus(Pageable pageable,Integer checkStatus,Integer resultStatus);

    Page<WithdrawNotes> findAllByUserId(Pageable pageable,String userId);

}
