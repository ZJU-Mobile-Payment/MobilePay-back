package com.servlet;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class LoginTest
 */

public class Question extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Question() {
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

        int id = Integer.parseInt(request.getParameter("id"));
        int state = Integer.parseInt(request.getParameter("state"));
        String name = request.getParameter("name").trim();
        String IDcard = request.getParameter("IDcard").trim();
        String telephone = request.getParameter("telephone").trim();
        String location = request.getParameter("loc").trim();

        String uri = "jdbc:mysql://47.106.195.214:3306/mobile_payment?serverTimezone=GMT%2B8&useSSL=false";



        try {
            con = DriverManager.getConnection(uri,"root","123456");
            String conditionn = "select * from healthcode where id = '"+id+"'";
            sql = con.prepareStatement(conditionn);
            ResultSet rSet = sql.executeQuery(conditionn);
            if(rSet.next()) {
                out.println("update code!");
                String condition4 = "update healthcode set state='"+state+"',name='"+name+"',IDcard='"+IDcard+"',telephone='"+telephone+"',hometown='"+location+"'where id='"+id+"'";
                sql = con.prepareStatement(condition4);
                int result=sql.executeUpdate();
                if(result!=0) {
                    out.println("update successfully!");
                }
                else {
                    out.println("can not update!");
                }
            }
            else {
                out.println("insert new code!");
                String condition = "insert into healthcode values(?,?,?,?,?,?)";
                sql = con.prepareStatement(condition);
                sql.setInt(1, id);
                sql.setString(2, location);
                sql.setString(3, name);
                sql.setString(4, IDcard);
                sql.setInt(5, state);
                sql.setString(6, telephone);
                int result=sql.executeUpdate();
                if(result!=0) {
                    out.println("state check!");
                }
                else {
                    out.println("state filed!");
                }
            }

            String condition="insert into location values(?,?,?,?)";
            sql = con.prepareStatement(condition);
            sql.setInt(1, id);
            sql.setString(2, location);
            sql.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            sql.setInt(4, 1);
            int result=sql.executeUpdate();
            if(result!=0) {
                out.println("location check!");
            }
            else {
                out.println(" location filed!");
            }


            if(state==2){
                String condition2 = "select busid from Passenger where userid = '"+id+"'";
                sql = con.prepareStatement(condition2);
                rSet = sql.executeQuery(condition2);

                while(rSet.next()){
                    int busid = rSet.getInt(1);
                    String condition3 = "select userid,datetime from Passenger where busid = '"+busid+"'";
                    out.println(busid);
                    sql = con.prepareStatement(condition3);
                    ResultSet res = sql.executeQuery(condition3);
                    while(res.next()){
                        int userid = res.getInt(1);
                        Date date1 = res.getDate(2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, -14);
                        Date date2 = calendar.getTime();

                        if(date2.before(date1)){
                            out.println(userid);
                            out.println(id);
                            if(userid!=id){
                                String condition4 = "update healthcode set state='4' where id = '"+userid+"'"+"and state='1'";
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
