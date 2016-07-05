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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.Product;
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
    Button searchButton;
    EditText searchText;
    public SearchListOnClickedFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.search_list_onclicked_fragment,container,false);
        recyclerView = (RecyclerView)rootViewBasic.findViewById(R.id.recyclerView_search);
        searchButton = (Button)rootViewBasic.findViewById(R.id.search_button_onclicked);
        searchText = (EditText)rootViewBasic.findViewById(R.id.search_edit_text_onclicked);

        recyclerView.setHasFixedSize(true);
        mContext = ApplicationController.getInstance().getMainActivityContext();

        mLayoutManager = new LinearLayoutManager(mContext);//Mainactivity 의 this
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //adapter 설정
        itemDatas = new ArrayList<ListItemData>();
        mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
        recyclerView.setAdapter(mAdapter);

        Product p = new Product();
        p.store_name = ApplicationController.getInstance().GetSearchtext();
        p.register_minprice = 1000;
        ListItemData tempitem = new ListItemData(p);
        itemDatas.add(tempitem);
        //여기에 아이템 추가해 줘야함.!!
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context ctx;
                ctx = ApplicationController.getInstance().getMainActivityContext();
                String text = searchText.getText().toString();
                ApplicationController.getInstance().SetSearchText(text);
                //text = 검색어 텍스트
                ((MainActivity)ctx).show_search_list_onclicked();
            }
        });
        return rootViewBasic;
    }
}
