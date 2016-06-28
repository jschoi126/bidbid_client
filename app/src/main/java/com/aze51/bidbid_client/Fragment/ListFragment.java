package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;

/**
 * Created by jeon3029 on 16. 6. 27..
 */
public class ListFragment extends Fragment { //view pager 사용해서 리사이클러 뷰 띄움
    View rootViewBasic;
    ViewPager viewpager;
    Context ctx;
    public ListFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.top_menu_fragment,container,false);
        viewpager = (ViewPager) rootViewBasic.findViewById(R.id.viewPager);

        ctx = getActivity().getApplicationContext();
        viewpager.setAdapter(new ViewPagerCustomAdapter(ctx));//Main Activity 의 this 를 보내야함.
        return rootViewBasic;
    }
}
