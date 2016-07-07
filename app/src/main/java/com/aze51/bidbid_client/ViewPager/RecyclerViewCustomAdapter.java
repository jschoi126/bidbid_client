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
 * Created by jeon3029 on 16. 6. 28..
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<ListItemData> itemDatas;
    Context mContext;

    public RecyclerViewCustomAdapter(Context mContext, ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.mContext = mContext;
        notifyDataSetChanged();

    }
    //ViewHolder 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cardview, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }
    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.imageView.setImageResource(itemDatas.get(position).getImage());

        Glide.with(mContext).load(itemDatas.get(position).getImg()).fitCenter().into(holder.imageView);
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getPrice());
        holder.text3.setText(itemDatas.get(position).getRemain_time_hour());
        holder.text4.setText(itemDatas.get(position).getRemain_time_min());
        holder.text5.setText(itemDatas.get(position).getStime());
        holder.text6.setText(itemDatas.get(position).getFtime());
        //holder.text5.setText(itemDatas.get(position).getRemain_time_sec());
    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}