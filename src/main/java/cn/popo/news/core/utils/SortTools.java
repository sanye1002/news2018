package cn.popo.news.core.utils;

import org.springframework.data.domain.Sort;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-08 下午 3:34
 */
public class SortTools {
    public static Sort basicSort() {
        return basicSort("desc", "id");
    }

    public static Sort basicSort(String orderType, String orderField) {
        Sort sort = new Sort(Sort.Direction.fromString(orderType), orderField);
        return sort;
    }

}
