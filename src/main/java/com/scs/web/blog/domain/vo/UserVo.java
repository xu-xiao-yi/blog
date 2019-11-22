package com.scs.web.blog.domain.vo;

import com.scs.web.blog.domain.dto.SimpleUser;
import com.scs.web.blog.entity.Article;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName UserVo
 * @Description 用户视图类，包括用户自身信息、发表的所有文章、所有粉丝和关注的人
 * @Date 2019/11/16
 * @Version 1.0
 **/
@Data
public class UserVo {
    private Long id;
    private String mobile;
    private String password;
    private String nickname;
    private String avatar;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String introduction;
    private String banner;
    private String email;
    private String homepage;
    private Short follows;
    private Short fans;
    private Short articles;
    private LocalDateTime createTime;
    private Short status;
    private List<Article> articleList;
    private List<SimpleUser> simpleUserList;
}
