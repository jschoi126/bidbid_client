package com.aze51.bidbid_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.User;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SplashActivity extends AppCompatActivity {

    TextView textView;
    Typeface font;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean hasSession;

    NetworkService networkService;
    private static final String IP_PATTERN =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    Handler handler;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("Cookie-Session", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        textView = (TextView)findViewById(R.id.bidtime);
        font = Typeface.createFromAsset(getAssets(), "Arista2.0 light.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.aze51);
        font = Typeface.createFromAsset(getAssets(), "Arista2.0 light.ttf");
        textView.setTypeface(font);

        //CookieManager cookiemanager = CookieManager.getInstance();
        connecting();
        initNetworkService();
        ApplicationController.getInstance().getDataFromServer();
        connectServer();
    }
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
                networkError();
            }
        });
    }
    public void connectSuccess(int statusCode) {
        Intent intent;
        if(statusCode == 200){
            Log.d("MyTag", "Has session");

            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(statusCode == 401){
            Log.d("MyTag", "Has no session ");
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }
        else{
            return;
        }

       new Thread() {
           @Override
           public void run() {
               try {
                   Thread.sleep(1500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }.run();

        startActivity(intent);
        finish();
    }

    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();

    }

    protected void connecting() {
        String ip = "52.78.66.175";
        if (TextUtils.isEmpty(ip) || ip.matches(IP_PATTERN) != true) {
            Toast.makeText(getApplicationContext(), "Invaild IP Pattern", Toast.LENGTH_LONG).show();
            return;
        }
        String portString = "3000";
        if (TextUtils.isEmpty(portString) || TextUtils.isDigitsOnly(portString) != true) {
            Toast.makeText(getApplicationContext(), "Invaild port value",
                    Toast.LENGTH_LONG).show();
            return;
        }
        int port = Integer.parseInt(portString);
        if (0 > port || port > 65535) {
            Toast.makeText(getApplicationContext(), "Invalid port value",
                    Toast.LENGTH_LONG).show();
            return;
        }

        ApplicationController application = ApplicationController.getInstance();
        //application.buildService();
        //application.buildNetworkService(ip, port);
    }

    public void networkError() {
        Toast.makeText(getApplicationContext(), "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
    }
}