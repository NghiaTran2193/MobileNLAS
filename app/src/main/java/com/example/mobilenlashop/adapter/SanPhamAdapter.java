package com.example.mobilenlashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.activity.ChiTietSanPhamActivity;
import com.example.mobilenlashop.model.Account;
import com.example.mobilenlashop.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    private Context context;
    private ArrayList<SanPham> arraySanpham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_new, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arraySanpham.get(position);
        holder.txtTensanpham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasanpham.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + "VNĐ");
        Glide.with(context).load(sanPham.getHinhAnhSanPham()).into(holder.imageviewSanpham);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends  RecyclerView.ViewHolder{
        private Account account;
        public ImageView imageviewSanpham;
        public TextView txtTensanpham, txtGiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageviewSanpham = itemView.findViewById(R.id.imageviewSanpham);
            txtTensanpham = itemView.findViewById(R.id.txtTensanpham);
            txtGiasanpham = itemView.findViewById(R.id.txtGiasanpham);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongTinXe", arraySanpham.get(getPosition()));
                    intent.putExtra("login",account);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<SanPham> getArraySanpham() {
        return arraySanpham;
    }

    public void setArraySanpham(ArrayList<SanPham> arraySanpham) {
        this.arraySanpham = arraySanpham;
    }
}
