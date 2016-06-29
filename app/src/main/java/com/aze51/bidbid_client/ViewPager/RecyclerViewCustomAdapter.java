package com.aze51.bidbid_client.ViewPager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aze51.bidbid_client.R;
import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<ListItemData> itemDatas;

    public RecyclerViewCustomAdapter(ArrayList<ListItemData> itemDatas){
        this.itemDatas = itemDatas;
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
        holder.imageView.setImageResource(itemDatas.get(position).getImage());
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getPrice());
        holder.text3.setText(itemDatas.get(position).getRemain_time());
    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}