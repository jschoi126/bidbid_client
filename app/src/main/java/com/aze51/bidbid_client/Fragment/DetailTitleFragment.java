package com.aze51.bidbid_client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailTitleFragment extends Fragment {
    View rootViewBasic;
    ImageView image1;
    TextView text;
    ImageView image2;
    public DetailTitleFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.detail_title_fragment,container,false);

        image1 = (ImageView)rootViewBasic.findViewById(R.id.detail_back);
        text = (TextView)rootViewBasic.findViewById(R.id.detail_bidbid_text);
        image2 = (ImageView)rootViewBasic.findViewById(R.id.detail_setting_image);

        image1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Main의 show current list
            }
        });
        return rootViewBasic;
    }
}