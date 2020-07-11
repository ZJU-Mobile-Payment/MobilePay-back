package com.servlet;

import net.sf.json.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;
public class AdminStaticsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码
            int userid = Integer.parseInt(request.getParameter("uid").trim());
            int busid = Integer.parseInt(request.getParameter("bid").trim());
            Date currentDate = new Date(System.currentTimeMillis());
            //初始化
            show a = new show();
            a.setBusid(busid);
            a.setUserid(userid);git add .
            a.setStarttime(currentDate);
            //a.setPeriod(period);

            //存储数据库查询结果
            List<Map<String,String>> lists=new ArrayList<Map<String,String>>();
            lists = a.query(1);
            Map<String,String> maps=new  LinkedHashMap<String,String>();
            solve b = new solve();
            b.setPara1("healthstate");
            //b.setPara2("datetime");
            b.setLs(lists);
            maps = b.extract();

           /* draw d = new draw();
            d.setdata(maps);
            d.drawpiechart();*/


            JSONObject js = new JSONObject();
            js = JSONObject.fromObject(maps);

            out.write(js.toString());
            System.out.println(js.toString());

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
