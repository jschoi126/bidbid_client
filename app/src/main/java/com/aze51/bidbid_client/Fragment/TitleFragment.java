package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.SettingActivity;

/**
 * Created by jeon3029 on 16. 6. 27..
 */
public class TitleFragment extends Fragment {
    View rootViewBasic;
    //ImageView image1;
    TextView text;
    ImageView image2;
    ImageView settingImage;
    public TitleFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.title_fragment,container,false);
        //image1 = (ImageView)rootViewBasic.findViewById(R.id.logo_image);
        text = (TextView)rootViewBasic.findViewById(R.id.bidbid_text);
        image2 = (ImageView)rootViewBasic.findViewById(R.id.setting_image);
        image2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context ctx = ApplicationController.getInstance().getMainActivityContext();
                Intent intent = new Intent(ctx, SettingActivity.class);

                startActivity(intent);
            }
        });
        return rootViewBasic;
    }
}
