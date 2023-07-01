package com.example.mobilenlashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdater extends BaseAdapter {
    private ArrayList<LoaiSanPham> arrayListLoaiSP;
    private Context context;
    public  LoaiSanPhamAdater(ArrayList<LoaiSanPham> arrayListLoaiSP, Context context){
        this.arrayListLoaiSP = arrayListLoaiSP;
        this.context = context;
    }

    public class ViewHolder{
        TextView txtTenloaisp;
        ImageView imgLoaisp;
    }
    @Override
    public int getCount() {
        return arrayListLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaisanpham ,parent,false);
            viewHolder.txtTenloaisp = (TextView) convertView.findViewById(R.id.textviewLoaiSP);
            viewHolder.imgLoaisp = (ImageView) convertView.findViewById(R.id.imageviewLoaiSP);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(position);
        viewHolder.txtTenloaisp.setText(loaiSanPham.getTenloaisp());
        Glide.with(convertView.getContext()).load(loaiSanPham.getHinhanhsp()).into(viewHolder.imgLoaisp);
//        Picasso.get().load(loaiSanPham.getHinhanhsp()).placeholder(R.drawable.noimg).error(R.drawable.error).into(viewHolder.imgLoaisp);
        return convertView;
    }
}
