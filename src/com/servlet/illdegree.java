package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Servlet implementation class LoginTest
 */

public class illdegree extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public illdegree() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Connection con;
        PreparedStatement sql;
        int location = Integer.parseInt(request.getParameter("location"));
        out.println(location);
        int degree = Integer.parseInt(request.getParameter("degree"));
        String uri = "jdbc:mysql://47.106.195.214:3306/mobile_payment?serverTimezone=GMT%2B8&useSSL=false";
        try {
            con = DriverManager.getConnection(uri,"root","123456");
            String condition;
            if(degree==1){
                //更新黄码（绿码和橙码的变）
                String conditionn = "select id from healthcode where hometown = '"+location+"' and state ='4'";
                sql = con.prepareStatement(conditionn);
                ResultSet rSet = sql.executeQuery(conditionn);
                while(rSet.next()) {
                    out.println("login successfully!");
                    int id = rSet.getInt(1);
                    condition= "update healthcode set state='3' where hometown = '"+location+"'"+"and id ='"+id+"'";
                    sql = con.prepareStatement(condition);
                    int result=sql.executeUpdate();
                    if(result!=0) {
                        out.println("update successfully!");
                    }
                    else {
                        out.println("can not update!");
                    }
                }
                String conditionnn = "select id from healthcode where hometown = '"+location+"' and state ='1'";
                sql = con.prepareStatement(conditionnn);
                rSet = sql.executeQuery(conditionnn);
                while(rSet.next()) {
                    out.println("login successfully!");
                    int id = rSet.getInt(1);
                    condition= "update healthcode set state='3' where hometown = '"+location+"'"+"and id ='"+id+"'";
                    sql = con.prepareStatement(condition);
                    int result=sql.executeUpdate();
                    if(result!=0) {
                        out.println("update successfully!");
                    }
                    else {
                        out.println("can not update!");
                    }
                }


                //更新location表中的地区state,用户途径该地也变成黄码
                String condition2 = "select id,date from location where location = '"+location+"'";
                sql = con.prepareStatement(condition2);
                rSet = sql.executeQuery(condition2);
                while(rSet.next()){
                    int id = rSet.getInt(1);
                    Date date1 = rSet.getDate(2);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -14);
                    Date date2 = calendar.getTime();

                    if(date2.before(date1)){
                        String condition3 = "update healthcode set state='3' where id = '"+id+"'";
                        String condition4 = "update location set state='1' where id = '"+id+"'";
                        sql = con.prepareStatement(condition3);
                        int result=sql.executeUpdate();
                        if(result!=0) {
                            out.println("update successfully!");
                        }
                        else {
                            out.println("can not update!");
                        }
                        sql = con.prepareStatement(condition4);
                        result=sql.executeUpdate();
                        if(result!=0) {
                            out.println("update successfully!");
                        }
                        else {
                            out.println("can not update!");
                        }

                    }

                }
            }
            else if(degree==2||degree==3){
                condition= "update healthcode set state='1' where hometown = '"+location+"'"+"and state='3'";
                sql = con.prepareStatement(condition);
                int result=sql.executeUpdate();
                if(result!=0) {
                    out.println("update successfully!");
                }
                else {
                    out.println("can not update!");
                }

                String condition2 = "select id from location where location = '"+location+"'";
                sql = con.prepareStatement(condition2);
                ResultSet rSet = sql.executeQuery(condition2);

                while(rSet.next()){
                    int id = rSet.getInt(1);
                    String condition3 = "update location set state='0' where id = '"+id+"'"+"and location='"+location+"'";
                    sql = con.prepareStatement(condition3);
                    result=sql.executeUpdate();
                    if(result!=0) {
                        out.println("update successfully!");
                    }
                    else {
                        out.println("can not update!");
                    }

                    String condition4 = "select * from location where id = '"+id+"'"+"and state=1";
                    sql = con.prepareStatement(condition4);
                    ResultSet rSet2 = sql.executeQuery(condition4);
                    if(rSet2.next()) {
                        out.println("still yellow!");
                    }
                    else {
                        String condition5 = "update healthcode set state='1' where id = '"+id+"'";
                        sql = con.prepareStatement(condition5);
                        result=sql.executeUpdate();
                        if(result!=0) {
                            out.println("update successfully!");
                        }
                        else {
                            out.println("can not update!");
                        }
                        out.println("green!");
                    }
                }
            }

            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
