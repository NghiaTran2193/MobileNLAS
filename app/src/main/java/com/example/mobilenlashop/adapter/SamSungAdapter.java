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

public class SamSungAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arraySanPham;

    public SamSungAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }
    public class ViewHolder{
        public TextView txtSS;
        public TextView txtGiaSS;
        public TextView txtMoTa;
        public ImageView imgSS;
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
        SamSungAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new SamSungAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_samsung, null);
            viewHolder.imgSS = convertView.findViewById(R.id.imgSS);
            viewHolder.txtSS = convertView.findViewById(R.id.txtSS);
            viewHolder.txtGiaSS = convertView.findViewById(R.id.txtGiaSS);
            viewHolder.txtMoTa = convertView.findViewById(R.id.txtMoTa);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(viewHolder);


        }else {
            viewHolder = (SamSungAdapter.ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtSS.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSS.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + "VNĐ");
        viewHolder.ratingBar.setRating(sanPham.getRating());
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhAnhSanPham()).into(viewHolder.imgSS);
        return convertView;
    }
}

