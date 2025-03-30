package com.example.datdoanonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.datdoanonline.Activity.shopping_cart;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.items_giohang;
import com.example.datdoanonline.R;

import java.io.IOException;
import java.util.ArrayList;

public class adapter_giohang extends ArrayAdapter<items_giohang> {
    private shopping_cart main;
    Activity context;
    ArrayList<items_giohang> arr_giohang = null;
    int layout_id;
    private SQLiteDatabase db;

    public adapter_giohang(@NonNull Activity context, int resource, ArrayList<items_giohang> arr, shopping_cart main) {
        super(context, resource, arr);
        this.context = context;
        this.layout_id = resource;
        this.arr_giohang = arr;
        this.main = main;

        DatabaseHelper sqlHelper = new DatabaseHelper(context);
        this.db = sqlHelper.getWritableDatabase();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout_id, null);

        TextView ten_item = convertView.findViewById(R.id.item_name);
        TextView gia_item = convertView.findViewById(R.id.items_gia);
        TextView soluong = convertView.findViewById(R.id.item_soluong);
        ImageView hinhanhsanpham = convertView.findViewById(R.id.item_giohang_image);
        ImageView trusoluong = convertView.findViewById(R.id.items_tru);
        ImageView congsoluong = convertView.findViewById(R.id.items_cong);

        items_giohang line_items = arr_giohang.get(position);
        ten_item.setText(line_items.getTenSanPham());
        gia_item.setText(String.format("%,d VND", line_items.getGiaSanPham()));
        soluong.setText(String.valueOf(line_items.getSoLuong()));

        // Xử lý hiển thị ảnh
        String imagePath = line_items.getHinhAnhSanPham();
        if (imagePath != null) {
            if (imagePath.startsWith("drawable/")) {
                // Xử lý ảnh từ drawable
                String drawableName = imagePath.replace("drawable/", "").split("\\.")[0];
                int resId = context.getResources().getIdentifier(
                        drawableName,
                        "drawable",
                        context.getPackageName()
                );

                if (resId != 0) {
                    Glide.with(context)
                            .load(resId)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(hinhanhsanpham);
                } else {
                    hinhanhsanpham.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                // Xử lý ảnh từ URI (thiết bị hoặc URL)
                try {
                    Uri imageUri = Uri.parse(imagePath);
                    Glide.with(context)
                            .load(imageUri)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(hinhanhsanpham);
                } catch (Exception e) {
                    hinhanhsanpham.setImageResource(R.drawable.ic_launcher_background);
                }
            }
        } else {
            hinhanhsanpham.setImageResource(R.drawable.ic_launcher_background);
        }

        // Xử lý nút giảm số lượng
        trusoluong.setOnClickListener(view -> {
            if (line_items.getSoLuong() > 1) {
                line_items.setSoLuong(line_items.getSoLuong() - 1);
                soluong.setText(String.valueOf(line_items.getSoLuong()));
                updateQuantityInDatabase(line_items.getMaMonAn(), line_items.getSoLuong());
            } else {
                deleteItem(position, line_items.getMaMonAn());
            }
            notifyDataSetChanged();
            main.capNhatTongTien();
        });

        // Xử lý nút tăng số lượng
        congsoluong.setOnClickListener(view -> {
            line_items.setSoLuong(line_items.getSoLuong() + 1);
            soluong.setText(String.valueOf(line_items.getSoLuong()));
            updateQuantityInDatabase(line_items.getMaMonAn(), line_items.getSoLuong());
            notifyDataSetChanged();
            main.capNhatTongTien();
        });

        return convertView;
    }

    private void updateQuantityInDatabase(int maMonAn, int newQuantity) {
        try {
            String updateQuery = "UPDATE GioHang SET SoLuong = " + newQuantity +
                    " WHERE MaMonAn = " + maMonAn +
                    " AND MaNguoiDung = " + main.getManguoidung();
            db.execSQL(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Cập nhật số lượng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem(int position, int maMonAn) {
        try {
            String deleteQuery = "DELETE FROM GioHang WHERE MaMonAn = " + maMonAn +
                    " AND MaNguoiDung = " + main.getManguoidung();
            db.execSQL(deleteQuery);
            arr_giohang.remove(position);
            Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}