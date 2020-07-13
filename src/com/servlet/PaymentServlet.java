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

public class PaymentServlet extends HttpServlet {
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
            int healthstate = Integer.parseInt(request.getParameter("hst").trim());

           //int healthstate = 1;
            //初始化
            pay a = new pay();
            a.setBusid(busid);
            a.setUserid(userid);
            a.setHstate(healthstate);
            a.setCost();
            int cost = a.getBusCost();
            Boolean ispermitted = a.judge();


            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            String hint = null;
            if (!ispermitted) {
                jsonObject.put("result","Payment Failed");
                jsonObject.put("cost","0");
                a.wPassenger();
            } else {
                a.wPassenger();
                a.wBill();
                int buscost = a.payBank();
                jsonObject.put("result", "Successfully Payed!");
                jsonObject.put("cost",String.valueOf(buscost));
            }

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
