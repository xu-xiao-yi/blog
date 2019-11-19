package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName ArticleDaoImpl
 * @Description 文章Dao接口实现类
 * @Date 2019/11/10
 * @Version 1.0
 **/
public class ArticleDaoImpl implements ArticleDao {
    private static Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

    @Override
    public int[] batchInsert(List<Article> articleList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO t_article (user_id,topic_id,title,summary,thumbnail,content,likes,comments,create_time) VALUES (?,?,?,?,?,?,?,?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        articleList.forEach(article -> {
            try {
                pstmt.setLong(1, article.getUserId());
                pstmt.setLong(2, article.getTopicId());
                pstmt.setString(3, article.getTitle());
                pstmt.setString(4, article.getSummary());
                pstmt.setString(5, article.getThumbnail());
                pstmt.setString(6, article.getContent());
                pstmt.setInt(7, article.getLikes());
                pstmt.setInt(8, article.getComments());
                pstmt.setObject(9, article.getCreateTime());
                pstmt.addBatch();
            } catch (SQLException e) {
                logger.error("批量加入文章数据产生异常");
            }
        });
        int[] result = pstmt.executeBatch();
        connection.commit();
        DbUtil.close(null, pstmt, connection);
        return result;
    }

    @Override
    public List<ArticleVo> selectHotArticles() throws SQLException {
        Connection connection = DbUtil.getConnection();
        //从文章、专题、用户表联查出前端需要展示的数据
        String sql = "SELECT a.id,a.user_id,a.topic_id,a.title,a.summary,a.thumbnail,a.comments,a.likes,a.create_time," +
                "b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "ORDER BY a.comments DESC " +
                "LIMIT 20 ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<ArticleVo> articleVoList = new ArrayList<>(100);
        try {
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getLong("id"));
                article.setUserId(rs.getLong("user_id"));
                article.setTopicId(rs.getLong("topic_id"));
                article.setTitle(rs.getString("title"));
                article.setThumbnail(rs.getString("thumbnail"));
                article.setSummary(rs.getString("summary"));
                article.setLikes(rs.getInt("likes"));
                article.setComments(rs.getInt("comments"));
                article.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                ArticleVo articleVo = new ArticleVo();
                articleVo.setArticle(article);
                articleVo.setNickname(rs.getString("nickname"));
                articleVo.setAvatar(rs.getString("avatar"));
                articleVo.setTopicName(rs.getString("topic_name"));
                articleVo.setLogo(rs.getString("logo"));
                articleVoList.add(articleVo);
            }
        } catch (SQLException e) {
            logger.error("查询文章详情出现异常");
        }
        DbUtil.close(rs, pstmt, connection);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> selectPage(int currentPage, int pageCount) throws SQLException {
        return null;
    }

    @Override
    public ArticleVo getArticle(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.id = ?  ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArticleVo articleVo = null;
        if (rs.next()) {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setUserId(rs.getLong("user_id"));
            article.setTopicId(rs.getLong("topic_id"));
            article.setTitle(rs.getString("title"));
            article.setThumbnail(rs.getString("thumbnail"));
            article.setSummary(rs.getString("summary"));
            article.setLikes(rs.getInt("likes"));
            article.setComments(rs.getInt("comments"));
            article.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            articleVo = new ArticleVo();
            articleVo.setArticle(article);
            articleVo.setNickname(rs.getString("nickname"));
            articleVo.setAvatar(rs.getString("avatar"));
            articleVo.setTopicName(rs.getString("topic_name"));
            articleVo.setLogo(rs.getString("logo"));
        }
        DbUtil.close(rs, pstmt, connection);
        return articleVo;
    }

}
