package com.scs.web.blog.service;

import com.scs.web.blog.domain.vo.ArticleVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName ArticleService
 * @Description TODO
 * @Date 22:48 2019/11/11
 * @Version 1.0
 **/
public interface ArticleService {
    /**
     * 获取热门文章
     * @return
     */
    List<ArticleVo> getHotArticles();
}
