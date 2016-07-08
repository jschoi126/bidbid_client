package com.aze51.bidbid_client.ViewPager;

import com.aze51.bidbid_client.Network.Product;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;


/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class ListItemData {
    int image;
    SimpleDateFormat simpleDateFormat;
    String product_name;
    String price;
    int registerid;
    int dealCount;
    //Date sDate;
    int remain_time, rHour, rMin, rSec, dealPrice, people;
    String stime, ftime;
    public String img;
    int prices;
    public ListItemData(int image,String product_name,String price,String remain_time){
        this.image = image;
        this.product_name = product_name;
        this.price = price;
        //this.remain_time = remain_time;
    }
    public ListItemData(Product product)
    {
        this.img = product.product_img;
        this.product_name = product.product_name;
        this.prices = product.register_minprice;
        this.remain_time = product.rtime;
        this.rHour = (remain_time/3600);
        double tmp2 = ((remain_time/3600.0)-rHour)*60.0;
        this.rMin = ((int)tmp2);
        double tmp3 = (tmp2-rMin)*60;
        this.rSec   = ((int)tmp3);
        this.dealPrice = product.deal_price;
        this.registerid = product.register_id;
        //this.stime = product.register_stime;
        this.stime = changeString(product.register_stime);
        this.ftime = changeString(product.register_ftime);
        this.people = product.register_numpeople;
        this.dealCount = product.dealCount;
    }
    //public int getImage(){return image;}
    public String getImg(){return img;}
    public int getDealCount(){return dealCount;}
    public String getProduct_name(){return product_name;}
    public String getRemain_time_hour(){return Integer.toString(rHour);}
    public String getRemain_time_min(){return Integer.toString(rMin);}
    public String getRemain_time_sec(){return Integer.toString(rSec);}
    public String getPrice(){return Integer.toString(getPrices());}
    public String getDealPrice(){return Integer.toString(getDealPrices());}
    public int getPrices(){return prices;}
    public int getDealPrices(){return dealPrice;}
    public int getRegister(){return registerid;}
    public String getStime() {return stime;}
    public String getFtime() {return ftime;}
    public String getNumPeople(){return Integer.toString(people);}

    /*private void changeData(String str1){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        str1 = format2.format(sDate);

    }*/
    private String changeString(String str1){
        if(str1==null || str1.length()==0){
            return "";
        }
        StringTokenizer tokenizer = new StringTokenizer(str1);
        String dates = tokenizer.nextToken("T");
        String tmp = tokenizer.nextToken(".");
        String times = tmp.substring(1, 6);

        String sumdates = dates +" "+ times;

        return sumdates;
    }
}