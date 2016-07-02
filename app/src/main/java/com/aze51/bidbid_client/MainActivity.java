package com.aze51.bidbid_client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.ViewPager.CustomChangeColorTab;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MainActivity extends AppCompatActivity  {
    public MainActivity reference;
    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    //TopMenuFragment topMenuFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    View rootViewBasic;
    //ViewPager
    ViewPager viewpager;
    LinearLayout currentLinear;
    LinearLayout scheduledLinear;
    LinearLayout approachingLinear;
    //List<RecyclerView> mTabFragments = new ArrayList<>();
    Button btn1;
    Button btn2;
    Button btn3;
    private CustomChangeColorTab changeColorTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        if (!FirebaseApp.getApps(this).isEmpty()) {

            //.getInstance().setPersistenceEnabled(true);
        }

//        Log.d("MyTag", "fcm token : "  + FirebaseInstanceId.getInstance().getToken());
        reference = this;
        initiate();
        show_current_list();
    }
    //Made By Tae Joon 2016 06 27 : 현재 판매중인 목록 프래그먼트로 보여주기.
    public void show_current_list() {
        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        //fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }
    public void show_scheduled_list(){
        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        //fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }
    public void show_approaching_list(){
        fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
        fragmentManager.beginTransaction().add(R.id.ListLayout,listFragment).commit();
        //fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
        fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
    }
    //Made By Tae Joon 2016 06 27 : 초기화
    private void initiate() {
        bottomMenuFragment = new BottomMenuFragment();
        listFragment = new ListFragment();
        //topMenuFragment = new TopMenuFragment();
        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();

        currentLinear = (LinearLayout)findViewById(R.id.linear_current);
        scheduledLinear = (LinearLayout)findViewById(R.id.linear_scheduled);
        approachingLinear = (LinearLayout)findViewById(R.id.linear_approaching);
    }
    public class ListFragment extends Fragment { //view pager 사용해서 리사이클러 뷰 띄움
        public ListFragment(){
            //생성자
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootViewBasic = inflater.inflate(R.layout.list_fragment,container,false);

            viewpager = (ViewPager) rootViewBasic.findViewById(R.id.viewPager);
            //ctx = getActivity().getApplicationContext();
            viewpager.setAdapter(new ViewPagerCustomAdapter(reference));//Main Activity 의 this 를 보내야함.

            changeColorTab = (CustomChangeColorTab)rootViewBasic.findViewById(R.id.change_color_tab);
            changeColorTab.setViewpager((ViewPager)rootViewBasic.findViewById(R.id.viewPager));

            btn1 = (Button) rootViewBasic.findViewById(R.id.current_btn);
            btn2 = (Button) rootViewBasic.findViewById(R.id.scheduled_btn);
            btn3 = (Button) rootViewBasic.findViewById(R.id.approaching_btn);
            return rootViewBasic;
        }
    }

    /*public class TopMenuFragment extends Fragment {
        Button btn1;
        Button btn2;
        Button btn3;
        View rootViewBasic;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootViewBasic = inflater.inflate(R.layout.top_menu_fragment,container,false);

            btn1 = (Button) rootViewBasic.findViewById(R.id.current_btn);
            btn2 = (Button) rootViewBasic.findViewById(R.id.scheduled_btn);
            btn3 = (Button) rootViewBasic.findViewById(R.id.approaching_btn);
            btn1.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View V){
                    viewpager.setCurrentItem(0);
                }
            });
            btn2.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View V){
                    viewpager.setCurrentItem(1);
                }
            });
            btn3.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View V){
                    viewpager.setCurrentItem(2);
                }
            });
            return rootViewBasic;
        }
    }*/
}
