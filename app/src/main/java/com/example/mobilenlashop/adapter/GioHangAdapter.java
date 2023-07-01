package com.example.mobilenlashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilenlashop.MainActivity;
import com.example.mobilenlashop.R;
import com.example.mobilenlashop.activity.GioHangActivity;
import com.example.mobilenlashop.model.Gia;
import com.example.mobilenlashop.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> gioHangs;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangs) {
        this.context = context;
        this.gioHangs = gioHangs;
    }
    @Override
    public int getCount() {
        return gioHangs.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {

        public ImageView imgGioHang;
        public TextView txtTenGioHang;
        public TextView txtGiaGioHang;
        public Button btnTru;
        public TextView btnGiaTri;
        public Button btnCong;
//        public CheckBox checkBox;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_gio_hang, null);

            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.txtTenGioHang = view.findViewById(R.id.txtTenGioHang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.txtGiaGioHang);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);
            viewHolder.btnGiaTri = view.findViewById(R.id.btnGiaTri);
            viewHolder.btnCong = view.findViewById(R.id.btnCong);
//            viewHolder.checkBox = view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "d chon", Toast.LENGTH_SHORT).show();
//                GioHangActivity.evenUltil();
//            }
//        });
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtTenGioHang.setText(gioHang.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText("Giá : " + decimalFormat.format(gioHang.getGiaSanPham()) + " VNĐ");

        Glide.with(context).load(gioHang.getHinhAnhSanPham()).into(viewHolder.imgGioHang);

        viewHolder.btnGiaTri.setText(gioHang.getSoLuong() + "");

        int sl = Integer.parseInt(viewHolder.btnGiaTri.getText().toString());

        if (sl >= 15) {
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }

//        ArrayList<Gia> gias;
        final ViewHolder finalViewHolder = viewHolder;
//        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    GioHangActivity.evenUltil();
//
//                }else{
//                    Toast.makeText(context, "Chon 1 MH", Toast.LENGTH_SHORT).show();
//                }
//            }});
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.btnGiaTri.getText().toString()) + 1;
                notifyDataSetChanged();
                int slHienTai = MainActivity.gioHangs.get(position).getSoLuong();
                long giaHienTai = MainActivity.gioHangs.get(position).getGiaSanPham();
                MainActivity.gioHangs.get(position).setSoLuong(slMoiNhat);
                long giaMoiNhat = (long) (giaHienTai * slMoiNhat) / slHienTai;
                MainActivity.gioHangs.get(position).setGiaSanPham(giaMoiNhat);
//                MainActivity.gias.get(position).setGiaMoiNhat(giaMoiNhat);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " VNĐ");

                GioHangActivity.evenUltil();
                if (slMoiNhat > 14){
                    finalViewHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slMoiNhat));
                } else {
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);

                }

            }
        });

        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.btnGiaTri.getText().toString()) - 1;
                notifyDataSetChanged();
                int slHienTai = MainActivity.gioHangs.get(position).getSoLuong();
                long giaHienTai = MainActivity.gioHangs.get(position).getGiaSanPham();
                MainActivity.gioHangs.get(position).setSoLuong(slMoiNhat);
                long giaMoiNhat = (long) (giaHienTai * slMoiNhat) / slHienTai;
                MainActivity.gioHangs.get(position).setGiaSanPham(giaMoiNhat);
//

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " VNĐ");

                GioHangActivity.evenUltil();
                if (slMoiNhat < 2) {
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slMoiNhat));
                } else {
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);

                }
            }
            }

         );

        return view;
    }
}
