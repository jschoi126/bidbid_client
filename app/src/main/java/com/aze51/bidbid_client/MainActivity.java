package com.aze51.bidbid_client;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.ListFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.Fragment.TopMenuFragment;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.RecyclerViewCustomAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public MainActivity reference;

    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    TopMenuFragment topMenuFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;

    //Recycler View
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        reference = this;
        initiate();
        show_now_list();
    }


    //Made By Tae Joon 2016 06 27 : 현재 판매중인 목록 프래그먼트로 보여주기.
    private void show_now_list() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_current);
        //아이템이 일정할 경우 고정
        recyclerView.setHasFixedSize(true);
        //LayoutManager 초기화
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //adapter 설정
        itemDatas = new ArrayList<ListItemData>();
        mAdapter = new RecyclerViewCustomAdapter(itemDatas);
        recyclerView.setAdapter(mAdapter);


        itemDatas.add(new ListItemData(R.mipmap.b,"이름","가격","3:57 남음"));


        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }


    //Made By Tae Joon 2016 06 27 : 초기화
    private void initiate() {
        bottomMenuFragment = new BottomMenuFragment();
        listFragment = new ListFragment();
        topMenuFragment = new TopMenuFragment();
        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();
    }
}
