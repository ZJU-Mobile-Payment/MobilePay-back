package com.servlet;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
public class GethstServlet extends HttpServlet {
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

            Gethst g = new Gethst();
            g.setuid(userid);

            int hst = g.query();


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hst", String.valueOf(hst));
          //  jsonObject.put("cost",String.valueOf(cost));

            out.write(String.valueOf(jsonObject));
            // out.write(jsonObject.toString());
            System.out.println(jsonObject.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


}
