package com.scs.web.blog.service;

import com.scs.web.blog.util.Result;

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
     *
     * @return
     */
    Result getHotArticles();

    /**
     * 获取分页文章
     *
     * @return
     */
    Result getPageArticles(int currentPage, int pageCount);

    /**
     * 获取文章详情
     *
     * @return
     */
    Result getArticle(long id);
}
