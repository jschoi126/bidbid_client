package com.aze51.bidbid_client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailItemFragment extends Fragment {
    View rootViewBasic;
    TextView detail_price;
    TextView detail_time;
    TextView detail_title;
    String get_img;
    ImageView detail_img;
    List<Product> products;
    int position;
    public DetailItemFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.detail_item_cardview,container,false);
        products = ApplicationController.getInstance().getProduct();
        position = ApplicationController.getPosition();
        initView();
        //detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        //detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        //detail_price.setText("120000");
        String tmpName = products.get(position).product_name;
        int tmpPrice = products.get(position).register_minprice;
        String tmpImg = products.get(position).product_img;
        //String tmpPrice = (products.get(position).register_minprice);
        detail_title.setText(tmpName);
        detail_price.setText(Integer.toString(tmpPrice));
        //detail_time.setText("3:45");
        Glide.with(this).load(tmpImg).into(detail_img);
        return rootViewBasic;
    }
    protected void initView(){
        detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        detail_title = (TextView)rootViewBasic.findViewById(R.id.detail_item_name);
        detail_img = (ImageView)rootViewBasic.findViewById(R.id.detail_ImageView);

    }
}
