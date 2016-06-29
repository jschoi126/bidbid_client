package com.aze51.bidbid_client;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.Fragment.TopMenuFragment;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.RecyclerViewCustomAdapter;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public MainActivity reference;

    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    TopMenuFragment topMenuFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    View rootViewBasic;

    //ViewPager
    ViewPager viewpager;

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
        show_current_list();
    }
    //Made By Tae Joon 2016 06 27 : 현재 판매중인 목록 프래그먼트로 보여주기.
    public void show_current_list() {


        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }
    public void show_scheduled_list(){
        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();

    }
    public void show_approaching_list(){
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
    public class ListFragment extends Fragment { //view pager 사용해서 리사이클러 뷰 띄움

        Context ctx;
        public ListFragment(){
            //생성자
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootViewBasic = inflater.inflate(R.layout.list_fragment,container,false);
            viewpager = (ViewPager) rootViewBasic.findViewById(R.id.viewPager);
            //ctx = getActivity().getApplicationContext();
            viewpager.setAdapter(new ViewPagerCustomAdapter(reference));//Main Activity 의 this 를 보내야함.
            return rootViewBasic;
        }
    }
}
