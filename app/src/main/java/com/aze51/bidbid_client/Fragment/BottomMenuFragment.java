package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 6. 27..
 */
public class BottomMenuFragment extends Fragment {
    View rootViewBasic;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    NetworkService networkService;
    int pageState;
    int first = 0;
    Context ctx;
    public BottomMenuFragment() {
        first = 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.bottom_menu_fragment,container,false);
        initView();
        initNetworkService();
        return rootViewBasic;
    }

    public void initView() {
        image1 = (ImageView) rootViewBasic.findViewById(R.id.home_image);
        image2 = (ImageView) rootViewBasic.findViewById(R.id.star_image);
        image3 = (ImageView) rootViewBasic.findViewById(R.id.personal_image);
        image4 = (ImageView) rootViewBasic.findViewById(R.id.search_image);
        image5 = (ImageView) rootViewBasic.findViewById(R.id.bell_image);
    }

    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
        ctx = ApplicationController.getInstance().getMainActivityContext();
        pageState = ((MainActivity)ctx).getPageState();
        if(pageState == 0) {
            image1.setImageResource(R.mipmap.home_click);
        }
        else if (pageState == 1){
            image1.setImageResource(R.mipmap.home_click);
        }
        else if(pageState == 2){
            image2.setImageResource(R.mipmap.favorite_bottom_click);
        }
        else if(pageState == 3){
            image3.setImageResource(R.mipmap.mypage_click);
        }
        else if(pageState ==4){
            image4.setImageResource(R.mipmap.search_click);
        }
        else if (pageState == 5) {
            image5.setImageResource(R.mipmap.push_click);
        }
        image1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1.setImageResource(R.mipmap.home_click);
                image2.setImageResource(R.mipmap.favorite_bottom);
                image3.setImageResource(R.mipmap.mypage);
                image4.setImageResource(R.mipmap.search);
                image5.setImageResource(R.mipmap.push);
                ((MainActivity)ctx).show_current_list();
            }
        });
        image2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1.setImageResource(R.mipmap.home);
                image2.setImageResource(R.mipmap.favorite_bottom_click);
                image3.setImageResource(R.mipmap.mypage);
                image4.setImageResource(R.mipmap.search);
                image5.setImageResource(R.mipmap.push);
                ((MainActivity)ctx).show_favorite_list();
            }
        });
        image3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1.setImageResource(R.mipmap.home);
                image2.setImageResource(R.mipmap.favorite_bottom);
                image3.setImageResource(R.mipmap.mypage_click);
                image4.setImageResource(R.mipmap.search);
                image5.setImageResource(R.mipmap.push);
                ((MainActivity)ctx).show_mypage_list();
            }
        });
        image4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1.setImageResource(R.mipmap.home);
                image2.setImageResource(R.mipmap.favorite_bottom);
                image3.setImageResource(R.mipmap.mypage);
                image4.setImageResource(R.mipmap.search_click);
                image5.setImageResource(R.mipmap.push);
                ((MainActivity)ctx).show_search_list();
            }
        });
        image5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1.setImageResource(R.mipmap.home);
                image2.setImageResource(R.mipmap.favorite_bottom);
                image3.setImageResource(R.mipmap.mypage);
                image4.setImageResource(R.mipmap.search);
                image5.setImageResource(R.mipmap.push_click);
                ((MainActivity)ctx).show_push_list();
            }
        });
        //return rootViewBasic;
    }
}
