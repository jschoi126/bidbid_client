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
    BottomMenuFragment bottomMenuFrame ;
    ListFragment listFrame ;
    TopMenuFragment topMenuFrame ;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        initiate();
    }

    private void initiate() {
        bottomMenuFrame = new BottomMenuFragment();
        listFrame = new ListFragment();
        topMenuFrame = new TopMenuFragment();
        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();

    }
}
