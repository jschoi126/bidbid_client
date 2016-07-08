package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by jeon3029 on 16. 7. 3..
 */
public class PushListCustomAdapter extends BaseAdapter{
    private ArrayList<PushListViewItem> itemDatas = null;
    private LayoutInflater layoutInflater = null;

    //생성자
    public PushListCustomAdapter(ArrayList<PushListViewItem> itemDatas, Context ctx){
        this.itemDatas = itemDatas;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItemDatas(ArrayList<PushListViewItem> itemDatas){
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

    // data-> listview에 뿌려주는 역할
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.push_item,parent,false);
        }

        ImageView productImageView = (ImageView) convertView.findViewById(R.id.push_image) ;
        TextView title = (TextView) convertView.findViewById(R.id.push_title) ;
        TextView remainTime = (TextView)convertView.findViewById(R.id.push_remain_time);
        TextView testView = (TextView)convertView.findViewById(R.id.test);
        PushListViewItem itemData_temp = itemDatas.get(position);
        //productImageView.setImageResource(itemData_temp.img);
        title.setText(itemData_temp.ps_title);
        //price.setText(String.valueOf(itemData_temp.bidPrice));
        remainTime.setText(changeString(itemData_temp.ps_time));
        testView.setText(itemData_temp.ps_detail);
        return convertView;
    }

    private String changeString(String str1){
        if(str1==null || str1.length()==0){
            return "";
        }
        StringTokenizer tokenizer = new StringTokenizer(str1);
        String dates = tokenizer.nextToken("T");
        String tmp = tokenizer.nextToken(".");
        String times = tmp.substring(1, 6);

        String sumdates = dates +" "+ times;

        return sumdates;
    }
}
