package com.servlet;
import java.util.Date;
import java.util.*;
import java.sql.*;
public class Gethst {
    private int usrid;
    private int hst;

    public void setuid(int a) {
        this.usrid = a;
    }

    public int query() {
        int healthstate = -1;
        try {
            String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
            Connection   conn = DriverManager.getConnection(url, "root","123456");
            // java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            String sql = "select state from healthcode where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,this.usrid);

            ResultSet rs1 = pstmt.executeQuery();

            //通过next来索引：判断是否有下一个记录
            while(rs1.next()){
                healthstate = rs1.getInt("state");
                System.out.println(healthstate);
            }
            conn.close();


        }  catch(Exception e) {
            e.printStackTrace();
        }

        this.hst = healthstate;
        return this.hst;

    }
}

