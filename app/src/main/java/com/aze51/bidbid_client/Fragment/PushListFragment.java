package com.aze51.bidbid_client.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 3..
 */
public class PushListFragment extends Fragment {
    View rootViewBasic;
    //ImageView image1;
    ListView listView;
    private ArrayList<PushListViewItem> itemDatas = null;
    PushListCustomAdapter pushListCustomAdapter;

    public PushListFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.push_list_fragment,container,false);
        listView = (ListView)rootViewBasic.findViewById(R.id.push_list_view);

        itemDatas = new ArrayList<PushListViewItem>();
        Context ctx = ApplicationController.getInstance().getMainActivityContext();
        pushListCustomAdapter = new PushListCustomAdapter(itemDatas,ctx);
        listView.setAdapter(pushListCustomAdapter);
        return rootViewBasic;
    }
}
