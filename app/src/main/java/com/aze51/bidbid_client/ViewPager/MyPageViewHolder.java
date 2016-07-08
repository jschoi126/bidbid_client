package com.aze51.bidbid_client.ViewPager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 7. 5..
 */
public class MyPageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView text1;
    TextView text2;
    TextView bidtime;
    //TextView text3;
    public MyPageViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.mypage_ImageView);
        text1 = (TextView)itemView.findViewById(R.id.mypage_item_name);
        text2 = (TextView)itemView.findViewById(R.id.mypage_price);
        bidtime = (TextView)itemView.findViewById(R.id.mypage_count);
        //text3 = (TextView)itemView.findViewById(R.id.mypage_remain_time);
    }
}