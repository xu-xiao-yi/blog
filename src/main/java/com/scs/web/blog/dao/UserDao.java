package com.scs.web.blog.dao;

import com.scs.web.blog.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName UserDao
 * @Description UserDao数据访问对象接口
 * @Date 10:54 2019/11/9
 * @Version 1.0
 **/
public interface UserDao {
    /**
     * 新增用户
     * @param user
     * @return
     * @throws SQLException
     */
    int insert(User user) throws SQLException;

    /**
     * 批量新增用户
     *
     * @param studentList
     * @return int[]
     * @throws SQLException
     */
    int[] batchInsert(List<User> userList) throws SQLException;

    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     * @throws SQLException
     */
    User findUserByMobile(String mobile) throws SQLException;

    /**
     * 查询热门用户
     * @return
     * @throws SQLException
     */
    List<User> selectHotUsers()throws SQLException;
}
