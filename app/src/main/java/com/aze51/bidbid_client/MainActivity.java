package com.aze51.bidbid_client;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.ListFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.Fragment.TopMenuFragment;

public class MainActivity extends AppCompatActivity {

    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    TopMenuFragment topMenuFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        initiate();
        show_now_list();
    }

    private void show_now_list() {
        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        //fragmentManager.beginTransaction().add(R.id.listView,listFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }

    private void initiate() {
        bottomMenuFragment = new BottomMenuFragment();
        listFragment = new ListFragment();
        topMenuFragment = new TopMenuFragment();
        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();

    }
}
