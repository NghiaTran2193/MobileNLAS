package com.example.mobilenlashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilenlashop.MainActivity;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.model.Account;
import com.example.mobilenlashop.ulti.CheckInternet;
import com.example.mobilenlashop.ulti.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog pDialog;
    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnSignin;
    private TextView txtRegister;

    private  static  final String KEY_USERNAME = "username";
    private  static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);
        txtRegister = findViewById(R.id.txtRegister);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);

        btnSignin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignin:
                String username = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                loginAccount(username, password);
                break;
            case R.id.txtRegister:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
    public void loginAccount(final  String username, final String password){
        if(checkEditText(edtUserName) && checkEditText(edtPassword)) {
            pDialog.show();
            StringRequest requestLogin = new StringRequest(Request.Method.POST, Server.urlLogin, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    String message = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("success") == 1) {
                            Account account = new Account();
                            account.setUserName(jsonObject.getString("username"));
                            account.setEmail(jsonObject.getString("email"));
                            account.setPhone(jsonObject.getString("sodienthoai"));
                            message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("login", account);
                            startActivity(intent);
                        } else {
                            message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CheckInternet.ShowToast(getApplicationContext(), "ỏnespone");

                    }
                    pDialog.dismiss();
                }
            },
                    new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                        }
                    }
                )
                {
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put(KEY_USERNAME, username);
                        params.put(KEY_PASSWORD, password);
                        return params;
                    }

                };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(requestLogin);
            }
        }
    private boolean checkEditText(EditText editText){
        if(editText.getText().toString().trim().length() >0) return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }
}