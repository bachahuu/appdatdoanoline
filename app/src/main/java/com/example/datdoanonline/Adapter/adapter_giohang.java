package com.example.datdoanonline.Adapter;

import android.app.Activity;
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
        super(context, resource,arr);
        this.context=context;
        this.layout_id =resource;
        this.arr_giohang =arr;
        this.main = main;

        // Khởi tạo DatabaseHelper và lấy cơ sở dữ liệu ghi
        DatabaseHelper sqlHelper = new DatabaseHelper(context);
        this.db = sqlHelper.getWritableDatabase();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        convertView = inflater.inflate(layout_id,null);
        TextView ten_item = convertView.findViewById(R.id.item_name);
        TextView gia_item = convertView.findViewById(R.id.items_gia);
        TextView soluong = convertView.findViewById(R.id.item_soluong);
        ImageView hinhanhsanpham = convertView.findViewById(R.id.item_giohang_image);
        ImageView trusoluong = convertView.findViewById(R.id.items_tru);
        ImageView congsoluong = convertView.findViewById(R.id.items_cong);

        items_giohang line_items = arr_giohang.get(position);
        ten_item.setText(arr_giohang.get(position).getTenSanPham());
        // Chuyển đổi giá sản phẩm và số lượng sang chuỗi trước khi gán
        gia_item.setText(String.valueOf(arr_giohang.get(position).getGiaSanPham()) + " VND");
        soluong.setText(String.valueOf(arr_giohang.get(position).getSoLuong()));
        // Hiển thị hình ảnh từ URI
        String imageUriString = line_items.getHinhAnhSanPham();
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            try {
                // Chuyển đổi URI thành Bitmap và hiển thị
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
                hinhanhsanpham.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                hinhanhsanpham.setImageResource(R.drawable.baseline_border_color_24); // Hình ảnh mặc định nếu có lỗi
            }
        } else {
            hinhanhsanpham.setImageResource(R.drawable.baseline_border_color_24); // Hình ảnh mặc định nếu không có URI
        }
        trusoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (line_items.getSoLuong()>1){
                    line_items.setSoLuong(line_items.getSoLuong()-1);
                    soluong.setText(String.valueOf(line_items.getSoLuong()));
                }else {
                    if (position >= 0 && position < arr_giohang.size()) {
                        items_giohang item = arr_giohang.get(position);
                        int maGioHang = main.getmagiohang();
                        int maMonAn = item.getMaMonAn();
                        String deleteQuery = "DELETE FROM GioHang WHERE MaGioHang = " + maGioHang + " AND MaMonAn = " + maMonAn;
                        try {
                            db.execSQL(deleteQuery);
                            arr_giohang.remove(position);
                            notifyDataSetChanged();
                            main.capNhatTongTien();
                            Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Không thể xóa sản phẩm. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Không thể xóa sản phẩm. Vị trí không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();// Cập nhật lại ListView
                main.capNhatTongTien();  // Gọi hàm cập nhật tổng tiền
//                main.capnhatsoluongmathang();  // Gọi hàm cập nhật số lượng trên biểu tượng giỏ hàng
            }
        });

        congsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line_items.setSoLuong(line_items.getSoLuong()+1);
                soluong.setText(String.valueOf(line_items.getSoLuong()));
                notifyDataSetChanged();
                main.capNhatTongTien();
            }
        });
        return  convertView;

    }
}
