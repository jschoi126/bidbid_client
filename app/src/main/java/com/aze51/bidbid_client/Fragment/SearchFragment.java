package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class SearchFragment extends Fragment {
    View rootViewBasic;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    Context ctx;
    private ArrayList<GridViewItem> itemDatas = null;

    public SearchFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.search_list_fragment,container,false);

        itemDatas = new ArrayList<GridViewItem>();
        GridViewItem gridViewItem = new GridViewItem();
        gridViewItem.item = "치킨";
        itemDatas.add(0,gridViewItem);
        ctx = ApplicationController.getInstance().getMainActivityContext();
        gridViewAdapter = new GridViewAdapter(itemDatas, ctx);
        gridView = (GridView)rootViewBasic.findViewById(R.id.gridView1);
        gridView.setAdapter(gridViewAdapter);
        return rootViewBasic;
    }
}
