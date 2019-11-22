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
        PreparedStatement pst = connection.prepareStatement(sql);
        articleList.forEach(article -> {
            try {
                pst.setLong(1, article.getUserId());
                pst.setLong(2, article.getTopicId());
                pst.setString(3, article.getTitle());
                pst.setString(4, article.getSummary());
                pst.setString(5, article.getThumbnail());
                pst.setString(6, article.getContent());
                pst.setInt(7, article.getLikes());
                pst.setInt(8, article.getComments());
                pst.setObject(9, article.getCreateTime());
                pst.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        int[] result = pst.executeBatch();
        connection.commit();
        DbUtil.close(connection, pst);
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
                "LIMIT 10 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        //调用封装的方法，将结果集解析成List
        List<ArticleVo> articleVoList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> selectByPage(int currentPage, int pageCount) throws SQLException {
        return null;
    }


    @Override
    public List<ArticleVo> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        //从文章、专题、用户表联查出前端需要展示的数据
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.title LIKE ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, "%" + keywords + "%");
        ResultSet rs = pst.executeQuery();
        List<ArticleVo> articleVos = convert(rs);
        DbUtil.close(connection, pst, rs);
        return articleVos;
    }

    @Override
    public List<ArticleVo> selectByTopicId(long topicId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        //从文章、专题、用户表联查出前端需要展示的数据
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.topic_id = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, topicId);
        ResultSet rs = pst.executeQuery();
        List<ArticleVo> articleVos = convert(rs);
        DbUtil.close(connection, pst, rs);
        return articleVos;
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
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        ArticleVo articleVo = convert(rs).get(0);
        rs.previous();
        articleVo.setContent(rs.getString("content"));
        DbUtil.close(connection, pst, rs);
        return articleVo;
    }

    private List<ArticleVo> convert(ResultSet rs) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        try {
            while (rs.next()) {
                ArticleVo articleVo = new ArticleVo();
                articleVo.setId(rs.getLong("id"));
                articleVo.setUserId(rs.getLong("user_id"));
                articleVo.setTopicId(rs.getLong("topic_id"));
                articleVo.setTitle(rs.getString("title"));
                articleVo.setThumbnail(rs.getString("thumbnail"));
                articleVo.setSummary(rs.getString("summary"));
                articleVo.setLikes(rs.getInt("likes"));
                articleVo.setComments(rs.getInt("comments"));
                articleVo.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                articleVo.setNickname(rs.getString("nickname"));
                articleVo.setAvatar(rs.getString("avatar"));
                articleVo.setTopicName(rs.getString("topic_name"));
                articleVo.setLogo(rs.getString("logo"));
                articleVoList.add(articleVo);
            }
        } catch (SQLException e) {
            logger.error("文章数据结果集解析异常");
        }
        return articleVoList;
    }
}
