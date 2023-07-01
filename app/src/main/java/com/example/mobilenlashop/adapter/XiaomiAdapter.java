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

public class XiaomiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arraySanPham;

    public XiaomiAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }
    public class ViewHolder{
        public TextView txtXiao;
        public TextView txtGiaXiao;
        public TextView txtMoTa;
        public ImageView imgXiao;
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
        XiaomiAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new XiaomiAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_xiaomi, null);
            viewHolder.imgXiao = convertView.findViewById(R.id.imgXiao);
            viewHolder.txtXiao = convertView.findViewById(R.id.txtXiao);
            viewHolder.txtGiaXiao = convertView.findViewById(R.id.txtGiaXiao);
            viewHolder.txtMoTa = convertView.findViewById(R.id.txtMoTa);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtXiao.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaXiao.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + "VNĐ");
        viewHolder.ratingBar.setRating(sanPham.getRating());
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhAnhSanPham()).into(viewHolder.imgXiao);
        return convertView;
    }
}
