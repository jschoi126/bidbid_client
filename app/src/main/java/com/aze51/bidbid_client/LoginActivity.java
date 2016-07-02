package com.aze51.bidbid_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.Login;
import com.aze51.bidbid_client.Network.NetworkService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    Button shareButton = null;
    //facebook
    LoginButton facebook_loginButton;
    CallbackManager callbackManager;

    //
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

        if(FirebaseInstanceId.getInstance() != null)
            Log.d("MyTag", "Token : " + FirebaseInstanceId.getInstance().getToken());

        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        initView();
        Connecting();
        initNetworkService();
        protected_passwd();

        facebook_loginButton = (LoginButton) findViewById(R.id.facebook_LoginButton);
        shareButton = (Button) findViewById(R.id.facebook_LoginButton);
        callbackManager = CallbackManager.Factory.create();

        facebook_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult result) {
                        GraphRequest request;
                        request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {
                                if (response.getError() != null) {
                                } else {
                                    Log.i("TAG", "user: " + user.toString());
                                    Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                                    setResult(RESULT_OK);
                                    //finish();
                                    //MainActivity 실행
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("test", "Error: " + error);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }
                });
            }
        });
        loginButton = (Button) findViewById(R.id.LoginButton);
        joinButton = (Button) findViewById(R.id.join_button);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void Connecting() {
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

        //ApplicationController application = new ApplicationController();
        ApplicationController application = ApplicationController.getInstance();
        application.buildNetworkService(ip, port);
    }

    protected void initView() {
        loginButton = (Button) findViewById(R.id.LoginButton);
        joinButton = (Button) findViewById(R.id.join_button);
        getLogin_id = (EditText) findViewById(R.id.login_id);
        getLogin_pw = (EditText) findViewById(R.id.login_pw);
    }

    private void initNetworkService() {
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void protected_passwd() {
        passWtm = new PasswordTransformationMethod();
        getLogin_pw.setTransformationMethod(passWtm);
    }
}
