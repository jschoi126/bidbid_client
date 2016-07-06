package com.aze51.bidbid_client.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;

import java.util.List;

/**
 * Created by user on 2016-07-06.
 */
public class ListItemCardViewFragment extends Fragment {
    View rootViewBasic;
    ImageView hotIcon, hurryIcon;
    ImageView stateImage1, stateImage2;
    Context ctx;
    NetworkService networkService;
    boolean state; //visible인지 gone인지를 나타냄

    public ListItemCardViewFragment() {
        state = false;//false = 아이콘 뜸, true = 아이콘 안뜸
    }
    List<Product> products;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initNetworkService();
        ctx = ApplicationController.getInstance().getMainActivityContext();

        initView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void numberMethod (int people_num) {
        if (people_num >= 3) {
            hotIcon.setVisibility(RelativeLayout.VISIBLE);
            stateImage1.setImageResource(R.mipmap.hot);
        }
        else {
            hotIcon.setVisibility(RelativeLayout.GONE);
        }
    }

    public void minuteMethod (int min_time) {
        if (min_time <= 5) {
            hurryIcon.setVisibility(RelativeLayout.VISIBLE);
            stateImage2.setImageResource(R.mipmap.hurry);
        }
        else {
            hurryIcon.setVisibility(RelativeLayout.GONE);
        }
    }

    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void initView() {
        ctx = ApplicationController.getInstance().getMainActivityContext();
        hotIcon = (ImageView) rootViewBasic.findViewById(R.id.hotIcon);
        hurryIcon = (ImageView) rootViewBasic.findViewById(R.id.hurryIcon);

        hotIcon.setVisibility(View.VISIBLE);
        hurryIcon.setVisibility(View.VISIBLE);
    }

}
