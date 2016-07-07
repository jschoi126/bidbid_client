package com.aze51.bidbid_client.ViewPager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aze51.bidbid_client.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Leekh on 2016-07-06.
 */
public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteViewHolder>{
    private ArrayList<ListItemData> itemDatas, tmpDatas;
    Context mContext;
    boolean flag;


    public FavoriteViewAdapter(Context mContext, ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.mContext = mContext;

    }
    public void setItemData(ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.notifyDataSetChanged();
    }
    //ViewHolder 생성
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_cardview, parent, false);
        FavoriteViewHolder viewHolder = new FavoriteViewHolder(itemView);
        return viewHolder;
    }
    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, final int position) {
        //holder.imageView.setImageResource(itemDatas.get(position).getImage());
        Glide.with(mContext).load(itemDatas.get(position).getImg()).into(holder.imageView);
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getPrice());
        holder.text3.setText(itemDatas.get(position).getRemain_time_hour());
        holder.text4.setText(itemDatas.get(position).getRemain_time_min());
        //holder.text3.setText(itemDatas.get(position).getRemain_time());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true) {
                    holder.checkBox.setChecked(true);
                    itemDatas.get(position).getRegister();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }


}