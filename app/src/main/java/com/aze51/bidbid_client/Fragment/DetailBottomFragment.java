package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class DetailBottomFragment extends Fragment {
    View rootViewBasic;

    ImageView upDownImage;
    EditText bidText;
    Button bidBtn;
    LinearLayout detailLinearBid;
    RelativeLayout relativeLayout;
    Context ctx;
    boolean upDownState;
    public DetailBottomFragment() {
        upDownState = false;//false = down, ture = up
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.detail_bottom_fragment,container,false);
        //image1 = (ImageView)rootViewBasic.findViewById(R.id.logo_image);
        //text = (TextView)rootViewBasic.findViewById(R.id.bidbid_text);
        //image2 = (ImageView)rootViewBasic.findViewById(R.id.setting_image);
        ctx = ApplicationController.getInstance().getMainActivityContext();
        upDownImage = (ImageView)rootViewBasic.findViewById(R.id.detail_up_image);
        bidBtn = (Button)rootViewBasic.findViewById(R.id.detail_bid_btn);
        bidText = (EditText)rootViewBasic.findViewById(R.id.detail_edit_text);
        detailLinearBid = (LinearLayout)rootViewBasic.findViewById(R.id.detail_linear_bid);
        relativeLayout = (RelativeLayout)rootViewBasic.findViewById(R.id.detail_bottom_relative);
        upDownImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("TAG","업 버튼 누름");
                if(upDownState == false){//현재 다운
                    upDownState = true;
                    detailLinearBid.setVisibility(LinearLayout.VISIBLE);
                    upDownImage.setImageResource(R.drawable.detail_down);

                    int paddingPixel = 0;
                    float density = ctx.getResources().getDisplayMetrics().density;
                    int paddingDp = (int)(paddingPixel * density);
                    relativeLayout.setPaddingRelative(0,paddingDp,0,0);
                }
                else if(upDownState == true){//현재 업
                    upDownState = false;
                    detailLinearBid.setVisibility(LinearLayout.GONE);
                    upDownImage.setImageResource(R.drawable.detail_up);

                    int paddingPixel = 50;
                    float density = ctx.getResources().getDisplayMetrics().density;
                    int paddingDp = (int)(paddingPixel * density);
                    relativeLayout.setPaddingRelative(0,paddingDp,0,0);
                }
            }
        });
        bidBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("TAG","입찰 버튼 투름");
                Toast.makeText(ctx,"입찰하셨습니다",Toast.LENGTH_SHORT).show();
            }
        });
        return rootViewBasic;
    }
}
