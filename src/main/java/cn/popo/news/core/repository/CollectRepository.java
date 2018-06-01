package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface CollectRepository extends JpaRepository<Collect,Integer> {
    Collect findAllByUidAndAid(String userId,String articleId);
    Page<Collect> findAllByUidAndTypeId(Pageable pageable, String userId, Integer typeId);
}
