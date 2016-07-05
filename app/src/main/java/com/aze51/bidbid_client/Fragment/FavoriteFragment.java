package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.RecyclerItemClickListener;
import com.aze51.bidbid_client.ViewPager.RecyclerViewCustomAdapter;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class FavoriteFragment extends Fragment {
    View rootViewBasic;
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Context mContext;
    private static final String FAVORITE_NUM = "FAVORITE_NUM_";
    //private static final String SAVE_FAVORITE = "SAVE_FAVORITE_";

    public FavoriteFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.favorite_list_fragment,container,false);
        recyclerView = (RecyclerView)rootViewBasic.findViewById(R.id.recyclerView_favorite);
        recyclerView.setHasFixedSize(true);
        mContext = ApplicationController.getInstance().getMainActivityContext();

        mLayoutManager = new LinearLayoutManager(mContext);//Mainactivity 의 this
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //adapter 설정
        itemDatas = new ArrayList<ListItemData>();
        mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
        recyclerView.setAdapter(mAdapter);


        //TODO : 패이버릿 리스트 저장하고있는거 서버에 보내서 받아서 itemdatas 에 추가해햐됨

        Product p = new Product();
        p.store_name = ApplicationController.getInstance().GetSearchtext();
        p.register_minprice = 1000;
        ListItemData tempitem = new ListItemData(p);
        itemDatas.add(tempitem);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //TODO :여기서 프로덕트의 리스트를 사용하면 안되고 새로히 받은 페이버릿의 리스트를 사용해야 됨

                        ApplicationController.getInstance().setPos(position);
                        ((MainActivity) mContext).show_detail_list();
                        String pos = String.valueOf(position);
                        Toast toast = Toast.makeText(mContext,
                                "포지션 : " + pos, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }));
        return rootViewBasic;
    }

}
