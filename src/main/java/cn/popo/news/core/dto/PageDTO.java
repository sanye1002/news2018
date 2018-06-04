package cn.popo.news.core.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 下午 4:14
 */
@Data
public class PageDTO<T> {
    private int totalPages;
    private int currentPage;
    private List<T> pageContent;
    public PageDTO() {
    }

    public PageDTO(int totalPages, List<T> pageContent) {
        this.totalPages = totalPages;
        this.pageContent = pageContent;
    }

    public PageDTO(int totalPages, int currentPage, List<T> pageContent) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageContent = pageContent;
    }
}
