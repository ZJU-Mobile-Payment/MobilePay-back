package com.servlet;

import java.util.Date;
import java.util.*;
import java.sql.*;
public class Billcheck {
    private int usrid = 0;
    private int billnum = 0;
    private int cost = -1;


    public void setuid(int a) {
        this.usrid = a;
    }

    public void Init(int a) {
        this.setuid(a);
        this.setcostnum();
    }

    public void setcostnum() {
        try {
            String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
            Connection   conn = DriverManager.getConnection(url, "root","123456");
            String sql1 = "select lastcost from billcheck where usrid = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1,this.usrid);
            ResultSet rs1 = pstmt1.executeQuery();
            while(rs1.next()){
                //deposit = rs2.getFloat("deposit");
                this.cost = rs1.getInt("lastcost");
                //System.out.println(this.cost);
            }


            String sql2 = "select billnum from billcheck where usrid = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1,this.usrid);
            ResultSet rs2 = pstmt2.executeQuery();
            while(rs2.next()){
                //deposit = rs2.getFloat("deposit");
                this.billnum = rs2.getInt("billnum");
                //System.out.println(this.billnum);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    public int getBillnum() {
        return this.billnum;
    }

    public int getCost() {
        return this.cost;
    }

}
