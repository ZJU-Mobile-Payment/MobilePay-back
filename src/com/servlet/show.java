package com.servlet;

import java.util.Date;
import java.util.*;
import java.sql.*;


public class show {
    private int userid;
    private int busid;
    private java.sql.Date starttime;
    private java.sql.Date endtime;
    private int period;//单位：天
    
    
    public void setBusid(int a) {
    	this.busid = a;
    }
    public void setUserid(int a) {
    	this.userid = a;
    }
    public void setStarttime(java.sql.Date a) {
    	this.starttime = a;
    }
    public void setEndtime(java.sql.Date a) {
    	this.endtime = a;
    }
    
    public void setPeriod(int a) {
    	this.period = a;
    }
   // int bid,int uid,Date stime,Date etime,int p
    
    public List<Map<String,String>> query(int type){
        List<Map<String,String>> lists=new ArrayList<Map<String,String>>();
        try {    
        	String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
            Connection   conn = DriverManager.getConnection(url, "root","123456");
 
            switch(type){
            	case 1:
            		//管理员查看公交统计信息
            		String sql1 = "select * from Passenger where busid =? and TO_DAYS(?) - TO_DAYS(datetime) <= 7";
                    PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            		pstmt1.setInt(1,this.busid); 
            		pstmt1.setDate(2,this.starttime); 
            		ResultSet rs1 = pstmt1.executeQuery();
            		lists=this.ResultSetToList(rs1);
            		rs1.close();
                    break;
            	case 2:
            		//查询公交相关信息
            		String sql2 = "select * from Bus where busid =?";
                    PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            		pstmt2.setInt(1,this.busid); 
            		//pstmt.setDate(2,this.starttime); 
            		ResultSet rs2 = pstmt2.executeQuery();
            		lists=this.ResultSetToList(rs2);
            		rs2.close();
            		break;
            	case 3:
            		//乘客查询
            		String sql3 = "select * from bill where busid =? and TO_DAYS(NOW()) - TO_DAYS(time) <= ?";
                    PreparedStatement pstmt3 = conn.prepareStatement(sql3);
            		pstmt3.setInt(1,this.busid); 
            		pstmt3.setInt(2,this.period); 
            		ResultSet rs3 = pstmt3.executeQuery();
            		lists=this.ResultSetToList(rs3);
            		rs3.close();
            		break;
            	default:
            		break;
            }
            conn.close();        
            return lists;
           }  catch(Exception e) {
                e.printStackTrace();
           }                             
       return lists;
      }
     
      	//转换为list形式
      public static List<Map<String,String>> ResultSetToList(ResultSet rs) {
            try {
            	//获得相关基本数据
                ResultSetMetaData md = rs.getMetaData();    
                int columnCount = md.getColumnCount();
                List<Map<String,String>> list = new ArrayList<Map<String,String>>();
                while (rs.next()) {

                    Map<String, String> rowData = new LinkedHashMap<String, String>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(md.getColumnName(i), rs.getString(i));
                        //System.out.println(rowData);  	
                    }
                    list.add(rowData);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
