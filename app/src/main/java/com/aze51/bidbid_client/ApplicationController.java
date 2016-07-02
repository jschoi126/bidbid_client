package com.aze51.bidbid_client;

import android.app.Application;

import com.aze51.bidbid_client.Network.NetworkService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leekh on 2016-06-18.
 */
public class ApplicationController extends Application {

    public static ApplicationController getInstance(){
        return instance;
    }

    // Applcation 인스턴스 선언
    private static ApplicationController instance;

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
