package com.aze51.bidbid_client;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Leekh on 2016-06-18.
 */
public class ApplicationController extends Application {
    //search text
    private String searchtext;
    public void SetSearchText(String s){searchtext = s;}
    public String GetSearchText(){
        return searchtext;}

    private int gridViewOnClicked = 0;
    public void SetGridViewOnClick(int num){gridViewOnClicked = num;}
    public int GetGridViewOnClicked(){return gridViewOnClicked;}


    //created by tae joon jeon. singleton 2016 07 02
    //어플리케이션 전체에서 접근할 상품 객체 생성
    private static List<Product> products, products1, products2, products3,products4,products5,products6;
    //product1 : 진행 2: 예정 3: 마감 4: favorite 5: mypage  6 :search
    public void SetProducts3(List<Product> temp){products3 = temp;}
    public void SetProducts4(List<Product> temp){products4 = temp;}
    public void SetProducts5(List<Product> temp){products5 = temp;}
    public void SetProducts6(List<Product> temp){products6 = temp;}
    private static List<List<Product>> getProducts;
    private static int postion;
    private static int registerId;
    //private static Auction auction;
    private static String user;
    private static boolean isFacebook = false;
    private static boolean flag = false;
    private static int detail = 0;
    public void SetFacebook(){isFacebook = true;}
    public boolean GetIsFacebook(){return isFacebook;}

    private static int productPos;
    private Context mainActivityContext;

    public void setMainActivityContext(Context ctx){
        mainActivityContext = ctx;
    }
    public Context getMainActivityContext(){
        return mainActivityContext;
    }

    private Call<List<Product>> listCall;

    private static String baseUrl = "http://52.78.69.183:3000";
    private Call<List<List<Product>>> getlistCall;
    public void getDataFromServer(){
        getlistCall = networkService.getProducts();
        getlistCall.enqueue(new Callback<List<List<Product>>>() {
            @Override
            public void onResponse(retrofit.Response<List<List<Product>>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    getProducts = response.body();
                    products1 = getProducts.get(0);
                    products2 = getProducts.get(1);
                    products3 = getProducts.get(2);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
        listCall = networkService.getContents();
        listCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(retrofit.Response<List<Product>> response, Retrofit retrofit) {
                Log.i("TAG", "response");

                if (response.isSuccess()) {
                    Log.i("TAG", "response succeed");
                    //viewPagerCustomAdapter.pbInvisible();
                    products = response.body();
                } else {
                    Log.i("TAG","else");
                    //viewPagerCustomAdapter.pbInvisible();
                    //Toast.makeText(reference.getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //viewPagerCustomAdapter.pbInvisible();
                Log.i("TAG","0 fail");
            }
        });
    }
    public static List<Product> getProducts(int id){
        if(id == 0){//진행
            return products1;
        }
        else if(id == 1){//예정
            return products2;
        }
        else if(id == 2){//마감
            return products3;
        }
        else if(id ==3){//favorite
            return products4;
        }
        else if(id == 4){//mypage
            return products5;
        }
        else if(id == 5){//search
            return products6;
        }
        else {
            Log.i("TAG","else get Products");
            return  products;
        }
    }
    public static int getPosition(){return postion;}
    public static void setPosition(int pos){postion = pos;}

    public static int getPos(){return productPos;}
    public static void setPos(int pos){productPos = pos;}
    public static String getUserId(){return user;}
    public static void setUserId(String id){user = id;}
    public static int getRegisterId(){return registerId;};
    public static void setRegisterId(int registerid) {registerId = registerid;}
    public static int gets(){return detail;};
    public static void sets(int details) {detail = details;}
    public static boolean getFlag(){return flag;}
    public static void setFlag(boolean flags){flag = flags;}
    //public static Auction getAuction(){return auction;}
    //public static void setAuction(Auction a){auction = a;}


    // Applcation 인스턴스 선언
    private static ApplicationController instance;
    public static ApplicationController getInstance(){
        return instance;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        ApplicationController.instance = this;
        buildService();
    }

    // 네트워크 인터페이스 구현
    public NetworkService getNetworkService() {
          return networkService;
    }
    private NetworkService networkService;

    public void buildService() {

        /*Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ")
                .create();
        GsonConverterFactory factory = GsonConverterFactory.create(gson);*/
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(cookieManager);
        client.interceptors().clear();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Log.i("MyTag", "요청 주소 : " + original.httpUrl());
                //Request.Builder builder = original.newBuilder().put(RequestBody);
                Request.Builder requestBuilder = original.newBuilder()
                        .header("State", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        networkService =  retrofit.create(NetworkService.class);
    }


    // 인터페이스 구현 메소드
    public void buildNetworkService(String ip, int port) {
        synchronized (ApplicationController.class){

            if(networkService == null){
                //baseUrl = String.format("http://%s:%d",ip,port);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                GsonConverterFactory factory = GsonConverterFactory.create(gson);

                //Retrofit 설정
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(factory)
                        .build();

                // 인터페이스 구현
                networkService = retrofit.create(NetworkService.class);
            }
        }
    }
}
