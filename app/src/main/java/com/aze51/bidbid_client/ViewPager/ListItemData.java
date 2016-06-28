package com.aze51.bidbid_client.ViewPager;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class ListItemData {
    int image;
    String product_name;
    String price;
    String remain_time;
    public ListItemData(int image,String product_name,String price,String remain_time){
        this.image = image;
        this.product_name = product_name;
        this.price = price;
        this.remain_time = remain_time;
    }
    public int getImage(){return image;}
    public String getProduct_name(){return product_name;}
    public String getRemain_time(){return remain_time;}
    public String getPrice(){return price;}

}
