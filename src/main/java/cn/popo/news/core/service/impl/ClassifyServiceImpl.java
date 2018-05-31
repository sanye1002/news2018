package cn.popo.news.core.service.impl;

import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.repository.ClassifyRepository;
import cn.popo.news.core.service.ClassifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    private ClassifyRepository classifyRepository;

    /**
     * 查询所有分类
     */
    @Override
    public List<Classify> findAllClassify(){
        List<Classify> list = classifyRepository.findAll();
        return list;
    }

    /**
     * 更新，保存分类
     */
    @Override
    public void saveClassify(String classify,Integer id) {
        Classify classify1 = new Classify();
        if(id == null){
            classify1.setClassify(classify);
            classifyRepository.save(classify1);
        }else {
            classify1 = classifyRepository.findOne(id);
            classify1.setClassify(classify);
        }

    }

    /**
     * 根据id删除分类
     */
    @Override
    public void deleteClassify(Integer id) {
        classifyRepository.delete(id);
    }

    /**
     * 通过id查找分类
     */
    @Override
    public Classify findClassifyById(Integer id) {
        return classifyRepository.findOne(id);
    }

    /**
     * 分页查找所有分类
     */
    @Override
    public Page<Classify> findClassify(Pageable pageable) {
        return classifyRepository.findAll(pageable);
    }
}
