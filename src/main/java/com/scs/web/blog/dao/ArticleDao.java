package com.scs.web.blog.dao;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName ArticleDao
 * @Description 文章Dao接口
 * @Date 7:11 2019/11/10
 * @Version 1.0
 **/
public interface ArticleDao {
    /**
     * 批量新增文章
     *
     * @param articleList
     * @return
     * @throws SQLException
     */
    int[] batchInsert(List<Article> articleList) throws SQLException;

    /**
     * 查询热门文章，返回视图集合
     * @return
     * @throws SQLException
     */
    List<ArticleVo> selectHotArticles() throws SQLException;

    /**
     * 分页获得文章视图数据
     * @param currentPage
     * @param pageCount
     * @return
     * @throws SQLException
     */
    List<ArticleVo> selectPage(int currentPage,int pageCount) throws SQLException;

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     * @throws SQLException
     */
    ArticleVo getArticle(long id)throws SQLException;
}
