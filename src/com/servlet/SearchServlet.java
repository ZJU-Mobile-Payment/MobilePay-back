package com.servlet;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id").trim();
            String password = request.getParameter("password").trim();

            JSONObject jsonObject = new JSONObject();
            Boolean verifyResult = verifyLogin(id, password);
            if(verifyResult){                                     //同样的验证通过才能查询，返回的是json格式的数据
                jsonObject = UserDAO.queryUser();
            }else{

            }
            out.write(jsonObject.toString());
        }
    }
    private Boolean verifyLogin(String userName, String password) {
        User user = UserDAO.queryUser(userName);
        //账户密码验证
        return null != user && password.equals(user.getPassword());
    }
}

