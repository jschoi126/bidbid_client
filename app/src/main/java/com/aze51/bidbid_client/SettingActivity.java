package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.User;
import com.aze51.bidbid_client.service.PrefUtils;
import com.facebook.CallbackManager;

import org.w3c.dom.Text;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SettingActivity extends AppCompatActivity {

    ImageView back_image;
    TextView terms_of_use;
    TextView privacy;
    TextView logout;
    NetworkService networkService;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        back_image = (ImageView)findViewById(R.id.setting_back_image);
        back_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        terms_of_use = (TextView)findViewById(R.id.setting_terms_of_use);
        terms_of_use.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsOfUseActivity.class);
                startActivity(intent);
            }
        });
        privacy = (TextView)findViewById(R.id.setting_privacy);
        privacy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(intent);
            }
        });
        logout = (TextView)findViewById(R.id.setting_logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                networkService = ApplicationController.getInstance().getNetworkService();
                Call<User> logoutCall = networkService.logout();
                logoutCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        if(response.isSuccess()){
                            if(ApplicationController.getInstance().GetIsFacebook()==true){
                                ApplicationController.getInstance().SetFacebook(false);
                                PrefUtils.clearCurrentUser(getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }
}
