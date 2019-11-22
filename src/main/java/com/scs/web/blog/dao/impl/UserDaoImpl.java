package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.UserDao;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.User;
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
 * @ClassName UserDaoImpl
 * @Description UserDao数据访问对象接口实现类
 * @Date 2019/11/9
 * @Version 1.0
 **/
public class UserDaoImpl implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public int insert(User user) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_user (mobile,password) VALUES (?,?) ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getMobile());
        pst.setString(2, user.getPassword());
        int n = pst.executeUpdate();
        DbUtil.close(connection, pst);
        return n;
    }

    @Override
    public int[] batchInsert(List<User> userList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO t_user (mobile,password,nickname,avatar,gender,birthday,address,introduction,create_time) VALUES (?,?,?,?,?,?,?,?,?) ";
        PreparedStatement pst = connection.prepareStatement(sql);
        userList.forEach(user -> {
            try {
                pst.setString(1, user.getMobile());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getNickname());
                pst.setString(4, user.getAvatar());
                pst.setString(5, user.getGender());
                pst.setObject(6, user.getBirthday());
                pst.setString(7, user.getAddress());
                pst.setString(8, user.getIntroduction());
                pst.setObject(9, user.getCreateTime());
                pst.addBatch();
            } catch (SQLException e) {
                logger.error("批量加入用户数据产生异常");
            }
        });
        int[] result = pst.executeBatch();
        DbUtil.close(connection, pst);
        return result;
    }

    @Override
    public User findUserByMobile(String mobile) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE mobile = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, mobile);
        ResultSet rs = pst.executeQuery();
        User user = convert(rs).get(0);
        DbUtil.close(connection, pst, rs);
        return user;
    }

    @Override
    public List<User> selectHotUsers() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user ORDER BY fans DESC LIMIT 10 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<User> userList = convert(rs);
        DbUtil.close(connection, pst, rs);
        return userList;
    }

    @Override
    public List<User> selectPageUsers(int currentPage, int pageCount) throws SQLException {
        return null;
    }

    @Override
    public UserVo getUser(long id) throws SQLException {
        return null;
    }

    private List<User> convert(ResultSet rs) {
        List<User> userList = new ArrayList<>(50);
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setMobile(rs.getString("mobile"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getString("gender"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setIntroduction(rs.getString("introduction"));
                user.setBanner(rs.getString("banner"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setFollows(rs.getShort("follows"));
                user.setFans(rs.getShort("fans"));
                user.setArticles(rs.getShort("articles"));
                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                user.setStatus(rs.getShort("status"));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("用户数据结果集解析产生异常");
        }
        return userList;
    }
}
