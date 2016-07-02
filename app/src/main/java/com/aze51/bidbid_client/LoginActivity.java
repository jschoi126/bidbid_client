package com.aze51.bidbid_client;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessTokenTracker;
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

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends Activity {
    Button loginButton;
    Button joinButton;

    Button shareButton = null;
    //facebook
    LoginButton facebook_loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            FacebookSdk.sdkInitialize(getApplicationContext());

            AppEventsLogger.activateApp(this);
            setContentView(R.layout.activity_login);
            facebook_loginButton = (LoginButton)findViewById(R.id.facebook_LoginButton);
            shareButton = (Button)findViewById(R.id.facebook_LoginButton);
            callbackManager = CallbackManager.Factory.create();

            facebook_loginButton.setOnClickListener(new View.OnClickListener(){
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
            loginButton = (Button)findViewById(R.id.LoginButton);
            joinButton =(Button) findViewById(R.id.join_button);
            loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            joinButton.setOnClickListener(new View.OnClickListener(){
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
}
