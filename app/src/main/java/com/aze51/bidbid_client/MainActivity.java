package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.DetailItemFragment;
import com.aze51.bidbid_client.Fragment.DetailTitleFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.Network.User;
import com.aze51.bidbid_client.ViewPager.CustomChangeColorTab;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;
import com.google.firebase.FirebaseApp;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {
    public MainActivity reference;

    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    DetailItemFragment detailItemFragment;
    int pageState = 0; // 0 = main, 1 = detail
    //TopMenuFragment topMenuFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    DetailTitleFragment detailTitleFragment;
    View rootViewBasic;

    //ViewPager
    ViewPager viewpager;
    LinearLayout currentLinear;
    LinearLayout scheduledLinear;
    LinearLayout approachingLinear;

    TextView detail_price;
    TextView detail_time;
    private CustomChangeColorTab changeColorTab;

    public NetworkService networkService;
    public Call<List<Product>> listCall;
    public List<Product> products;
    int flag=0;

    ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        if (!FirebaseApp.getApps(this).isEmpty()) {
        }
        else {
        }
        connectServer();
//        Log.d("MyTag", "fcm token : "  + FirebaseInstanceId.getInstance().getToken());
        reference = this;
        initiate();
        show_current_list();
        //show_detail_list();
    }

    /**
     *
     */
    public void connectServer() {
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<User> loginTest = networkService.getSession();
        loginTest.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if(response.body() != null)
                    Log.d("MyTag",response.body().user_id);
                connectSuccess(response.code());
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void connectSuccess(int statusCode) {
        Intent intent;
        if (statusCode == 200) {
            Log.d("MyTag", "Has session");

            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else if (statusCode == 401) {
            Log.d("MyTag", "Has no session ");
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        } else {
            return;
        }
    }

    /**
     *
     *
     */


    public void show_detail_list() {
        pageState = 1;
        fragmentManager.beginTransaction().replace(R.id.TitleLayout,detailTitleFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.ListLayout,detailItemFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.BottomLayout,bottomMenuFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(pageState ==1){//on detail page
            show_current_list();
        }
        else if (pageState ==0){//on main page
            super.onBackPressed();
        }
    }
    //Made By Tae Joon 2016 06 27 : 현재 판매중인 목록 프래그먼트로 보여주기.
    public void show_current_list() {
        pageState = 0;
        if (flag==0) {
            fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().add(R.id.ListLayout, listFragment).commit();
            //fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
            fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
            flag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout,titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout,listFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout,bottomMenuFragment).commit();
            flag = 1;
        }
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
        ApplicationController.getInstance().setMainActivityContext(this);

        bottomMenuFragment = new BottomMenuFragment();
        listFragment = new ListFragment();
        detailItemFragment = new DetailItemFragment();
        //topMenuFragment = new TopMenuFragment();

        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();
        detailTitleFragment = new DetailTitleFragment();

        currentLinear = (LinearLayout)findViewById(R.id.linear_current);
        scheduledLinear = (LinearLayout)findViewById(R.id.linear_scheduled);
        approachingLinear = (LinearLayout)findViewById(R.id.linear_approaching);

        detail_price = (TextView)findViewById(R.id.detail_price);
        detail_time = (TextView)findViewById(R.id.detail_time);
        Log.i("TAG","init service in main");
       // initNetworkService();
       // getDataFromServer();
    }
    public class ListFragment extends Fragment { //view pager 사용해서 리사이클러 뷰 띄움
       // public Context ctx;
        public ListFragment(){
            //생성자
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootViewBasic = inflater.inflate(R.layout.list_fragment,container,false);
            viewpager = (ViewPager) rootViewBasic.findViewById(R.id.viewPager);
            //viewpager.setAdapter(new ViewPagerCustomAdapter(reference));//Main Activity 의 this 를 보내야함.
            viewpager.setAdapter(viewPagerCustomAdapter);
            viewpager.setOffscreenPageLimit(0);
            //ctx = getActivity().getApplicationContext();
            changeColorTab = (CustomChangeColorTab)rootViewBasic.findViewById(R.id.change_color_tab);
            changeColorTab.setViewpager((ViewPager)rootViewBasic.findViewById(R.id.viewPager));

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
    // 처음으로 서버로 부터 값을 가져오는
    @Override
    protected void onResume(){
        super.onResume();
        initiate();
        //show_current_list();
    }
}
