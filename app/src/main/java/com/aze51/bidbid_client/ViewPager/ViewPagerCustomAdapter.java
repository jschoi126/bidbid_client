package com.aze51.bidbid_client.ViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Fragment.DetailItemFragment;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeon3029 on 16. 6. 28..
 */
public class ViewPagerCustomAdapter extends PagerAdapter {
    private Context mContext;
    NetworkService networkService;
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    Context ctx;
    List<Product> productss, tmp_Product;
    int position, pos;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    //int productPostion;
    //Call<List<Product>> listCall;
    List<Product> products;
    DetailItemFragment detailItemFragment;
    public ProgressBar pbHeaderProgress;
    //NetworkService networkService;
    public ViewPagerCustomAdapter(Context context) {
        mContext = context;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {

        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        //notifyDataSetChanged();
        pbHeaderProgress = (ProgressBar)collection.findViewById(R.id.pbHeaderProgress);
        if(position == 0) {//첫 번째 페이지 일 경우
            //notifyDataSetChanged();
            recyclerView = (RecyclerView) collection.findViewById(R.id.recyclerView_approaching);
            //아이템이 일정할 경우 고정
            recyclerView.setHasFixedSize(true);
            //LayoutManager 초기화
            mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());//Mainactivity 의 this
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            //adapter 설정
            itemDatas = new ArrayList<ListItemData>();
            mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
            recyclerView.setAdapter(mAdapter);

            //products = ((MainActivity)mContext).products;
            products = ApplicationController.getInstance().getProducts(position);
            if(products == null || products.isEmpty()){
                Log.i("TAG","0viewpager 비어있습니다");
            }
            else {
                Log.i("TAG","0is not empty");
                pbInvisible();
                for (Product p : products) {
                    itemDatas.add(new ListItemData(p));
                }
            }
            //itemDatas.add(new ListItemData(R.mipmap.b,"이름111","가격1111","3:57 남음"));
        }
        else if(position == 1){
            //notifyDataSetChanged();
            recyclerView = (RecyclerView) collection.findViewById(R.id.recyclerView_current);
            //아이템이 일정할 경우 고정
            recyclerView.setHasFixedSize(true);
            //LayoutManager 초기화
            mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());//Mainactivity 의 this
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            //adapter 설정
            itemDatas = new ArrayList<ListItemData>();
            mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
            recyclerView.setAdapter(mAdapter);

            //itemDatas.add(new ListItemData(R.mipmap.b,"이름222","가격2222","3:57 남음"));
            //products = ((MainActivity)mContext).products;
            products = ApplicationController.getInstance().getProducts(position);

            if(products == null || products.isEmpty()){
                Log.i("TAG","1viewpager 비어있습니다");
            }
            else {
                Log.i("TAG","1is not empty");
                pbInvisible();
                for (Product p : products) {
                    itemDatas.add(new ListItemData(p));
                }
            }
        }
        else if(position == 2){
            Log.i("TAG", "2");
            //notifyDataSetChanged();
            recyclerView = (RecyclerView) collection.findViewById(R.id.recyclerView_scheduled);
            //아이템이 일정할 경우 고정
            recyclerView.setHasFixedSize(true);
            //LayoutManager 초기화
            mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());//Mainactivity 의 this
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            //adapter 설정
            itemDatas = new ArrayList<ListItemData>();
            mAdapter = new RecyclerViewCustomAdapter(mContext,itemDatas);
            recyclerView.setAdapter(mAdapter);
            //itemDatas.add(new ListItemData(R.mipmap.b,"이름333","가격3333","3:57 남음"));
            //products = ((MainActivity)mContext).products;
            products = ApplicationController.getInstance().getProducts(position);

            if(products == null || products.isEmpty()){

                Log.i("TAG","2viewpager 비어있습니다");
            }
            else {
                Log.i("TAG","2is not empty");
                pbInvisible();
                for (Product p : products) {
                    itemDatas.add(new ListItemData(p));
                }
            }
        }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                ApplicationController.getInstance().setPos(position);   // 리스트의 상품 순서 번호
                    ((MainActivity) mContext).show_detail_list();
                if(ApplicationController.getInstance().gets() == 1) {
                    notifyDataSetChanged();
                    ((MainActivity) mContext).show_detail_list();
                }
                String pos = String.valueOf(position);
                Toast toast = Toast.makeText(mContext,
                        "포지션 : " + pos, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }));
        return layout;
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
    @Override
    public int getCount() {
        return ModelObject.values().length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    public void pbVisible(){
        pbHeaderProgress.setVisibility(View.VISIBLE);
        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //my_button.setBackgroundResource(R.drawable.defaultcard);
            }
        }, 2000);*/
    }
    @Override
    public void notifyDataSetChanged() {
        //pbInvisible();
        super.notifyDataSetChanged();
    }

    public void pbInvisible(){
        if(pbHeaderProgress!=null && pbHeaderProgress.getVisibility() == ProgressBar.VISIBLE) {
            pbHeaderProgress.setVisibility(View.GONE);
        }
    }
   /* public void getDetailContent(int id, String userid){
        Call<List<Product>> callProduct = networkService.getContent(id,userid);
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    tmp_Product = response.body();
                    /*tmpProduct = tmp_Product.get(0);
                    registerID = tmpProduct.register_id;
                    detail_title.setText(tmpProduct.product_name);
                    Glide.with(getContext()).load(tmpProduct.product_img).into(detail_img);
                    detail_price.setText(tmpProduct.register_minprice);
                    //tmp_time = tmpProduct.rtime;

                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }  //DetailItemFragment*/

}

