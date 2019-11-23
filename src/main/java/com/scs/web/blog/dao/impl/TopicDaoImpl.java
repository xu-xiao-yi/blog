package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.TopicDao;
import com.scs.web.blog.domain.vo.TopicVo;
import com.scs.web.blog.entity.Topic;
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
 * @ClassName TopicDaoImpl
 * @Description TODO
 * @Date 2019/11/16
 * @Version 1.0
 **/
public class TopicDaoImpl implements TopicDao {
    private static Logger logger = LoggerFactory.getLogger(TopicDaoImpl.class);

    @Override
    public void batchInsert(List<Topic> topicList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO t_topic (admin_id,topic_name,logo,description,homepage,articles,follows,create_time) VALUES (?,?,?,?,?,?,?,?) ";
        PreparedStatement pst = connection.prepareStatement(sql);
        topicList.forEach(topic -> {
            try {
                pst.setLong(1, topic.getAdminId());
                pst.setString(2, topic.getTopicName());
                pst.setString(3, topic.getLogo());
                pst.setString(4, topic.getDescription());
                pst.setString(5, topic.getHomepage());
                pst.setInt(6, topic.getArticles());
                pst.setInt(7, topic.getFollows());
                pst.setObject(8, topic.getCreateTime());
                pst.addBatch();
            } catch (SQLException e) {
                logger.error("批量加入专题数据产生异常");
            }
        });
        pst.executeBatch();
        connection.commit();
        DbUtil.close(connection, pst);
    }

    @Override
    public List<Topic> selectAll() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic ORDER BY id ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<Topic> topicList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return topicList;
    }

    @Override
    public List<Topic> selectHotTopics() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic ORDER BY follows DESC LIMIT 8 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<Topic> topicList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return topicList;
    }

    @Override
    public List<Topic> selectByPage(int currentPage, int count) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic  ORDER BY id LIMIT ?,? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, (currentPage - 1) * count);
        pst.setInt(2, count);
        ResultSet rs = pst.executeQuery();
        List<Topic> topicList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return topicList;
    }

    @Override
    public TopicVo getTopic(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        //查询专题详情，包括专题表信息，文章列表，创建者基础信息，关注人列表
        String sql = "SELECT a.*,b.nickname,b.avatar " +
                "FROM t_topic a " +
                "LEFT JOIN t_user b " +
                "ON a.admin_id = b.id " +
                "WHERE a.id = ?  ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        TopicVo topicVo = null;
        if (rs.next()) {
            topicVo = new TopicVo();
            topicVo.setId(rs.getLong("id"));
            topicVo.setAdminId(rs.getLong("admin_id"));
            topicVo.setNickname(rs.getString("nickname"));
            topicVo.setAvatar(rs.getString("avatar"));
            topicVo.setTopicName(rs.getString("topic_name"));
            topicVo.setLogo(rs.getString("logo"));
            topicVo.setDescription(rs.getString("description"));
            topicVo.setHomepage(rs.getString("homepage"));
            topicVo.setArticles(rs.getInt("articles"));
            topicVo.setFollows(rs.getInt("follows"));
            topicVo.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        }
        DbUtil.close(connection, pst, rs);
        return topicVo;
    }

    @Override
    public List<Topic> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic " +
                "WHERE topic_name LIKE ?  OR description LIKE ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, "%" + keywords + "%");
        pst.setString(2, "%" + keywords + "%");
        ResultSet rs = pst.executeQuery();
        List<Topic> topicList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return topicList;
    }

    private List<Topic> convert(ResultSet rs) {
        List<Topic> topicList = new ArrayList<>();
        try {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getLong("id"));
                topic.setAdminId(rs.getLong("admin_id"));
                topic.setTopicName(rs.getString("topic_name"));
                topic.setLogo(rs.getString("logo"));
                topic.setDescription(rs.getString("description"));
                topic.setHomepage(rs.getString("homepage"));
                topic.setArticles(rs.getInt("articles"));
                topic.setFollows(rs.getInt("follows"));
                topic.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                topicList.add(topic);
            }
        } catch (SQLException e) {
            logger.error("专题数据结果集解析产生异常");
        }
        return topicList;
    }
}
