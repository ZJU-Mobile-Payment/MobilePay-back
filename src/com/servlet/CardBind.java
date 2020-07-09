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

/**
 * Servlet implementation class LoginTest
 */
@WebServlet("/CardBindTest")
public class CardBind extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardBind() {
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
        request.setCharacterEncoding("utf-8");
//        request.setCharacterEncoding("gb2312");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con;
        Statement sql;
        String bank = request.getParameter("bank");
        String cardid = request.getParameter("cardid");
        String usrname = request.getParameter("usrname");

        String uri = "jdbc:mysql://47.106.195.214:3306/mobile_payment?useUnicode=true&characterEncoding=UTF-8";
        try {
            con = DriverManager.getConnection(uri,"root","123456");
            String condition = "select * from BankCard where bank = '"+bank+"' and cardid = '"+cardid+"' and usrname = '"+usrname+"'";

            sql = con.prepareStatement(condition);
            ResultSet rSet = sql.executeQuery(condition);
            if(rSet.next()) {
                out.println("bind successfully!");
            }
            else {
                out.println("can not bind!");
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



