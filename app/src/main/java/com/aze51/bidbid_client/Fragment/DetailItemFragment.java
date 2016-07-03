package com.aze51.bidbid_client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.R;

import org.w3c.dom.Text;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailItemFragment extends Fragment {
    View rootViewBasic;
    //ImageView backimage;
    TextView detail_price;
    TextView detail_time;
    public DetailItemFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.detail_item_cardview,container,false);

        detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        detail_price.setText("120000");
        detail_time.setText("3:45");
        return rootViewBasic;
    }
}
