package com.aze51.bidbid_client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.DetailItemFragment;
import com.aze51.bidbid_client.Fragment.DetailTitleFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.ViewPager.CustomChangeColorTab;
import com.aze51.bidbid_client.ViewPager.ListItemData;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public MainActivity reference;
    public static MainActivity getReference;
    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment;
    DetailItemFragment detailItemFragment;
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
    //List<RecyclerView> mTabFragments = new ArrayList<>();
    Button btn1;
    Button btn2;
    Button btn3;

    TextView detail_price;
    TextView detail_time;
    private CustomChangeColorTab changeColorTab;

    //Recycler View
    ArrayList<ListItemData> itemDatas;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;

    //test

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

            //.getInstance().setPersistenceEnabled(true);
        } else {
        }
//        Log.d("MyTag", "fcm token : "  + FirebaseInstanceId.getInstance().getToken());
        reference = this;
        initiate();
        show_current_list();
        //show_detail_list();
    }

    public void show_detail_list() {
        fragmentManager.beginTransaction().replace(R.id.TitleLayout,detailTitleFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.ListLayout,detailItemFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.BottomLayout,bottomMenuFragment).commit();
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
        initNetworkService();
        getDataFromServer();
    }

    public void getDataFromServer(){

        listCall = networkService.getContents();
        listCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.i("TAG", "response");
                if(flag==0) {
                    viewPagerCustomAdapter.pbVisible();
                   flag=1;
                }
                if (response.isSuccessful()) {
                    Log.i("TAG", "response succeed");
                    viewPagerCustomAdapter.pbInvisible();
                    products = response.body();
                    viewPagerCustomAdapter.notifyDataSetChanged();
                    //List<Products> listproducts = respose.body()
                    //for (Product p : products) {
                    //    itemDatas.add(new ListItemData(p));
                    //}
                    //itemDatas.add(new ListItemData(R.mipmap.b, "이름", "가격", "3:57 남음"));
                } else {
                    viewPagerCustomAdapter.pbInvisible();
                    Toast.makeText(reference.getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                viewPagerCustomAdapter.pbInvisible();
                Log.i("TAG","0 fail");
            }
        });
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

           // btn1 = (Button) rootViewBasic.findViewById(R.id.current_btn);
            //btn2 = (Button) rootViewBasic.findViewById(R.id.scheduled_btn);
            //btn3 = (Button) rootViewBasic.findViewById(R.id.approaching_btn);
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
        show_current_list();
    }

    private void initNetworkService(){
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }
}
