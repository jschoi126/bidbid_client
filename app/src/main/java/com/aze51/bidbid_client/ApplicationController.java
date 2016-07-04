package com.aze51.bidbid_client;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leekh on 2016-06-18.
 */
public class ApplicationController extends Application {
    //created by tae joon jeon. singleton 2016 07 02
    //어플리케이션 전체에서 접근할 상품 객체 생성
    private static List<Product> products, products1, products2, products3;
    private static List<List<Product>> getProducts;
    private static int postion;
    //private static Auction auction;
    private static String user;
    private static int productPos;
    private Context mainActivityContext;

    public void setMainActivityContext(Context ctx){
        mainActivityContext = ctx;
    }
    public Context getMainActivityContext(){
        return mainActivityContext;
    }

    private Call<List<Product>> listCall;
    private Call<List<List<Product>>> getlistCall;
    public void getDataFromServer(){

        getlistCall = networkService.getProducts();
        getlistCall.enqueue(new Callback<List<List<Product>>>() {
            @Override
            public void onResponse(Call<List<List<Product>>> call, Response<List<List<Product>>> response) {
                if(response.isSuccessful()){
                    getProducts = response.body();
                    products1 = getProducts.get(0);
                    products2 = getProducts.get(1);
                    products3 = getProducts.get(2);
                   // tmp = products2.get(1).product_name;
                   // tmp2 = products2.get(1).product_name;
                }
            }

            @Override
            public void onFailure(Call<List<List<Product>>> call, Throwable t) {

            }
        });
        listCall = networkService.getContents();
        listCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.i("TAG", "response");

                if (response.isSuccessful()) {
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
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //viewPagerCustomAdapter.pbInvisible();
                Log.i("TAG","0 fail");
            }
        });
    }
    public static List<Product> getProducts(int id){
        if(id == 0){
            return products1;
        }
        else if(id == 1){
            return products2;
        }
        else {
            return products3;
        }
    }
    public static int getPosition(){return postion;}
    public static void setPosition(int pos){postion = pos;}
    public static int getPos(){return productPos;}
    public static void setPos(int pos){productPos = pos;}
    public static String getUserId(){return user;}
    public static void setUserId(String id){user = id;}
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
    }
    // 네트워크 인터페이스 구현
    public NetworkService getNetworkService() {
          return networkService;
    }
    private NetworkService networkService;
    private String baseUrl;

    // 인터페이스 구현 메소드
    public void buildNetworkService(String ip, int port) {
        synchronized (ApplicationController.class){
            if(networkService == null){
                baseUrl = String.format("http://%s:%d",ip,port);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T':HH:mm:ss.SSS'Z'")
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
