package com.example.datdoanonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datdoanonline.Activity.admin_order;
import com.example.datdoanonline.Activity.shopping_cart;
import com.example.datdoanonline.Domain.admin_items_order;
import com.example.datdoanonline.Domain.items_giohang;
import com.example.datdoanonline.Domain.items_order;
import com.example.datdoanonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class adapter_order_admin extends ArrayAdapter<admin_items_order> {
    private admin_order main;
    Activity context;
    ArrayList<admin_items_order> arr_donhang = null;
    int layout_id;
    public adapter_order_admin(@NonNull Activity context, int resource, ArrayList<admin_items_order> arr_donhang, admin_order main) {
        super(context, resource, arr_donhang);
        this.main = main;
        this.context = context;
        this.arr_donhang = arr_donhang;
        this.layout_id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout_id,null);
        TextView madon = convertView.findViewById(R.id.tvOrderId);
        TextView ngay_dat_don = convertView.findViewById(R.id.tvOrderDate);
        TextView tong_tien = convertView.findViewById(R.id.tvOrderTotal);
        TextView Trangthai = convertView.findViewById(R.id.tvOrderStatus);
        TextView manguoidung = convertView.findViewById(R.id.tvUserId);
        TextView thongtinlienlac = convertView.findViewById(R.id.tvContactInfo);
        TextView diachigiaohang = convertView.findViewById(R.id.tvShippingAddress);
        TextView magiamgia = convertView.findViewById(R.id.tvCouponCode);
        Button btn_updatestatus = convertView.findViewById(R.id.btnUpdateOrder);
        Button btn_delete_donhang = convertView.findViewById(R.id.btnDeleteOrder);
        magiamgia.setText("Voucher: "+arr_donhang.get(position).getMaGiamGia());
        manguoidung.setText("ID_USER:"+String.valueOf(arr_donhang.get(position).getMaNguoiDung()));
        thongtinlienlac.setText(arr_donhang.get(position).getThongtinlienlac());
        diachigiaohang.setText("Đia chỉ:"+arr_donhang.get(position).getDiachigiaohang());
        madon.setText("Mã đơn: "+"DH"+String.valueOf(arr_donhang.get(position).getMaDonHang()));
        tong_tien.setText("Thành Tiền: "+String.valueOf(arr_donhang.get(position).getTongTien())+" VND");
        Trangthai.setText("Trạng thái:"+arr_donhang.get(position).getTrangThai());
       admin_items_order line_items = arr_donhang.get(position);
        //Định dạng ngày thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngay_dat_hang = dateFormat.format(arr_donhang.get(position).getNgaydathang());
        ngay_dat_don.setText("Ngày đặt: "+ngay_dat_hang);
        btn_updatestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.showUpdateStatusDialog(arr_donhang.get(position));
            }
        });
        btn_delete_donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.showDeleteOrderDialog(arr_donhang.get(position));
            }
        });
        return  convertView;
    }
}
