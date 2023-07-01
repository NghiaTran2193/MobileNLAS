package com.example.mobilenlashop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobilenlashop.R;
import com.example.mobilenlashop.model.Account;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbarUserAccount;

    private TextView txtUserName, txtEmail, txtPhone;
    private Button btnLogout;
    private Account account;
    private LinearLayout lnSPDaMua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");
        init();
        actionToolbar();

    }
    private void actionToolbar(){
        setSupportActionBar(toolbarUserAccount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarUserAccount.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        toolbarUserAccount = findViewById(R.id.toolbarUserAccount);
        txtEmail = findViewById(R.id.txtEmail);
        txtUserName = findViewById(R.id.txtUserName);
        txtPhone = findViewById(R.id.txtPhone);
        lnSPDaMua = findViewById(R.id.lnSPDaMua);
        btnLogout = findViewById(R.id.btnLogout);

        txtUserName.setText(account.getUserName());
        txtPhone.setText(account.getPhone());
        txtEmail.setText(account.getEmail());

        lnSPDaMua.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lnSPDaMua:
                Intent intent = new Intent(getApplicationContext(), SanPgamDaMuaActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
                builder.setTitle("Xác Nhận Đăng Xuất");
                builder.setMessage("Bạn Có Chắc Muốn Đăng Xuất?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent1);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;

        }
    }
}