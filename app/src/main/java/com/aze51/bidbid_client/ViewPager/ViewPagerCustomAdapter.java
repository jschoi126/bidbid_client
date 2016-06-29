package com.aze51.bidbid_client.ViewPager;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by jeon3029 on 16. 6. 28..
 */

public class ViewPagerCustomAdapter extends PagerAdapter {
    private Context mContext;

    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    public ViewPagerCustomAdapter(Context context) {
        mContext = context;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);

        collection.addView(layout);

        if(position == 0) {//첫 번째 페이지 일 경우
            recyclerView = (RecyclerView) collection.findViewById(R.id.recyclerView_current);
            //아이템이 일정할 경우 고정
            recyclerView.setHasFixedSize(true);
            //LayoutManager 초기화
            mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());//Mainactivity 의 this
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            //adapter 설정
            itemDatas = new ArrayList<ListItemData>();
            mAdapter = new RecyclerViewCustomAdapter(itemDatas);
            recyclerView.setAdapter(mAdapter);
            itemDatas.add(new ListItemData(R.mipmap.b,"이름","가격","3:57 남음"));
        }
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
}

