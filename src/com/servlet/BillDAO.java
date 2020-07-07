package com.servlet;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDAO {
    //查询用户最近的乘车记录，并返回json对象
    public static JSONObject queryBill(int userid) {
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date time = null;
        SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from bill natural join Bus where userid=?");        //问号？的地方会被id替换

        Map<String, String> message = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, userid);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            resultSet = preparedStatement.executeQuery();
            for(int i = 1;resultSet.next();i++) {
                message.put("billid", ""+String.valueOf(resultSet.getInt("billid")));
                message.put("userid", ""+String.valueOf(resultSet.getInt("userid")));
                message.put("bus", String.valueOf(resultSet.getString("location")) + String.valueOf(resultSet.getInt("busid")) + "路公交");
                message.put("cost", ""+String.valueOf(resultSet.getInt("cost")));
                time = new Date(resultSet.getTimestamp("time").getTime());
                String taketime=formattime.format(time);
                message.put("time", ""+taketime);
                jsonObject.put(i, message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBManager.closeAll(connection, preparedStatement, resultSet);
        return jsonObject;
    }
}
