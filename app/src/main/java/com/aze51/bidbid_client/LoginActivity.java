package com.aze51.bidbid_client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.AppIntro.BidBidIntro;
import com.aze51.bidbid_client.Network.FaceBookLogin;
import com.aze51.bidbid_client.Network.Login;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.service.FaceBookUser;
import com.aze51.bidbid_client.service.PrefUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends Activity {
    Button shareButton = null;
    //facebook
    LoginButton facebook_loginButton;
    CallbackManager callbackManager;

    NetworkService networkService;
    Button loginButton;
    Button joinButton;
    String userID;
    PasswordTransformationMethod passWtm;
    EditText getLogin_id, getLogin_pw;
    String deviceToken;
    TextView textView;
    Typeface font, font2, font3;

    ProgressDialog progressDialog;
    FaceBookUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);


        textView = (TextView)findViewById(R.id.member);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);
        //font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        //font3 = Typeface.createFromAsset(getAssets(), "NanumGothicExtraBold.ttf");
        //font4 = Typeface.createFromAsset(getAssets(), "Arista2.0.ttf");

        textView = (TextView)findViewById(R.id.bidtime);
        font3 = Typeface.createFromAsset(getAssets(), "Arista2.0 light.ttf");
        textView.setTypeface(font3);

        textView = (TextView)findViewById(R.id.or);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.login_id);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.login_pw);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.join_button);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.LoginButton);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        textView.setTypeface(font2);
        //textView.append("텍스트 뷰에 들어갈 내용");
        if(FirebaseInstanceId.getInstance() != null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("MyTag", "Token : " + deviceToken);
        }
        initView();

        protected_passwd();


        facebook_loginButton = (LoginButton) findViewById(R.id.facebook_LoginButton);
        callbackManager = CallbackManager.Factory.create();
        networkService = ApplicationController.getInstance().getNetworkService();
        if(PrefUtils.getCurrentUser(LoginActivity.this) != null){
            Intent intent;
            if(ApplicationController.getInstance().GetSharedTutorial()==1){
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            else{
                intent = new Intent(getApplicationContext(), BidBidIntro.class);
            }
            String str = ApplicationController.getInstance().GetSharedFaceBook();
            if(str!=null){
                Toast.makeText(LoginActivity.this,str + "님 페이스북으로 로그인 하셨습니다. ",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(LoginActivity.this, "페이스북으로 로그인 하셨습니다. ", Toast.LENGTH_LONG).show();
            }
            ApplicationController.getInstance().SetFacebook(true);
            startActivity(intent);
            finish();
        }
        /*
        facebook_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Intent intent;
                if(ApplicationController.getInstance().GetSharedTutorial()==1){
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                }
                startActivity(intent);
                finish();
            }
            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/
        /*
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
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);

                                    //sharedpreference가 있으면 MainActivity로
                                    //Intent intent = new Intent(getApplicationContext(), BidBidIntro.class);
                                    Intent intent;
                                    if(ApplicationController.getInstance().GetSharedTutorial()==1){
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                    }
                                    else {
                                        intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                                    }

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
        });*/
        loginButton = (Button) findViewById(R.id.LoginButton);
        joinButton = (Button) findViewById(R.id.join_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                login.user_id = getLogin_id.getText().toString();
                login.user_passwd = getLogin_pw.getText().toString();
                ApplicationController.setUserId(login.user_id);
                login.user_device_token = deviceToken;
                Log.d("MyTag", "login.device_token : " + login.user_device_token);
                Call<Login> loginCall = networkService.getMember(login);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Response<Login> response, Retrofit retrofit) {
                        if(response.isSuccess()){
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();
                            //sharedpreference가 있으면 MainActivity로
                            Intent intent;
                            if(ApplicationController.getInstance().GetSharedTutorial()==1){
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                            }
                            else{
                                intent = new Intent(getApplicationContext(), BidBidIntro.class);
                            }
                            ApplicationController.getInstance().SetFacebook(false);
                            startActivity(intent);
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            startActivity(intent);
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
    @Override
    protected void onResume() {
        super.onResume();
        callbackManager=CallbackManager.Factory.create();
        facebook_loginButton= (LoginButton)findViewById(R.id.facebook_LoginButton);
        facebook_loginButton.setReadPermissions("public_profile", "email","user_friends");
        TextView btnLogin= (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                facebook_loginButton.performClick();
                facebook_loginButton.setPressed(true);
                facebook_loginButton.invalidate();
                facebook_loginButton.registerCallback(callbackManager, mCallBack);
                facebook_loginButton.setPressed(false);
                facebook_loginButton.invalidate();
            }
        });
    }
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.e("response: ", response + "");
                            try {
                                user = new FaceBookUser();
                                user.facebookID = object.getString("id").toString();
                                user.email = object.getString("email").toString();
                                user.name = object.getString("name").toString();
                                user.gender = object.getString("gender").toString();
                                PrefUtils.setCurrentUser(user,LoginActivity.this);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this,"welcome "+user.name,Toast.LENGTH_LONG).show();

                            FaceBookLogin login = new FaceBookLogin();
                            Log.i("TAG",user.facebookID);

                            login.facebookID = user.facebookID;
                            login.user_device_token = deviceToken;
                            ApplicationController.getInstance().SetSharedFaceBook(user.name);

                            Call<FaceBookLogin> loginCall = networkService.getFaceBookMember(login);
                            loginCall.enqueue(new Callback<FaceBookLogin>() {
                                @Override
                                public void onResponse(Response<FaceBookLogin> response, Retrofit retrofit) {
                                    if(response.isSuccess()){
                                        Toast.makeText(getApplicationContext(),user.name +" 님 페이스북 로그인 성공",Toast.LENGTH_LONG).show();
                                        //sharedpreference가 있으면 MainActivity로
                                        Intent intent;
                                        if(ApplicationController.getInstance().GetSharedTutorial()==1){
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                        }
                                        else{
                                            intent = new Intent(getApplicationContext(), BidBidIntro.class);
                                        }
                                        ApplicationController.getInstance().SetFacebook(false);
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
                            /*
                            ApplicationController.getInstance().SetSharedFaceBook(user.name);
                            Intent intent;
                            if(ApplicationController.getInstance().GetSharedTutorial()==1){
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                            }
                            else{
                                intent = new Intent(getApplicationContext(), BidBidIntro.class);
                            }
                            startActivity(intent);
                            finish();*/
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };
}
