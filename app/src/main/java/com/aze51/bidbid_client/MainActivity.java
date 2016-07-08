package com.aze51.bidbid_client;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aze51.bidbid_client.Fragment.BottomMenuFragment;
import com.aze51.bidbid_client.Fragment.DetailBottomFragment;
import com.aze51.bidbid_client.Fragment.DetailItemFragment;
import com.aze51.bidbid_client.Fragment.DetailTitleFragment;
import com.aze51.bidbid_client.Fragment.EmptyFragment;
import com.aze51.bidbid_client.Fragment.FavoriteFragment;
import com.aze51.bidbid_client.Fragment.MypageFragment;
import com.aze51.bidbid_client.Fragment.PushListFragment;
import com.aze51.bidbid_client.Fragment.SearchFragment;
import com.aze51.bidbid_client.Fragment.SearchListOnClickedFragment;
import com.aze51.bidbid_client.Fragment.TitleFragment;
import com.aze51.bidbid_client.ViewPager.CustomChangeColorTab;
import com.aze51.bidbid_client.ViewPager.ViewPagerCustomAdapter;
import com.google.firebase.FirebaseApp;
import com.viewpagerindicator.TabPageIndicator;


public class MainActivity extends AppCompatActivity {
    public MainActivity reference;

    //Fragment Variable
    BottomMenuFragment bottomMenuFragment;
    ListFragment listFragment; //Main list (ViewPager)
    DetailItemFragment detailItemFragment;
    DetailBottomFragment detailBottomFragment;
    FragmentManager fragmentManager;
    TitleFragment titleFragment;
    DetailTitleFragment detailTitleFragment;
    EmptyFragment emptyFragment;
    EmptyFragment emptyFragmentDetail;
    SearchListOnClickedFragment searchListOnClickedFragment;
    //for listfragment

    //태준 작업중
    PushListFragment pushListFragment;
    FavoriteFragment favoriteFragment;
    MypageFragment mypageFragment;
    SearchFragment searchFragment;

    ///
    int pageState = 0; // 0 = main, 1 = detail
                       // 2 = favorite, 3 = mypage 4 = search 5 = push
                       // 6 = search list on clicked
    int fromState = 0;

    public int getPageState(){return pageState;}
    public int getFromState(){return fromState;}

    //ViewPager

    LinearLayout currentLinear;
    LinearLayout scheduledLinear;
    LinearLayout approachingLinear;



    TextView detail_price;
    TextView detail_time;


