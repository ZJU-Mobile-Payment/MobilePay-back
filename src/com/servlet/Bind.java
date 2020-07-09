package com.servlet;

public class Bind {
    //绑定关系id号
    private int bindid;
    //银行卡账号
    private String card;
    //用户id
    private int usrid;

    public int getId() {
        return bindid;
    }

    public void setId(int bindid) {
        this.bindid = bindid;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) { this.card = card; }

    public int getUsrid() {
        return usrid;
    }

    public void setUsrid(int usrid) {
        this.usrid = usrid;
    }

}
