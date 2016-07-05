package com.aze51.bidbid_client.ViewPager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<ListItemData> itemDatas;
    Context mContext;
    Thread thread;
    TextView text11;
    TextView text12;
    TextView text13;

    public RecyclerViewCustomAdapter(Context mContext, ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.mContext = mContext;

    }
    //ViewHolder 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cardview, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        //ApplicationController.getInstance().setViewHolder(viewHolder);
        text11 = viewHolder.text3;
        text12 = viewHolder.text4;
        text13 = viewHolder.text5;
        ApplicationController.getInstance().setTextview1(text11);
        ApplicationController.getInstance().setTextview2(text12);
        ApplicationController.getInstance().setTextview3(text13);
        return viewHolder;
    }
    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.imageView.setImageResource(itemDatas.get(position).getImage());
        Glide.with(mContext).load(itemDatas.get(position).getImg()).into(holder.imageView);
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getPrice());
        holder.text3.setText(Integer.toString(itemDatas.get(position).getRemainHour()));
        holder.text4.setText(Integer.toString(itemDatas.get(position).getRemainMin()));
        holder.text5.setText(Integer.toString(itemDatas.get(position).getRemainSec()));


    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

    public class TimesThread extends Thread{
        boolean running = true;
        int rSec, rMin, rHour;
        //ViewHolder viewHolder;

        List<Product> products = ApplicationController.getInstance().getProducts(0);
        /*public TimesThread(ViewHolder holder){
            text11 = holder.text3;
            text12 = holder.text4;
            text13 = holder.text5;
        }*/
        int time;
        public void run(){
            while (running){
                try {
                    Thread.sleep(1000);
                    for(int i=0;i<products.size();i++){
                        time = products.get(i).rtime;
                        rHour = (time/3600);
                        double tmp2 = ((time/3600.0)-rHour)*60.0;
                        rMin = ((int)tmp2);
                        double tmp3 = (tmp2-rMin)*60;
                        rSec = ((int)tmp3);
                    }
                    rSec--;
                    if(rSec == 0){
                        rMin--;
                        rSec = 59;
                    }
                    if(rMin == 0){
                        rHour--;
                        rMin = 59;
                    }

                    text11.setText(Integer.toString(rHour));
                    text12.setText(Integer.toString(rMin));
                    text13.setText(Integer.toString(rSec));

                }
                catch (InterruptedException ex){
                    Log.e("SampleJavaThread", "Exception in thread");
                }
            }
        }
    }

}

