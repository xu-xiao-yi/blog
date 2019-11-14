package com.scs.web.blog.domain.vo;

import com.scs.web.blog.entity.Article;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mq_xu
 * @ClassName ArticleVo
 * @Description 文章视图类
 * @Date 22:12 2019/11/11
 * @Version 1.0
 **/
@Data
public class ArticleVo  {
    private Long id;
    private Long userId;
    private String nickname;
    private String avatar;
    private String title;
    private String summary;
    private String thumbnail;
    private Integer likes;
    private Integer comments;
}