    int currentFlag = 0;
    int detailFlag = 0;
    int favoriteFlag = 0;
    int myPageFlag = 0;
    int searchFlag = 0;
    int searchClickedFlag = 0;
    int pushListFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//splash Activity
        if (!FirebaseApp.getApps(this).isEmpty()) {
        }
        else {
        }
//        Log.d("MyTag", "fcm token : "  + FirebaseInstanceId.getInstance().getToken());
        reference = this;
        initiate();
        show_current_list();
    }
    public void show_detail_list() {
        fromState = pageState;
        pageState = 1;
        if(detailFlag == 0){
            fragmentManager.beginTransaction().replace(R.id.TitleLayout,detailTitleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout,detailItemFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout,emptyFragment).commit();
            fragmentManager.beginTransaction().add(R.id.detail_bottom_layout,detailBottomFragment).commit();
            detailFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout,detailTitleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout,detailItemFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout,emptyFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout,detailBottomFragment).commit();
        }
    }
    @Override
    public void onBackPressed() {
        if(pageState == 1 && fromState == 3) {//mypage and detail
            show_mypage_list();
        }
        else if(pageState == 1 && fromState == 2){//from favorite detail
            show_favorite_list();
        }
        else if(pageState == 1 && fromState == 5){//from push detail
            show_push_list();
        }
        else if(pageState == 1 && fromState == 6){//from search on clicked detail
            show_search_list_onclicked();
        }
        else if(pageState == 1 && fromState == 2){//from favorite detail
            show_favorite_list();
        }
        else if(pageState ==1){//on detail page
            show_current_list();
        }
        else if (pageState ==0){//on main page
            super.onBackPressed();
        }
        else if (pageState ==2){//on favorite page
            super.onBackPressed();
        }
        else if (pageState ==3){//on my page
            super.onBackPressed();
        }
        else if (pageState ==4){//on search page
            super.onBackPressed();
        }
        else if (pageState ==5){//on push page
            super.onBackPressed();
        }
        else if(pageState == 6){//on search on clicked
            show_search_list();
        }
        else{
            super.onBackPressed();
        }
    }
    //Made By Tae Joon 2016 06 27 : 현재 판매중인 목록 프래그먼트로 보여주기.
    public void show_current_list() {
        fromState = pageState;
        if(pageState == 1){//from detail page
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout, emptyFragmentDetail).commit();
        }
        pageState = 0;
        if (currentFlag == 0) {
            fragmentManager.beginTransaction().add(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().add(R.id.ListLayout, listFragment).commit();
            //fragmentManager.beginTransaction().add(R.id.TopMenuLayout, topMenuFragment).commit();
            fragmentManager.beginTransaction().add(R.id.BottomLayout, bottomMenuFragment).commit();
            currentFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout,titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout,listFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout,bottomMenuFragment).commit();
            currentFlag = 1;
        }
    }
    //Made By Tae Joon 2016 06 27 : 초기화
    private void initiate() {
        ApplicationController.getInstance().setMainActivityContext(this);

        bottomMenuFragment = new BottomMenuFragment();
        listFragment = new ListFragment();
        detailItemFragment = new DetailItemFragment();
        detailBottomFragment = new DetailBottomFragment();
        emptyFragment = new EmptyFragment();
        emptyFragmentDetail = new EmptyFragment();
        pushListFragment = new PushListFragment();
        favoriteFragment = new FavoriteFragment();
        mypageFragment = new MypageFragment();
        searchFragment = new SearchFragment();
        searchListOnClickedFragment = new SearchListOnClickedFragment();

        fragmentManager = getSupportFragmentManager();
        titleFragment = new TitleFragment();
        detailTitleFragment = new DetailTitleFragment();

        currentLinear = (LinearLayout)findViewById(R.id.linear_current);
        scheduledLinear = (LinearLayout)findViewById(R.id.linear_scheduled);
        approachingLinear = (LinearLayout)findViewById(R.id.linear_approaching);

        detail_price = (TextView)findViewById(R.id.detail_price);


    }
    public void show_favorite_list() {
        if(pageState == 1){//from detail page
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout, emptyFragmentDetail).commit();
        }
        fromState = pageState;
        pageState = 2;
        if(favoriteFlag ==0) {
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, favoriteFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
            favoriteFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, favoriteFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
        }
    }
    public void show_mypage_list() {
        if(pageState == 1){//from detail page
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout, emptyFragmentDetail).commit();
        }
        fromState = pageState;
        pageState = 3;
        if(myPageFlag ==0) {
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, mypageFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
            myPageFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, mypageFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
        }
    }
    public void show_search_list() {
        fromState = pageState;
        pageState = 4;
        if(searchFlag ==0) {
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, searchFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
            searchFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, searchFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
        }
    }
    public void show_push_list() {
        if(pageState == 1){//from detail page
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout, emptyFragmentDetail).commit();
        }
        fromState = pageState;
        pageState = 5;
        if(pushListFlag ==0) {
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, pushListFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
            pushListFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, pushListFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
        }
    }
    public void show_search_list_onclicked(){
        if(pageState == 1){//from detail page
            fragmentManager.beginTransaction().replace(R.id.detail_bottom_layout, emptyFragmentDetail).commit();
        }
        fromState = pageState;
        pageState = 6;
        if(searchClickedFlag ==0){
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, detailTitleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, searchListOnClickedFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
            searchClickedFlag = 1;
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.TitleLayout, titleFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.ListLayout, searchListOnClickedFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.BottomLayout, bottomMenuFragment).commit();
        }
    }
    //protected static final String[] CONTENT = new String[] { "마감임박","진행","예정" };
    public static class ListFragment extends Fragment { //view pager 사용해서 리사이클러 뷰 띄움
       // public Context ctx;
       View rootViewBasic;
        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
            ApplicationController.getInstance().getDataFromServer();
            super.onViewStateRestored(savedInstanceState);
        }
        ViewPager viewpager;
        private CustomChangeColorTab changeColorTab;
        TabPageIndicator mIndicator;
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(ApplicationController.getInstance().getMainActivityContext());
        public ListFragment(){
            //생성자
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootViewBasic = inflater.inflate(R.layout.list_fragment,container,false);
            viewpager = (ViewPager) rootViewBasic.findViewById(R.id.viewPager);
            //viewpager.setAdapter(new ViewPagerCustomAdapter(reference));//Main Activity 의 this 를 보내야함.
            //FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

            viewpager.setAdapter(viewPagerCustomAdapter);
            //viewpager.setAdapter(adapter);
            viewpager.setOffscreenPageLimit(0);
            mIndicator = (TabPageIndicator)rootViewBasic.findViewById(R.id.id_indicator);
            //mIndicator.setBackgroundColor(Color.RED);
            //mIndicator.setDrawingCacheBackgroundColor(Color.RED);
            mIndicator.setViewPager(viewpager);
            //ctx = getActivity().getApplicationContext();
            changeColorTab = (CustomChangeColorTab)rootViewBasic.findViewById(R.id.change_color_tab);
            changeColorTab.setViewpager((ViewPager)rootViewBasic.findViewById(R.id.viewPager));
            return rootViewBasic;
        }
    }
    /*
    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }*/

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
        ///ApplicationController.getInstance().setPosition(viewpager.getCurrentItem());
        //show_current_list();
    }
    @Override
    protected void onPause(){
        super.onPause();
       // ApplicationController.getInstance().setPosition(viewpager.getCurrentItem());
    }
}
