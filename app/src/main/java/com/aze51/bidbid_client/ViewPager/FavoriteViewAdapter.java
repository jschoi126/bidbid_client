package com.aze51.bidbid_client.ViewPager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
/**
 * Created by Leekh on 2016-07-06.
 */
public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteViewHolder>{
    private ArrayList<ListItemData> itemDatas, tmpDatas;
    Context mContext, ctx;
    boolean flag;
    int check;
    String uId;
    NetworkService networkService;
    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("yyyy년 M월 d일 h시 m분에 게시");


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
        initNetworkService();
        flag = ApplicationController.getInstance().getFlag();
        uId = ApplicationController.getInstance().getUserId();
        Glide.with(mContext).load(itemDatas.get(position).getImg()).into(holder.imageView);
        holder.text1.setText(itemDatas.get(position).getProduct_name());
        holder.text2.setText(itemDatas.get(position).getPrice());
        holder.text3.setText(itemDatas.get(position).getRemain_time_hour());
        holder.text4.setText(itemDatas.get(position).getRemain_time_min());

       // holder.text5.setText(FORMAT.format(itemDatas.get(position).getStime()));
       // holder.text6.setText(FORMAT.format(itemDatas.get(position).getFtime()));

//        if(itemDatas.get(position) != null && !TextUtils.isEmpty(itemDatas.get(position).getStime()))
//            holder.text5.setText(FORMAT.format(itemDatas.get(position).getStime()));
//        if(itemDatas.get(position) != null && !TextUtils.isEmpty(itemDatas.get(position).getFtime()))
//            holder.text6.setText(FORMAT.format(itemDatas.get(position).getFtime()));
    }
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

    public void deleteCall(String user, int register){
        Call<Void> deleteCall = networkService.deleteFavorite(user, register);
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if(response.isSuccess()){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

}