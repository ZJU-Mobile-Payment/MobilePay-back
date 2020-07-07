package com.servlet;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class QueryBill extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public QueryBill() {
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

        JSONObject jsonObject = BillDAO.queryBill(id);
        out.write(jsonObject.toString());
        System.out.println(jsonObject.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
