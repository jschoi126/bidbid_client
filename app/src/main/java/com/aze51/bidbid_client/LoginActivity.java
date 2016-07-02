package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.Login;
import com.aze51.bidbid_client.Network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    NetworkService networkService;
    Button loginButton;
    Button joinButton;
    PasswordTransformationMethod passWtm;
    EditText getLogin_id, getLogin_pw;
    private static final String IP_PATTERN =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Connecting();
        initNetworkService();
        protected_passwd();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                login.user_id = getLogin_id.getText().toString();
                login.user_passwd = getLogin_pw.getText().toString();
                Call<Login> loginCall = networkService.getMember(login);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"망했어요",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void Connecting()
    {
        String ip = "52.78.66.175";
        if(TextUtils.isEmpty(ip) || ip.matches(IP_PATTERN) != true){
            Toast.makeText(getApplicationContext(), "Invaild IP Pattern", Toast.LENGTH_LONG).show();
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
    }

    protected void initView()
    {
        loginButton = (Button)findViewById(R.id.LoginButton);
        joinButton =(Button) findViewById(R.id.join_button);
        getLogin_id = (EditText)findViewById(R.id.login_id);
        getLogin_pw = (EditText)findViewById(R.id.login_pw);
    }

    private void initNetworkService(){
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void protected_passwd()
    {
        passWtm = new PasswordTransformationMethod();
        getLogin_pw.setTransformationMethod(passWtm);
    }
}
