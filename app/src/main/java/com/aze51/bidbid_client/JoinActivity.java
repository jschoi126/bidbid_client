package com.aze51.bidbid_client;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.Join;
import com.aze51.bidbid_client.Network.NetworkService;

import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class JoinActivity extends AppCompatActivity {
    private NetworkService networkService;
    private Button memjoin_button, checkid, phoneAuth;
    PasswordTransformationMethod passWtm;
    private EditText username, userid, userpw, checkpw, editPhoneNumber, editPhoneAuth;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private TextView textViewKeyTimer;

    private boolean checkedPhoneCertficate = false;
    private boolean checkPermintId = false;
    private boolean isKeyExpired = true;

    public String getId;
    public String tmp;
    public String check;
    public String tmpCertifiacation;

    TextView showPrivacy;
    TextView textView;
    Button button;
    CheckBox checkbox;
    RadioButton radiobutton;
    Typeface font, font2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        textView = (TextView)findViewById(R.id.user_name);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.user_id);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.user_password);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.check_password);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.phone_number);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.phone_auth);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        button = (Button)findViewById(R.id.check_id);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        button.setTypeface(font2);

        button = (Button)findViewById(R.id.auth_Phone);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        button.setTypeface(font2);

        button = (Button)findViewById(R.id.memjoin_button);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        button.setTypeface(font2);

        radiobutton = (RadioButton)findViewById(R.id.radiobtn_male);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        radiobutton.setTypeface(font);

        radiobutton = (RadioButton)findViewById(R.id.radiobtn_female);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        radiobutton.setTypeface(font);

        checkbox = (CheckBox)findViewById(R.id.check);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        checkbox.setTypeface(font2);

        textView = (TextView)findViewById(R.id.show_privacy_view);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        textView = (TextView)findViewById(R.id.textview_keytimer);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);

        initView();
        initNetworkService();
        protected_passwd();
        checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId = userid.getText().toString();
                Call<String> CallgetId = networkService.getID(getId);
                CallgetId.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        if(response.isSuccess())
                        {
                            tmp = response.body().toString();
                            check = "valid";
                            if (tmp.equals(check)) {
                                checkPermintId = true;
                                Toast.makeText(getApplicationContext(), "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "중복된 ID입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "다시 한번 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        phoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneAuth.setText("인증번호 확인");
                GetPhoneAuthNum();
            }
        });
        memjoin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpString = editPhoneAuth.getText().toString();
                if(checkedPhoneCertficate) {
                    if (checkPermintId == true) {
                        Join join = new Join();
                        join.user_name = username.getText().toString();
                        join.user_passwd = userpw.getText().toString();
                        if (radioGroupGender.getCheckedRadioButtonId() == 0)
                            join.user_gender = true;
                        else
                            join.user_gender = false;
                        join.user_id = userid.getText().toString();
              /*  if(userpw != checkpw){
                    Toast.makeText(getApplicationContext(),"")
                }*/
                        Call<Join> joinCall = networkService.newMember(join);
                        joinCall.enqueue(new Callback<Join>() {
                            @Override
                            public void onResponse(Response<Join> response, Retrofit retrofit) {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 실패하였습니다.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "ID 중복확인 체크를 진행해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "문자인증을 진행해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showPrivacy = (TextView)findViewById(R.id.show_privacy_view);
        showPrivacy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void initView()
    {
        memjoin_button = (Button)findViewById(R.id.memjoin_button);
        username = (EditText)findViewById(R.id.user_name);
        userid = (EditText)findViewById(R.id.user_id);
        userpw = (EditText)findViewById(R.id.user_password);
        checkpw = (EditText)findViewById(R.id.check_password);
        editPhoneNumber = (EditText)findViewById(R.id.phone_number);
        editPhoneAuth = (EditText)findViewById(R.id.phone_auth);
        checkid = (Button)findViewById(R.id.check_id);
        radioGroupGender = (RadioGroup)findViewById(R.id.radiogrp_gender);
        radioButtonMale = (RadioButton) findViewById(R.id.radiobtn_male);
        radioButtonFemale = (RadioButton) findViewById(R.id.radiobtn_female);
        phoneAuth = (Button)findViewById(R.id.auth_Phone);
        textViewKeyTimer = (TextView) findViewById(R.id.textview_keytimer);

    }
    private void initNetworkService(){
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void protected_passwd() {
        passWtm = new PasswordTransformationMethod();
        userpw.setTransformationMethod(passWtm);
        checkpw.setTransformationMethod(passWtm);
    }

    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
        if (!ps.matcher(source).matches()) {
            return "";
        }
        return null;
        }
    };
    public void GetPhoneAuthNum(){
        if(!TextUtils.isEmpty(editPhoneNumber.getText())) {
            long phoneNum = Long.parseLong(editPhoneNumber.getText().toString());
            Call<String> callPhoneAuth = networkService.getPhoneCertification(phoneNum);
            callPhoneAuth.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
                        tmpCertifiacation = response.body().toString();
                        phoneAuth.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isKeyExpired){
                                    Toast.makeText(getApplicationContext(), "인증번호가 만료되었습니다", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.equals(phoneAuth.getText(), tmpCertifiacation)){
                                    checkedPhoneCertficate = true;
                                    Toast.makeText(getApplicationContext(), "인증번호가 확인되었습니다", Toast.LENGTH_SHORT).show();
                                    phoneAuth.setClickable(false);
                                } else {
                                    checkedPhoneCertficate = false;
                                    Toast.makeText(getApplicationContext(), "인증번호가 올바르지않습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        startRemainingTimeCount();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(JoinActivity.this, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRemainingTimeCount() {
        isKeyExpired = false;
        CountDownTimer timer = new CountDownTimer(3 * 60 * 1000, 1000) {

            int counter = 3 * 60;
            @Override
            public void onTick(long millisUntilFinished) {
                counter--;
                int sec = (int)millisUntilFinished/1000;
                int min = (int)sec / 60;
                int modsec = sec % 60;
                textViewKeyTimer.setText( min + " 분" + modsec +" 초 남음");
            }

            @Override
            public void onFinish() {
                textViewKeyTimer.setText("인증 번호가 만료 되었습니다");
                isKeyExpired = true;
            }
        };

        timer.start();
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
