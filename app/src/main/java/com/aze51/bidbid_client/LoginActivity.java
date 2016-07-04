package com.aze51.bidbid_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
    String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseInstanceId.getInstance() != null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("MyTag", "Token : " + deviceToken);
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        initView();

        protected_passwd();


        facebook_loginButton = (LoginButton) findViewById(R.id.facebook_LoginButton);
        shareButton = (Button) findViewById(R.id.facebook_LoginButton);
        callbackManager = CallbackManager.Factory.create();
        networkService = ApplicationController.getInstance().getNetworkService();

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
                                    //이 아니라 로딩할 액티비티 실행
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
                login.user_device_token = deviceToken;
                Log.d("MyTag", "login.device_token : " + login.user_device_token);
                Call<Login> loginCall = networkService.getMember(login);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Response<Login> response, Retrofit retrofit) {
                        if(response.isSuccess()){
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
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


    protected void initView() {
        loginButton = (Button) findViewById(R.id.LoginButton);
        joinButton = (Button) findViewById(R.id.join_button);
        getLogin_id = (EditText) findViewById(R.id.login_id);
        getLogin_pw = (EditText) findViewById(R.id.login_pw);
    }

    private void protected_passwd() {
        passWtm = new PasswordTransformationMethod();
        getLogin_pw.setTransformationMethod(passWtm);
    }
}
