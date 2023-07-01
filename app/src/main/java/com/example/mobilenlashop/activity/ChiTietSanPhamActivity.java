package com.example.mobilenlashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobilenlashop.MainActivity;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.adapter.SanPhamAdapter;
import com.example.mobilenlashop.model.Account;
import com.example.mobilenlashop.model.GioHang;
import com.example.mobilenlashop.model.SanPham;
import com.example.mobilenlashop.ulti.CheckInternet;
import com.example.mobilenlashop.ulti.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private Toolbar toolbarChiTiet;
    private ImageView imgChiTiet;
    private TextView txtChiTiet,txtGia,txtMoTaChiTiet;
    private Spinner spinner;
    private Button btnThemGioHang;
    private RecyclerView rcyChiTiet;
    private ArrayList<SanPham> arrayListSanPham;
    private SanPhamAdapter sanPhamAdapter;

    private Account account;

    int id = 0;
    int idLoaiSP = 0;
    int gia = 0;
    String tenSP ="";
    String moTa ="";
    String hinhAnhSP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");
        init();
        actionToolbar();
        getData();
        catchevenSpinner();
        evenButton();
        GetDataNew();

    }
    private  void init(){
        toolbarChiTiet = findViewById(R.id.toolbarChiTiet);
        imgChiTiet = findViewById(R.id.imgChiTiet);
        txtChiTiet = findViewById(R.id.txtChiTiet);
        txtMoTaChiTiet = findViewById(R.id.txtMoTaChiTiet);
        txtGia = findViewById(R.id.txtGia);
        spinner = findViewById(R.id.spinner);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);
        rcyChiTiet = findViewById(R.id.rcyChiTiet);
        arrayListSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), arrayListSanPham);
        rcyChiTiet.setHasFixedSize(true);
        rcyChiTiet.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rcyChiTiet.setAdapter(sanPhamAdapter);

    }
    private void GetDataNew(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlSanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    int id = 0;
                    String tensanpham = "";
                    int giasanpham = 0;
                    String hinhanhsanpham = "";
                    String motasanpham = "";
                    int idsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensp");
                            giasanpham = jsonObject.getInt("giasp");
                            hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            motasanpham = jsonObject.getString("motasp");
                            idsanpham = jsonObject.getInt("idsanpham");
                            arrayListSanPham.add(new SanPham(id, tensanpham, giasanpham, hinhanhsanpham, motasanpham, idsanpham,0));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternet.ShowToast(getApplicationContext(),error.toString());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void getData(){
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongTinXe");
         id = sanPham.getId();
         tenSP = sanPham.getTenSanPham();
         gia = sanPham.getGiaSanPham();
         hinhAnhSP = sanPham.getHinhAnhSanPham();
         moTa = sanPham.getMoTaSanPham();
         idLoaiSP = sanPham.getIdLoaiSanPham();

         txtChiTiet.setText(tenSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá : " + decimalFormat.format(gia) + " VNĐ");
        txtMoTaChiTiet.setText(moTa);

        Glide.with(getApplicationContext()).load(hinhAnhSP).into(imgChiTiet);
    }
    private void actionToolbar(){
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void evenButton(){
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangs.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit = false;

                    for (int i = 0; i < MainActivity.gioHangs.size(); i++) {
                        if (MainActivity.gioHangs.get(i).idSP == id) {
                            MainActivity.gioHangs.get(i).setSoLuong(MainActivity.gioHangs.get(i).getSoLuong() + sl);
                            if (MainActivity.gioHangs.get(i).getSoLuong() >= 15) {
                                MainActivity.gioHangs.get(i).setSoLuong(15);
                            }
                            MainActivity.gioHangs.get(i).setGiaSanPham(gia * MainActivity.gioHangs.get(i).getSoLuong());
                            exit = true;
                        }
                    }
                    if (exit == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaNew = soluong * gia;
                        MainActivity.gioHangs.add(new GioHang(id, tenSP, giaNew, hinhAnhSP, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaNew = soluong * gia;

                    MainActivity.gioHangs.add(new GioHang(id, tenSP, giaNew, hinhAnhSP, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                intent.putExtra("login", account);
                Toast.makeText(getApplicationContext(), "Đã Thêm Vào Giỏ Hàng", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void catchevenSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}