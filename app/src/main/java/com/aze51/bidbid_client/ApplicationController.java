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

    //created by tae joon jeon. singleton 2016 07 02
    //어플리케이션 전체에서 접근할 상품 객체 생성
    private static List<Product> products;
    private Context mainActivityContext;
    public void setMainActivityContext(Context ctx){
        mainActivityContext = ctx;
    }
    public Context getMainActivityContext(){
        return mainActivityContext;
    }
    private Call<List<Product>> listCall;

    private static String baseUrl = "http://52.78.66.175:3000";

    public void getDataFromServer(){

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
    public static List<Product> getProduct(){return products;}

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
