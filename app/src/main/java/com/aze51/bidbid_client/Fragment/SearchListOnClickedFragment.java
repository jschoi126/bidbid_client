package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.RecyclerViewCustomAdapter;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 5..
 */
public class SearchListOnClickedFragment extends Fragment {
    View rootViewBasic;
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Context mContext;
    public SearchListOnClickedFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.search_list_onclicked_fragment,container,false);
        recyclerView = (RecyclerView)rootViewBasic.findViewById(R.id.recyclerView_search);
        recyclerView.setHasFixedSize(true);
        mContext = ApplicationController.getInstance().getMainActivityContext();

        mLayoutManager = new LinearLayoutManager(mContext);//Mainactivity 의 this
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //adapter 설정
        itemDatas = new ArrayList<ListItemData>();
        mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
        recyclerView.setAdapter(mAdapter);

        //여기에 아이템 추가해 줘야함.!!

        return rootViewBasic;
    }
}
