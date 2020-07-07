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

public class ModifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id").trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String password = request.getParameter("password").trim();
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String address = request.getParameter("address").trim();
        User user = UserDAO.queryUser(id);
        if(password.equals("")) {
            password = user.getPassword();
        }
        if(username.equals("")){
            username = user.getUsername();
        }
        if (email.equals("")){
            email = user.getEmail();
        }
        if(phone.equals("")){
            phone = user.getPhone();
        }
        if (address.equals("")){
            address = user.getAddress();
        }

        String updateRes = UserDAO.updateUser(id, password, username, email, phone, address);
        user = UserDAO.queryUser(id);
        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        if(user != null && user.getPassword().equals(password)) {
            params.put("result", "success");
            params.put("id", "" + id);
            params.put("password", "" + user.getPassword());
            params.put("username", "" + user.getUsername());
            params.put("email", "" + user.getEmail());
            params.put("phone", "" + user.getPhone());
            params.put("address", ""+user.getAddress());
            params.put("isadmin", "" + user.getIsadmin());
        }
        else
        {
            params.put("result", "failed");
        }
        jsonObject.put("params", params);
        out.write(jsonObject.toString());
        System.out.println(jsonObject.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
