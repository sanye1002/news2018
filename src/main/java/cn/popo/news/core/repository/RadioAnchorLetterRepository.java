package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.RadioAnchorLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadioAnchorLetterRepository extends JpaRepository<RadioAnchorLetter,Integer> {
    Page<RadioAnchorLetter> findAllByDayAndRadioId(Pageable pageable,String day,Integer radioId);
    Page<RadioAnchorLetter> findAllByDay(Pageable pageable,String day);
}
