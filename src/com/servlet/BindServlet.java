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

public class BindServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BindServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        int usrid = 0;
        try {
            usrid = Integer.parseInt(request.getParameter("usrid").trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String card = request.getParameter("card").trim();

        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        Bind bindbf = BindDAO.queryBind(usrid, card);
        if(bindbf != null){
            params.put("result", "BindAlready");
        }
        else{
            String createRes = BindDAO.createBind(usrid,card);
            Bind bind = BindDAO.queryBind(usrid);

            if(bind != null && bind.getCard().equals(card)) {
                params.put("result", "success");
                params.put("usrid", "" + usrid);
                params.put("card", "" + bind.getCard());
            }
            else
            {
                params.put("result", "failed");
            }
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
