package cn.popo.news.core.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:49
 * @Desc    分类（导航栏）
 */

@Data
@Entity
@Table(name="classify")
@AllArgsConstructor
@NoArgsConstructor
public class Classify {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 列别名称 头条   科技   娱乐  游戏   搞笑  文史   视频
     */
    @JsonProperty("title")
    private String classify;
}
