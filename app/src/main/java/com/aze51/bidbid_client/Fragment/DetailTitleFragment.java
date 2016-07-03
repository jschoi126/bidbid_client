package com.aze51.bidbid_client.Fragment;

import android.app.ApplicationErrorReport;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailTitleFragment extends Fragment {
    View rootViewBasic;
    ImageView backimage;
    TextView text;
    ImageView image2;
    public DetailTitleFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.detail_title_fragment,container,false);

        backimage = (ImageView)rootViewBasic.findViewById(R.id.detail_back_image);
        text = (TextView)rootViewBasic.findViewById(R.id.detail_bidbid_text);
        image2 = (ImageView)rootViewBasic.findViewById(R.id.detail_setting_image);

        backimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context ctx = ApplicationController.getInstance().getMainActivityContext();
                ((MainActivity)ctx).show_current_list();
            }
        });
        return rootViewBasic;
    }
}