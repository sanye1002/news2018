package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="user_friends")
public class UserFriends {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String friendUserId;

    private String friendNickname;

    private String friendAvatar;
}
