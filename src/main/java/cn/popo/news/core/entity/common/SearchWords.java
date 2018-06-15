package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "search_words")
public class SearchWords {
    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    private Integer searchCount;
}
