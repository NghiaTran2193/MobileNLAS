package com.example.mobilenlashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mobilenlashop.R;

public class ContractActivity extends AppCompatActivity {
    private Toolbar toolbarLH;
    private LinearLayout lnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        iniViews();
        clickButton();
        ActionBar();
    }
    private void clickButton(){
        lnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCall();
            }
        });
    }
    private void iniViews(){
        toolbarLH = findViewById(R.id.toolbarLienhe);
        lnCall = findViewById(R.id.lnCall);
    }
    private void phoneCall(){
        String phone = "0886085585";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: " + phone));
        startActivity(intent);
    }

    private void  ActionBar(){
        setSupportActionBar(toolbarLH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}