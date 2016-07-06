package com.aze51.bidbid_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    ImageView back_image;
    TextView terms_of_use;
    TextView privacy;
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

    }
}
