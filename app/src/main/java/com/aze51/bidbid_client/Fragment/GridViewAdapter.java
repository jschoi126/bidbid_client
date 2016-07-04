package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aze51.bidbid_client.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-07-04.
 */
public class GridViewAdapter extends BaseAdapter {

    private ArrayList<GridViewItem> itemDatas = null;
    private LayoutInflater layoutInflater = null;

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
        GridViewItem itemData_temp = itemDatas.get(position);

        return convertView;
    }
}
