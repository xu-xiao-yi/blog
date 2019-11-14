package com.scs.web.blog.dao;

import com.scs.web.blog.dao.impl.ArticleDaoImpl;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.DataUtil;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleDaoTest {

    private static Logger logger = LoggerFactory.getLogger(ArticleDaoTest.class);
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();

    @Test
    public void batchInsert() throws SQLException {
        int[] result = articleDao.batchInsert(JSoupSpider.getArticles());
        assertEquals(52, result.length);
    }

    @Test
    public void selectHotArticles() throws SQLException {
        List<ArticleVo> articleVoList = articleDao.selectHotArticles();
        articleVoList.forEach(a -> System.out.println(a));
    }
}