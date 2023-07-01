package com.example.mobilenlashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.media.ExifInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobilenlashop.activity.ContractActivity;
import com.example.mobilenlashop.activity.GioHangActivity;
import com.example.mobilenlashop.activity.InformationActivity;
import com.example.mobilenlashop.activity.IphoneActivity;
import com.example.mobilenlashop.activity.OppoActivity;
import com.example.mobilenlashop.activity.SamSungActivity;
import com.example.mobilenlashop.activity.UserAccountActivity;
import com.example.mobilenlashop.activity.XiaomiActivity;
import com.example.mobilenlashop.adapter.LoaiSanPhamAdater;
import com.example.mobilenlashop.adapter.SanPhamAdapter;
import com.example.mobilenlashop.model.Account;
import com.example.mobilenlashop.model.Gia;
import com.example.mobilenlashop.model.GioHang;
import com.example.mobilenlashop.model.LoaiSanPham;
import com.example.mobilenlashop.model.SanPham;
import com.example.mobilenlashop.ulti.CheckInternet;
import com.example.mobilenlashop.ulti.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<GioHang> gioHangs;
    public static ArrayList<Gia> gias;

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ArrayList<LoaiSanPham> arrayListLoaiSP;
    private LoaiSanPhamAdater loaiSanPhamAdater;
    private ArrayList<SanPham> arrayListSanPham;
    private SanPhamAdapter sanPhamAdapter;
    int id =0;
    private String tenLoaiSP ="";
    private String hinhLoaiSP ="";
    private Account account;

    private CardView cardViewMeat;
    private CardView cardViewFish;
    private CardView cardViewFruits;
    private CardView cardViewVegetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");
        iniViews();
        if (CheckInternet.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDataLoaiSanPham();
            GetDataNew();
            CatchOnItemListView();
        }else {
            CheckInternet.ShowToast(getApplicationContext(),"Hãy Kiểm Tra Kết Nối Internet");
            finish();
        }
    }
    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        // Gắn địa chỉ vào imageview
        mangquangcao.add("https://imgs.viettelstore.vn/Images/Product/ProductImage/dien-thoai/Apple/iPhone%2014%20Pro%20Max%20128/iPhone-14-Pro-Max-1.jpg");
        mangquangcao.add("https://cdn2.cellphones.com.vn/358x358,webp,q100/media/catalog/product/g/a/galaxys23ultra_front_green_221122_2.jpg");
        mangquangcao.add("https://i02.appmifile.com/746_operator_vn/27/02/2023/961b57536e594fe98a4344f5ba333c41.png");
        mangquangcao.add("https://images.fpt.shop/unsafe/fit-in/1200x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2023/5/31/638211678411165145_F-C1_1200x300.png");
        mangquangcao.add("https://images.fpt.shop/unsafe/fit-in/640x250/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2023/6/22/638230539997426590_F_M1_640x250.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
//            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_out);
        viewFlipper.setInAnimation(animation_slide_in);
    }
    private void GetDataLoaiSanPham(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSP = jsonObject.getString("tenLoaisp");
                            hinhLoaiSP = jsonObject.getString("hinhanhLoaisp");
                            arrayListLoaiSP.add(new LoaiSanPham(id, tenLoaiSP, hinhLoaiSP));
                            loaiSanPhamAdater.notifyDataSetChanged();//Update
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayListLoaiSP.add(5, new LoaiSanPham(0, "Contract", "https://pigllet.000webhostapp.com/Datvexe/phone.png"));
                    arrayListLoaiSP.add(6, new LoaiSanPham(0, "Thông Tin", "https://pigllet.000webhostapp.com/Datvexe/phone.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternet.ShowToast(getApplicationContext(),error.toString());
            }
    });
        requestQueue.add(jsonArrayRequest);
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
    private void CatchOnItemListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())){
                            Intent intent= new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, IphoneActivity.class);
                            intent.putExtra("idloaisanpham", arrayListLoaiSP.get(position).getId());
                            intent.putExtra("login",account);
                            startActivity(intent);
                        }else {
                            CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,SamSungActivity.class);
                            intent.putExtra("idloaisanpham", arrayListLoaiSP.get(position).getId());
                            intent.putExtra("login",account);
                            startActivity(intent);
                        }else {
                            CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, XiaomiActivity.class);
                            intent.putExtra("idloaisanpham", arrayListLoaiSP.get(position).getId());
                            intent.putExtra("login",account);
                            startActivity(intent);
                        }else {
                            CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, OppoActivity.class);
                            intent.putExtra("idloaisanpham", arrayListLoaiSP.get(position).getId());
                            intent.putExtra("login",account);
                            startActivity(intent);
                        }else {
                            CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContractActivity.class);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
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
    private void  ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void iniViews(){
        toolbar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewFlipper);
        listView = findViewById(R.id.listView);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recycleView);
        drawerLayout = findViewById(R.id.drawerLayout);
        cardViewMeat = findViewById(R.id.cardviewMeat);
        cardViewFish = findViewById(R.id.cardviewFish);
        cardViewFruits = findViewById(R.id.cardviewFruits);
        cardViewVegetable = findViewById(R.id.cardviewVegetables);
//        /////////////////////////
        arrayListLoaiSP = new ArrayList<>();
        arrayListLoaiSP.add(new LoaiSanPham(0, "Home","https://pigllet.000webhostapp.com/Datvexe/home.png"));
        loaiSanPhamAdater = new LoaiSanPhamAdater(arrayListLoaiSP,getApplicationContext());
        listView.setAdapter(loaiSanPhamAdater);
//        ////////////////////////////////
        arrayListSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), arrayListSanPham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
//      ///////////////////////
        if (gioHangs != null){

        }else {
            gioHangs = new ArrayList<>();
        }
        cardViewMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this, IphoneActivity.class);
                    intent.putExtra("idloaisanpham", arrayListLoaiSP.get(1).getId());
                    intent.putExtra("login",account);
                    startActivity(intent);
                }else {
                    CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                }
            }
        });


        cardViewFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this,SamSungActivity.class);
                    intent.putExtra("idloaisanpham", arrayListLoaiSP.get(2).getId());
                    intent.putExtra("login",account);
                    startActivity(intent);
                }else {
                    CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                }
            }
        });

        cardViewFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this, XiaomiActivity.class);
                    intent.putExtra("idloaisanpham", arrayListLoaiSP.get(3).getId());
                    intent.putExtra("login",account);
                    startActivity(intent);
                }else {
                    CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                }
            }
        });

        cardViewVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this, OppoActivity.class);
                    intent.putExtra("idloaisanpham", arrayListLoaiSP.get(4).getId());
                    intent.putExtra("login",account);
                    startActivity(intent);
                }else {
                    CheckInternet.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối Internet");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}