package com.servlet;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BindDAO {
    //查询绑定关系是否存在，存在返回具体Bind对象，否则为空。
    public static Bind queryBind(int usrid) {
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from CardBind where usrid=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, usrid);

            resultSet = preparedStatement.executeQuery();                  //执行查找语句，获得返回信息
            Bind bind = new Bind();
            if (resultSet.next()) {                                        //生成User对象并返回
                bind.setId(resultSet.getInt("usrid"));           //设置账户、密码，根据返回的内容还可以设置其他信息
                bind.setCard(resultSet.getString("card"));
                return bind;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);               //关闭连接
        }
    }

    //查询绑定记录是否已经存在
    public static Bind queryBind(int usrid, String card){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from CardBind where usrid=? and card=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, usrid);
            preparedStatement.setString(2, card);

            resultSet = preparedStatement.executeQuery();                  //执行查找语句，获得返回信息
            Bind bind = new Bind();
            if (resultSet.next()) {                                        //生成User对象并返回
                bind.setId(resultSet.getInt("usrid"));           //设置账户、密码，根据返回的内容还可以设置其他信息
                bind.setCard(resultSet.getString("card"));
                return bind;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);               //关闭连接
        }
    }

    //查询其他绑定的信息，返回json格式
    public static JSONObject queryBind() {
        String sql1="select * from CardBind"; //sql语句
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        Map<String, String> message = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            preparedStatement = connection.prepareStatement(sql1);
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            for(int i = 1;resultSet.next();i++) {
                message.put("bindid", String.valueOf(resultSet.getInt("bindid")));
                message.put("card", ""+resultSet.getString("card"));
                message.put("usrid", ""+resultSet.getInt("usrid"));
                jsonObject.put(i, message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBManager.closeAll(connection, preparedStatement, resultSet);
        return jsonObject;
    }

    //插入绑定信息
    public static String createBind(int usrid, String card){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        //SQL语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("insert into CardBind (usrid, card) values (?, ?)");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, usrid);
            preparedStatement.setString(2, card);
            preparedStatement.executeUpdate();
            return "绑定成功";
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "绑定失败";
        } finally {
            DBManager.closeAll(connection, preparedStatement);               //关闭连接
        }
    }

}
