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
 * Created by jeon3029 on 16. 7. 5..
 */
public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<MyPageViewHolder>{
    private ArrayList<ListItemData> itemDatas;
    Context mContext;

    public MyPageRecyclerViewAdapter(Context mContext, ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.mContext = mContext;

    }
    //ViewHolder 생성
    @Override
    public MyPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mypage_cardview, parent,false);
        MyPageViewHolder viewHolder = new MyPageViewHolder(itemView);
        return viewHolder;
    }
    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(MyPageViewHolder holder, int position) {
        //holder.imageView.setImageResource(itemDatas.get(position).getImage());
        Glide.with(mContext).load(itemDatas.get(position).getImg()).into(holder.imageView);
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getDealPrice());
        //holder.text3.setText(itemDatas.get(position).getRemain_time());
    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}