package com.servlet;

import java.util.Date;
import java.util.*;
import java.sql.*;

public class pay {
	private int userid;
    private int busid;
    private int healthstate;
    private int cost;//公交车费用
    private Boolean flag;//是否允许支付
    
    public void setBusid(int a) {
    	this.busid = a;
    }
    public void setUserid(int a) {
    	this.userid = a;
    }
    public void setHstate(int a) {
    	this.healthstate = a;
    }
    public void setCost() {
    	this.cost = getBusCost();
    }
    
    
    
    //根据健康码判断是否允许乘车
    public Boolean judge() {
    	if(this.healthstate == 1) {
    		this.flag = true;
    	}    		
    	else {
    		this.flag = false;
    	}    		
    	return this.flag;
    }
    //Passenger表记入记录
    public int wPassenger() {
    	int rs = 0;
    	try {
    		String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
            Connection   conn = DriverManager.getConnection(url, "root","123456");
           // java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); 
          
            String sql = "insert into Passenger (scanid,userid,busid,healthstate,ispayed,datetime) values (0,?,?,?,?,NOW())";
            PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1,this.userid); 
    		pstmt.setInt(2,this.busid); 
    		pstmt.setInt(3,this.healthstate); 
    		pstmt.setInt(4,this.flag?1:0); 
    		rs = pstmt.executeUpdate();
    		conn.close();  
    		
    		return rs;
    	}  catch(Exception e) {
            e.printStackTrace();
       }                             
    	return rs;
    }
    
    //成功支付的，更新bill表
    public int wBill() {
    	int rs = 0;
    	try {
    		String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
            Connection   conn = DriverManager.getConnection(url, "root","123456");
           // java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); 
          
            String sql = "insert into bill (billid,userid,busid,time) values (0,?,?,NOW())";
            PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1,this.userid); 
    		pstmt.setInt(2,this.busid); 
    		rs = pstmt.executeUpdate();
    		conn.close();  
    		
    		return rs;
    	}  catch(Exception e) {
            e.printStackTrace();
       }                             
    	return rs;
    }

    //支付扣款，查找CardBind 与 BankCard表，并进行费用扣除，更新BankCard表中相应的余额
    public int payBank() {
    	int rs = -100;
    	ArrayList<String> cards = new ArrayList<String>();
    	try {
    		String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
    		Connection   conn = DriverManager.getConnection(url, "root","123456");
    		// java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); 
    		
    		
    		String sql1 = "select card from CardBind where usrid = ?";
    		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
    		pstmt1.setInt(1,this.userid); 
    		//pstmt.setInt(2,this.busid); 
    		ResultSet rs1 = pstmt1.executeQuery();
    		
    		//获取该用户绑定的所有银行卡账号
    		while(rs1.next()){  
                  cards.add(rs1.getString(1));
                 
    		}

    		pstmt1.close();
    		rs1.close();
    		//conn.close();  
 		
    		int size = cards.size();
    		Boolean find = false;
    		
    		for(int i = 0; i < size;i++) {
    			String cnum = cards.get(i);
    			//System.out.println(cnum);

    			String sql2 = "select deposit from BankCard where cardid = ?";
    			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        		pstmt2.setString(1,cnum); 
        		ResultSet rs2 = pstmt2.executeQuery();
        		float deposit = (float) -1.0;
        		while(rs2.next()){
        		 deposit = rs2.getFloat("deposit");
        		 System.out.println(deposit);
        		 System.out.println(this.cost);
        		}

        		//找出该乘客所有账户下第一张余额足够支付的银行卡
        		if((int)deposit >= this.cost) {
        			float newdpt = deposit - this.cost;
        			find = true;
        			pstmt2.close();
        			rs2.close();
        			//conn2.close();
        		
        			//对该卡的余额进行更新
        			String sql3 = "update  BankCard set deposit = ? where cardid = ?";
        			PreparedStatement pstmt3 = conn.prepareStatement(sql3);
            		pstmt3.setFloat(1,newdpt); 
            		pstmt3.setString(2,cnum); 
            		pstmt3.executeUpdate();
        		        			
        			break;
        		}
        		
        		else {
        			;
        		}
        		//rs2.close();
    		}
    		
    		if(find) {
    			rs = this.cost;
    		}
    		else {
    			rs = -1;
    		}	
    		conn.close();  
    	
    	
    	return rs;
    	}  catch(Exception e) {
            e.printStackTrace();
       }
    	
    	return rs;
    }
    
    //获取该公交车的固定费用
    public int getBusCost() {
    	int bcost = -3;
    	try {
    		String url = "jdbc:mysql://47.106.195.214:3306/mobile_payment";
    		Connection   conn = DriverManager.getConnection(url, "root","123456");
    		// java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); 
    		
    		
    		String sql1 = "select cost from Bus where busid = ?";
    		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
    		pstmt1.setInt(1,this.busid); 
    		//pstmt.setInt(2,this.busid); 
    		ResultSet rs1 = pstmt1.executeQuery();
    		while(rs1.next()){
    			bcost = rs1.getInt("cost");
       		}
    		
    		rs1.close();
    		conn.close();
    		return bcost;
    	}  catch(Exception e) {
            e.printStackTrace();
       }
    	return bcost;
    }
    
      
}
