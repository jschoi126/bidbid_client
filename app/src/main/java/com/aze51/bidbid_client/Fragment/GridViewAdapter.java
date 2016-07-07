package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-07-04.
 */
public class GridViewAdapter extends BaseAdapter {

    private ArrayList<GridViewItem> itemDatas = null;
    private LayoutInflater layoutInflater = null;
    Button btn;
    public GridViewAdapter(ArrayList<GridViewItem> itemDatas, Context ctx){
        this.itemDatas = itemDatas;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItemDatas(ArrayList<GridViewItem> itemDatas){
        this.itemDatas = itemDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemDatas != null ? itemDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return (itemDatas != null && ( position>=0 && position < itemDatas.size()) ? itemDatas.get(position):null);
    }

    @Override
    public long getItemId(int position) {
        return (itemDatas != null && ( position>=0 && position < itemDatas.size()) ? position:0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.search_grid_item,parent,false);
        }
        //아이템 데이터 연결 20160705태준
        GridViewItem itemData_temp = itemDatas.get(position);
        btn = (Button)convertView.findViewById(R.id.gridItem);
        btn.setText(itemData_temp.item);
        //그리드 뷰 아이템 클릭했을 경우
/*
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context ctx;
                ctx = ApplicationController.getInstance().getMainActivityContext();
                String text = btn.getText().toString();
                //text = 그리드 뷰 텍스트
                ApplicationController.getInstance().SetSearchText(text);
                ApplicationController.getInstance().SetGridViewOnClick(1);//그리드 뷰 클릭일 경우
                ((MainActivity)ctx).show_search_list_onclicked();
            }
        });
        //////*/
        return convertView;
    }
}
