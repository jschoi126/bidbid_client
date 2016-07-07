package com.aze51.bidbid_client;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneAuthActivity extends AppCompatActivity {

    TextView textView;
    Typeface font, font2;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        editText = (EditText)findViewById(R.id.phone_number);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        editText.setTypeface(font);

        editText = (EditText)findViewById(R.id.input_num);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        editText.setTypeface(font);

        button = (Button)findViewById(R.id.facebook_confirm_btn);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        button.setTypeface(font);

        textView = (TextView)findViewById(R.id.phone_confirm);
        font2 = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
        textView.setTypeface(font2);

        textView = (TextView)findViewById(R.id.rest_time);
        font = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(font);
    }
}
