package com.scs.web.blog.dao;

import com.scs.web.blog.entity.Topic;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName TopicDao
 * @Description TODO
 * @Date 1:01 2019/11/14
 * @Version 1.0
 **/
public interface TopicDao {
    int[] batchInsert(List<Topic> topicList) throws SQLException;
}
