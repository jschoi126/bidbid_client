package com.aze51.bidbid_client.ViewPager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public TextView text1;
    public TextView text2;
    public TextView text3;
    public TextView text4;
    public TextView text5;

    public ViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.ImageView);
        text1 = (TextView)itemView.findViewById(R.id.item_name);
        text2 = (TextView)itemView.findViewById(R.id.item_price);
        text3 = (TextView)itemView.findViewById(R.id.remain_time_hour);
        text4 = (TextView)itemView.findViewById(R.id.remain_time_min);
        text5 = (TextView)itemView.findViewById(R.id.remain_time_sec);

    }

}
