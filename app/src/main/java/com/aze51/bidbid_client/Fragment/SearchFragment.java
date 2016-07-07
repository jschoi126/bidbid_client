package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.ViewPager.ListItemData;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class SearchFragment extends Fragment {

    TextView textView;
    Button button;
    Typeface font;



    View rootViewBasic;
    Button searchButton;
    EditText searchText;
    GridView gridView;
    //NetworkService networkService;
    GridViewAdapter gridViewAdapter;
    ArrayList<ListItemData> itemData;
    Context ctx;
    Context mContext;
    boolean get = false;
    //for the suggestion of the search token 2016 07 05 태준
    String totalRecomendation[] = {"치킨","파스타","맥북","초밥","샐러드바","세종대","맛집","샐러드바","홍대","멋진최준성형","전태준","이경호 : 오로나민씨","권순짱","박예원:서버여신","김가영 : 차기 디자인파트장","재지니","아영짱","잘생긴 송성호","한장호찡",
            "세정 : 레이아웃파트장","디비여신 박예원","우윳빛깔 박예원","피자장인 송성호","강남"};
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
        itemData = new ArrayList<ListItemData>();
        itemDatas = new ArrayList<GridViewItem>();
        mContext = ApplicationController.getInstance().getMainActivityContext();

        //textView = (TextView)findViewById(R.id.recommend_search);
        //font = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        //textView.setTypeface(font);

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
                ctx = ApplicationController.getInstance().getMainActivityContext();
                String text = searchText.getText().toString();
                ApplicationController.getInstance().SetSearchText(text);
                //text = 검색어 텍스트
                ((MainActivity) ctx).show_search_list_onclicked();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                //String selectedItem = parent.getItemAtPosition(position).toString();
                // Display the selected/clicked item text and position on TextView
                Button btn = (Button)view.findViewById(R.id.gridItem);
                Log.i("TAG",position + "클릭");
                Context ctx;
                ctx = ApplicationController.getInstance().getMainActivityContext();
                String text = btn.getText().toString();
                //String text = selectedItem;
                //text = 그리드 뷰 텍스트
                ApplicationController.getInstance().SetSearchText(text);
                ApplicationController.getInstance().SetGridViewOnClick(1);//그리드 뷰 클릭일 경우
                ((MainActivity)ctx).show_search_list_onclicked();
            }
        });
        return rootViewBasic;
    }
}
