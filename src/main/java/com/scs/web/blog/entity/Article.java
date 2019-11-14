package com.scs.web.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mq_xu
 * @ClassName Article
 * @Description TODO
 * @Date 21:37 2019/11/9
 * @Version 1.0
 **/
@Data
public class Article {
    private Long id;
    private Long userId;
    private String title;
    private String summary;
    private String thumbnail;
    private String content;
    private Integer likes;
    private Integer comments;
    private LocalDateTime createTime;

}
