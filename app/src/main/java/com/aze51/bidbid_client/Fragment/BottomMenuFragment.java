package com.aze51.bidbid_client.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.R;

import retrofit2.Call;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.bottom_menu_fragment,container,false);
        initView();
        initNetworkService();
        return rootViewBasic;
    }

    public void initView(){
        image1 = (ImageView)rootViewBasic.findViewById(R.id.home_image);
        image2 = (ImageView)rootViewBasic.findViewById(R.id.star_image);
        image3 = (ImageView)rootViewBasic.findViewById(R.id.personal_image);
        image4 = (ImageView)rootViewBasic.findViewById(R.id.search_image);
        image5 = (ImageView)rootViewBasic.findViewById(R.id.bell_image);
    }
    public void searchContents(String content){
        Call<String> callSearch = networkService.searchContents(content);

    }
    private void initNetworkService() {
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();

    }
}
