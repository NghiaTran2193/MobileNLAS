package com.example.mobilenlashop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IphoneAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arraySanPham;

    public IphoneAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }
    public class ViewHolder{
        public TextView txtIphone;
        public TextView txtGiaIphone;
        public TextView txtMoTa;
        public ImageView imgIphone;
        public RatingBar ratingBar;
    }

    @Override
    public int getCount() {
        return arraySanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_iphone, null);
            viewHolder.imgIphone = convertView.findViewById(R.id.imgIphone);
            viewHolder.txtIphone = convertView.findViewById(R.id.txtIphone);
            viewHolder.txtGiaIphone = convertView.findViewById(R.id.txtGiaIphone);
            viewHolder.txtMoTa = convertView.findViewById(R.id.txtMoTa);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtIphone.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaIphone.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + "VNĐ");
        viewHolder.ratingBar.setRating(sanPham.getRating());
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhAnhSanPham()).into(viewHolder.imgIphone);
        return convertView;
    }
}
