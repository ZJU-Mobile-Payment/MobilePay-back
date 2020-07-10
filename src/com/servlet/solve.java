package com.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class solve {
    private List<Map<String,String>> ls;
    private List<Map<String,String>> m = new ArrayList<Map<String,String>>();
    String para1 = null;
    String para2 = null;
    public void setLs(List<Map<String,String>> a){
        this.ls = a;
    }
    public void setPara1(String a){
        this.para1 = a;
    }
    public void setPara2(String a){
        this.para2 = a;
    }




    public Map<String,String> extract(){
        int size = this.ls.size();
        Map<String,String> rs = new LinkedHashMap<String,String>();
        String str1= "GREEN";int con1 = 0;
        String str2= "RED";int con2 =0;
        String str3= "YELLOW";int con3 = 0;
        String str4= "ORANGE";int con4 = 0;

        for(int i = 0; i < size; i++){
            Map<String,String> tmp = this.ls.get(i);
            Set<String> keySet = tmp.keySet();

            //Map<String, String> rowData = new LinkedHashMap<String, String>();
            // String userid = null;String usr = null;
            //String time = null;String t = null;
            // 有了Set集合， 就可以获取其迭代器。
            for (Iterator<String> it = keySet.iterator(); it.hasNext(); )
            {
                String key = it.next();
                // 有了键可以通过map集合的get方法获取获取其对应的值。
                String value = tmp.get(key);

                if(key.equals(this.para1)){
                    if(value.equals("1")) {
                        con1++;
                    }
                    else if(value.equals("2")) {
                        con2++;
                    }
                    else if(value.equals("3")) {
                        con3++;
                    }
                    else  {
                        con4++;
                    }
                    break;
                }
               /* if(key.equals(this.para2)){
                	if(value.equals("1")) {
                		con2++;
                	}
                    //break;
                }
*/
            }
            //rowData.put(userid,usr);
            //rowData.put(time,t);
            //rs.add(rowData);

        }
        rs.put(str1,String.valueOf(con1));
        rs.put(str2,String.valueOf(con2));
        rs.put(str3,String.valueOf(con3));
        rs.put(str4,String.valueOf(con4));
        return rs;
    }
}