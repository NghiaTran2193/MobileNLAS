package com.example.mobilenlashop.activity;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.adapter.IphoneAdapter;
import com.example.mobilenlashop.model.Account;
import com.example.mobilenlashop.model.SanPham;
import com.example.mobilenlashop.ulti.CheckInternet;
import com.example.mobilenlashop.ulti.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IphoneActivity extends AppCompatActivity {

    private Toolbar toolbarIphone;
    private ListView rcyIphone;
    private View footerView;

    private IphoneAdapter iphoneAdapter;
    private ArrayList<SanPham> sanPhams;

    int idIphone = 0;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
    private Account account;

    private MHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);

        Intent intent= getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");
        init();

        if (CheckInternet.haveNetworkConnection(getApplicationContext())){
            getID();
            actionToolbar();
            getData(page);
            loadMoreData();
        }else {
            CheckInternet.ShowToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối Internet!!!");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.mnLogin:
                Intent intent1 = new Intent(getApplicationContext(), UserAccountActivity.class);
                intent1.putExtra("login", account);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        toolbarIphone = findViewById(R.id.toolbarIphone);;
        rcyIphone = findViewById(R.id.rcyIphone);
        sanPhams = new ArrayList<>();
        iphoneAdapter = new IphoneAdapter(getApplicationContext(),sanPhams);
        rcyIphone.setAdapter(iphoneAdapter);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);

        mHandler = new MHandler();

    }
    private void loadMoreData(){
        rcyIphone.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongTinXe", sanPhams.get(position));
                intent.putExtra("login", account);
                startActivity(intent);
            }
        });
        rcyIphone.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount
                && totalItemCount != 0
                && isLoading == false && limitData ==false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void getData(int Page){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlIphone + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idLoaiSp = 0;
                int gia = 0;
                String tenSp = "";
                String moTa = "";
                String hinhAnh = "";
                float rating = 0;
                if (response != null && response.length() != 2) {
                    rcyIphone.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenSp = jsonObject.getString("tensp");
                            gia = jsonObject.getInt("giasp");
                            hinhAnh = jsonObject.getString("hinhanhsp");
                            moTa = jsonObject.getString("motasp");
                            idLoaiSp = jsonObject.getInt("idsanpham");
                            rating = jsonObject.getLong("rating");
                            sanPhams.add(new SanPham(id, tenSp, gia, hinhAnh, moTa, idLoaiSp,rating));
                            iphoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    rcyIphone.removeFooterView(footerView);
                    CheckInternet.ShowToast(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<String, String>();
                    //param.put("idloaisanpham", String.valueOf(idXe));
                    param.put("idloaisanpham", String.valueOf(idIphone));
                    return param;
                }
            };
        requestQueue.add(stringRequest);
    }
    private void actionToolbar(){
        setSupportActionBar(toolbarIphone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarIphone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getID(){
        idIphone = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giaTriLoaiSP", idIphone + "");
    }
    public class MHandler extends Handler{
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    rcyIphone.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}