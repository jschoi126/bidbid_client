package com.aze51.bidbid_client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aze51.bidbid_client.R;


/**
 * Created by jeon3029 on 16. 6. 27..
 */
public class TopMenuFragment extends Fragment {
    TextView text1;
    TextView text2;
    TextView text3;
    View rootViewBasic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootViewBasic = inflater.inflate(R.layout.top_menu_fragment,container,false);
        text1 = (TextView)rootViewBasic.findViewById(R.id.current_text);
        text2 = (TextView)rootViewBasic.findViewById(R.id.scheduled_text);
        text3 = (TextView)rootViewBasic.findViewById(R.id.approaching_text);
        return rootViewBasic;
    }
}
