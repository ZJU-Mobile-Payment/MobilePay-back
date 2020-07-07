package com.servlet;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    //查询账户是否存在，存在返回具体User对象，否则为空。
    public static User queryUser(int id) {
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from user where id=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();                  //执行查找语句，获得返回信息
            User user = new User();
            if (resultSet.next()) {                                        //生成User对象并返回
                user.setId(resultSet.getInt("id"));           //设置账户、密码，根据返回的内容还可以设置其他信息
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setIsadmin(resultSet.getInt("isadmin"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);               //关闭连接
        }
    }

    public static String createUser(int id, String password){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        //SQL查询语句
        Random rm = new Random();
        String username = "用户" + (rm.nextInt(10000)+1);
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("insert into user (id, password, username, isadmin) values (?, ?, ?, 0)");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, username);
            preparedStatement.executeUpdate();
            return "注册成功";
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "注册失败";
        } finally {
            DBManager.closeAll(connection, preparedStatement);               //关闭连接
        }
    }

    public static String updateUser(int id, String password, String username, String email, String phone, String address){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("update user set password=?, username=?, email=?, phone=?, address=? where id=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(6, id);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.executeUpdate();
            return "修改成功";
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "修改失败";
        } finally {
            DBManager.closeAll(connection, preparedStatement);               //关闭连接
        }
    }

    public static String deleteUser(int id){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("delete from user where id=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return "删除成功";
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "删除失败";
        } finally {
            DBManager.closeAll(connection, preparedStatement);               //关闭连接
        }
    }
    /*
    //查询其他用户的信息，返回json格式
    public static JSONObject queryUser() {
        String sql1="select * from user"; //sql语句
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        Map<String, String> message = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            preparedStatement = connection.prepareStatement(sql1);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            for(int i = 1;resultSet.next();i++) {
                message.put("id", String.valueOf(resultSet.getInt("id")));
                message.put("password", ""+resultSet.getDouble("password"));
                message.put("username", ""+resultSet.getDouble("username"));
                message.put("email", resultSet.getString("email"));
                message.put("phone", resultSet.getString("phone"));
                message.put("address", resultSet.getString("address"));
                message.put("isadmin", String.valueOf(resultSet.getInt("isadmin")));
                jsonObject.put(i, message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBManager.closeAll(connection, preparedStatement, resultSet);
        return jsonObject;
    }

    //修改用户信息
    public static void UpdateLocation(String account,String Latitude,String Longitude) {
        Connection connection = com.servlet.DBManager.getConnection();
        PreparedStatement preparedStatement = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("update whoere.email set Latitude = ? ,Longitude = ? where account = ?");
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, Latitude);
            preparedStatement.setString(2, Longitude);
            preparedStatement.setString(3, account);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            com.servlet.DBManager.closeAll(connection, preparedStatement);
        }
    }

    //实现注册账号功能(还有些问题！)
    public static void Register(String account,String password){
        //获得数据库的连接对象
        Connection connection = com.servlet.DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("insert into whoere.email (account,password) values (?,?)");
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            com.servlet.DBManager.closeAll(connection, preparedStatement);
        }
    }
     */

}
