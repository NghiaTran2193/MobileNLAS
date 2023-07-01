package com.example.mobilenlashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InforUserActivity extends AppCompatActivity {

    private EditText edtTenKhachHang;
    private EditText edtSoDienThoai, edtEmail1;
    private Button btnXacNhan, btnTroVe;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
        init();
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");


        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
            evenButton();
        } else {
            CheckInternet.ShowToast(getApplicationContext(), "Bạn Hãy Kiểm Tra Lại kết Nối");
        }
    }
    private void init(){
        edtTenKhachHang =  findViewById(R.id.edtTenKhachHang);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail1 = findViewById(R.id.edtEmailKH);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTroVe = findViewById(R.id.btnTroVe);
    }
    private void evenButton(){
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtTenKhachHang.getText().toString().trim();
                final  String sdt = edtSoDienThoai.getText().toString().trim();
                final String email = edtEmail1.getText().toString().trim();

                if(ten.length()>0 && sdt.length()>0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String madonhang) {
                            Log.d("madonhang", madonhang);
                            if (Integer.parseInt(madonhang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.urlChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.gioHangs.clear();
                                            CheckInternet.ShowToast(getApplicationContext(), "Bạn Đã Đặt thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("login", account);
                                            startActivity(intent);
                                            CheckInternet.ShowToast(getApplicationContext(), "Mời Bạn Tiếp Tục Mua Hàng");

                                        } else {
                                            CheckInternet.ShowToast(getApplicationContext(), "Dữ Liệu Bị Lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                                ) {
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.gioHangs.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.gioHangs.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.gioHangs.get(i).getTenSanPham());
                                                jsonObject.put("giasanpham", MainActivity.gioHangs.get(i).getGiaSanPham());
                                                jsonObject.put("hinhanhsanpham", MainActivity.gioHangs.get(i).getHinhAnhSanPham());
                                                jsonObject.put("soluongsanpham", MainActivity.gioHangs.get(i).getSoLuong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        protected Map<String, String> getParams() throws AuthFailureError{
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            return  hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckInternet.ShowToast(getApplicationContext(),"Kiểm Tra Lại Dữ Liệu");
                }
                }
        });

    }
}