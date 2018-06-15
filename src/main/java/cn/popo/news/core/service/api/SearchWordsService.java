package cn.popo.news.core.service.api;

import cn.popo.news.core.entity.common.SearchWords;

import java.util.List;

public interface SearchWordsService {
    void saveSearchWords(String content);
    List<SearchWords> findHotSearchWords();
}
