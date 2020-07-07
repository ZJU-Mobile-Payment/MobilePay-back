package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager extends HttpServlet {
    ServletConfig config;                                                           //定义一个ServletConfig对象
    private static String username = "root";                                        //定义数据库用户名
    private static String password = "123456";                                      //定义数据库连接密码
    private static String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";  //定义数据库连接URL
    private static Connection connection;                                           //定义连接

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);                                        //继承父类的init()方法
        this.config = config;                                      //获取配置信息
    }

    //连接数据库
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    //关闭连接，ResultSet是返回数据库的查询内容。
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //没有ResultSet的是关闭修改、增加数据操作（因为不用返回结果）
    public static void closeAll(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
