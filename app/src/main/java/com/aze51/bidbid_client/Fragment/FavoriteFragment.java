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
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.ViewPager.FavoriteViewAdapter;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jeon3029 on 16. 7. 4..
 */
public class FavoriteFragment extends Fragment {
    View rootViewBasic;
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    FavoriteViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Context mContext;
    NetworkService networkService;
    List<Product> myProducts;
    int tmpPos;
    int rtmp;
    String uId;
    private static final String FAVORITE_NUM = "FAVORITE_NUM_";
    //private static final String SAVE_FAVORITE = "SAVE_FAVORITE_";

    public FavoriteFragment() {
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.favorite_list_fragment,container,false);
        recyclerView = (RecyclerView)rootViewBasic.findViewById(R.id.recyclerView_favorite);
        recyclerView.setHasFixedSize(true);
        mContext = ApplicationController.getInstance().getMainActivityContext();
        tmpPos = ApplicationController.getInstance().getPos();
        uId = ApplicationController.getInstance().getUserId();
        mLayoutManager = new LinearLayoutManager(mContext);//Mainactivity 의 this
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //adapter 설정
        itemDatas = new ArrayList<ListItemData>();
        //mAdapter = new FavoriteViewAdapter(mContext,itemDatas);
        initNetworkService();
        CallFavoriteList();
        //TODO : 패이버릿 리스트 저장하고있는거 서버에 보내서 받아서 itemdatas 에 추가해햐됨
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //TODO :여기서 프로덕트의 리스트를 사용하면 안되고 새로히 받은 페이버릿의 리스트를 사용해야 됨

                        ApplicationController.getInstance().setPos(position);
                        ((MainActivity) mContext).show_detail_list();
                        if (ApplicationController.getInstance().gets() == 1) {
                            ((MainActivity) mContext).show_detail_list();
                        }
                        String pos = String.valueOf(position);
                        Toast toast = Toast.makeText(mContext,
                                "포지션 : " + pos, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }));
        //checkBoxEvent();
        return rootViewBasic;
    }


    private void CallFavoriteList(){
        String userId = ApplicationController.getInstance().getUserId();
        Call<List<Product>> getFavorite = networkService.getFavoriteProduct(userId);
        getFavorite.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    myProducts = response.body();
                    ApplicationController.getInstance().SetProducts4(myProducts);
                    if (myProducts.isEmpty() != true) {
                        for (Product product : myProducts) {
                            itemDatas.add(new ListItemData(product));
                        }
                        //rtmp = itemDatas.get(tmpPos).getRegister();
                        init();
                        mAdapter.setItemData(itemDatas);
                    }
                }
            }
            @Override
            public void onFailure (Throwable t) {

            }
        });
    }

    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }
    private void init(){
        mAdapter = new FavoriteViewAdapter(mContext,itemDatas);
        recyclerView.setAdapter(mAdapter);

    }


}
