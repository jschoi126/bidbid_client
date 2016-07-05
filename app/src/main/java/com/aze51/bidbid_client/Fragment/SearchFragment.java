package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class SearchFragment extends Fragment {
    View rootViewBasic;
    Button searchButton;
    EditText searchText;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    Context ctx;
    //for the suggestion of the search token 2016 07 05 태준
    String totalRecomendation[] = {"치킨","파스타","맥북","커피","고데기","세종대","맛집","영화","홍대","멋진최준성형","전태준","이경호 : 오로나민씨","권순짱","박예원:서버여신","김가영 : 차기 디자인파트장","재지니","아영짱","잘생긴 송성호","한장호찡",
            "세정 : 레이아웃파트장","디비여" +
            "신 박예원","우윳빛깔 박예원","피자장인 송성호"};
    //default : false
    boolean[] checkDuplication = new boolean[totalRecomendation.length];
    int randomCnt;
    ///
    private ArrayList<GridViewItem> itemDatas = null;
    public SearchFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.search_list_fragment,container,false);
        searchButton = (Button)rootViewBasic.findViewById(R.id.search_button);
        searchText = (EditText)rootViewBasic.findViewById(R.id.search_edit_text);

        itemDatas = new ArrayList<GridViewItem>();
        //5~8개의 랜덤 개수 추천
        randomCnt = (int)(Math.random()*3)+5;
        //initiate
        for(int i=0;i<totalRecomendation.length;i++)checkDuplication[i]=false;

        for(int i=0;i<randomCnt;){
            //전체 랜덤 토큰
            int random = (int)(Math.random()*totalRecomendation.length);
            if(checkDuplication[random]==false){
                GridViewItem gridViewItem = new GridViewItem();
                gridViewItem.item = totalRecomendation[random];

                itemDatas.add(0,gridViewItem);
                i++;
                checkDuplication[random]=true;
            }
            else{
                continue;
            }
        }
        ctx = ApplicationController.getInstance().getMainActivityContext();
        gridViewAdapter = new GridViewAdapter(itemDatas, ctx);
        gridView = (GridView)rootViewBasic.findViewById(R.id.gridView1);
        gridView.setAdapter(gridViewAdapter);
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
