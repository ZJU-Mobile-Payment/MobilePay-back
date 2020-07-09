package com.servlet;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryHealth extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int usrid;

    public QueryHealth() {
        super();
        // TODO Auto-generated constructor stub
    }

    //查询绑定记录是否已经存在
    public static int queryState(int usrid){
        //连接数据库
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int state = 0;

        //SQL查询语句
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from healthcode where id=?");        //问号？的地方会被id替换

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setInt(1, usrid);

            resultSet = preparedStatement.executeQuery();                  //执行查找语句，获得返回信息
            if (resultSet.next()) {
                state = resultSet.getInt("state");
            }
            return state;
        } catch (SQLException ex) {
            Logger.getLogger(BindDAO.class.getName()).log(Level.SEVERE, null, ex);
            return state;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);               //关闭连接
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        try {
            usrid = Integer.parseInt(request.getParameter("usrid").trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        int state = queryState(usrid);
        params.put("result", ""+state);

        jsonObject.put("params", params);
        out.write(jsonObject.toString());
        System.out.println(jsonObject.toString());
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
