package com.aze51.bidbid_client.Network;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.LoginActivity;
import com.aze51.bidbid_client.R;

public class ConnectActivity extends AppCompatActivity {



    private static final String IP_PATTERN =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        String ip = "52.78.69.183";
        if(TextUtils.isEmpty(ip) || ip.matches(IP_PATTERN) != true){
            Toast.makeText(getApplicationContext(),"Invaild IP Pattern",Toast.LENGTH_LONG).show();
            return;
        }
        String portString = "3000";
        if(TextUtils.isEmpty(portString) || TextUtils.isDigitsOnly(portString) != true){
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
        application.buildNetworkService(ip, port);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();


    }
}
