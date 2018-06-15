package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.entity.common.SearchWords;
import cn.popo.news.core.repository.SearchWordsRepository;
import cn.popo.news.core.service.api.SearchWordsService;
import cn.popo.news.core.utils.SortTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/6/15 16:24
 * @Desc    搜索关键词逻辑控制中心
 */

@Transactional
@Service
public class SearchWordsServiceImpl implements SearchWordsService {
    @Autowired
    private SearchWordsRepository searchWordsRepository;

    /**
     * 添加搜索输入的内容
     */
    @Override
    public void saveSearchWords(String content) {
        SearchWords searchWords = searchWordsRepository.findAllByContent(content);
        SearchWords searchWords1 = new SearchWords();
        if (searchWords!=null){
            searchWords.setSearchCount(searchWords.getSearchCount()+1);
        }else {
            searchWords1.setContent(content);
            searchWords1.setSearchCount(1);
            searchWordsRepository.save(searchWords1);
        }

    }

    /**
     * 查找搜索量最多的6个词
     */
    @Override
    public List<SearchWords> findHotSearchWords() {
        PageRequest pageRequest = new PageRequest(0,6,SortTools.basicSort("desc","searchCount"));
        Page<SearchWords> searchWordsPage = searchWordsRepository.findAll(pageRequest);
        List<SearchWords> list = new ArrayList<>();
        searchWordsPage.getContent().forEach(l->{
            SearchWords searchWords = new SearchWords();
            BeanUtils.copyProperties(l,searchWords);
            list.add(searchWords);
        });
        return list;
    }
}
