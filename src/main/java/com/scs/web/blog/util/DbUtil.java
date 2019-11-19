package com.scs.web.blog.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author mq_xu
 * @ClassName JSoupSpider
 * @Description 数据库连接工具类
 * @Date 9:13 2019/11/7
 * @Version 1.0
 **/
public class DbUtil {

    //    private static String URL;
//    private static String USERNAME;
//    private static String PASSWORD;
//    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);


//    /**
//     * 读取resources目录下的db-config.properties文件
//     */
//    private static ResourceBundle rb = ResourceBundle.getBundle("db-config");
//
//    /**
//     * 私有的构造方法，单例模式，禁止外部创建对象
//     */
//    private DbUtil() {
//    }
//
//    //使用静态块加载驱动程序，只执行一次
//    static {
//        URL = rb.getString("jdbc.url");
//        USERNAME = rb.getString("jdbc.username");
//        PASSWORD = rb.getString("jdbc.password");
//        String driver = rb.getString("jdbc.driver");
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 定义一个获取数据库连接的方法
//     *
//     * @return Connection
//     */
//    public static Connection getConnection() {
//        if (conn == null) {
//            try {
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            } catch (SQLException e) {
//                logger.error(LocalDateTime.now() + "数据库连接失败");
//            }
//        }
//        return conn;
//    }


    /**
     * 私有的构造方法，单例模式，禁止外部创建对象
     */
    private DbUtil() {
    }

    /**
     * 获取数据库连接，遇到深坑，先这样解决了
     * 在Tomcat里配置了MySQL连接
     *
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static Connection getConnection() {
        Context initContext = null;
        Context envContext = null;
        DataSource ds = null;
        try {
            initContext = new InitialContext();
            envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/db_blog");
        } catch (NamingException e) {
            logger.error("上下文初始化失败");
        }
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            logger.error("获取数据库连接失败");
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param rs
     * @param stat
     * @param conn
     */
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, NamingException {
        Connection connection = DbUtil.getConnection();
    }
}