package com.scs.web.blog.dao;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class ArticleDaoTest {

    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();

    @Test
    public void batchInsert() throws SQLException {
        int[] result = articleDao.batchInsert(JSoupSpider.getArticles());
        System.out.println(result.length);
    }

    @Test
    public void selectHotArticles() throws SQLException {
        List<ArticleVo> articleVoList = articleDao.selectHotArticles();
        articleVoList.forEach(System.out::println);
    }

    @Test
    public void getArticle() throws SQLException {
        ArticleVo article = articleDao.getArticle(11);
        System.out.println(article);
    }
}