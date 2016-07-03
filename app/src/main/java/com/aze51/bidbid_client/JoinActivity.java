package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aze51.bidbid_client.Network.Join;
import com.aze51.bidbid_client.Network.NetworkService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    private NetworkService networkService;
    private Button memjoin_button, checkid, phoneAuth;
    PasswordTransformationMethod passWtm;
    private EditText username, userid, userpw, checkpw, editPhoneNumber, editPhoneAuth;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private boolean checkedPhoneCertficate = false;
    private boolean checkPermintId = false;
    public String getId;
    public String tmp;
    public String check;
    public String tmpCertifiacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
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
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
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
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "다시 한번 시도해주세요.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        phoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPhoneAuthNum();
            }
        });
        memjoin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpString = editPhoneAuth.getText().toString();
                if((tmpString.equals(tmpCertifiacation)) && (checkPermintId == true)) {
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
                        public void onResponse(Call<Join> call, Response<Join> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 실패하였습니다.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(Call<Join> call, Throwable t) {
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"입력하신 인증번호가 다릅니다.",Toast.LENGTH_SHORT).show();
                }
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


    }
    private void initNetworkService(){
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void protected_passwd()
    {
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
        long phoneNum = Long.parseLong(editPhoneNumber.getText().toString());
        Call<String> callPhoneAuth = networkService.getPhoneCertification(phoneNum);
        callPhoneAuth.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    tmpCertifiacation = response.body().toString();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
