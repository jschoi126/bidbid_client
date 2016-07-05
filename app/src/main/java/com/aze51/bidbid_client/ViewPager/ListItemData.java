package com.aze51.bidbid_client.ViewPager;

import com.aze51.bidbid_client.Network.Product;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class ListItemData {
    int image;
    String product_name;
    String price;
    int remain_time, rHour, rMin, rSec;
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
        this.rSec = ((int)tmp3);
    }
    //public int getImage(){return image;}
    public String getImg(){return img;}
    public String getProduct_name(){return product_name;}
    public String getRemain_time_hour(){return Integer.toString(rHour);}
    public String getRemain_time_min(){return Integer.toString(rMin);}
    public String getRemain_time_sec(){return Integer.toString(rSec);}
    public String getPrice(){return Integer.toString(getPrices());}
    public int getPrices(){return prices;}

}