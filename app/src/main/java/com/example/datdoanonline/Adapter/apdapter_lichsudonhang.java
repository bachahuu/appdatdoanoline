package com.example.datdoanonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.datdoanonline.Activity.history_order;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.items_order;
import com.example.datdoanonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

public class apdapter_lichsudonhang extends ArrayAdapter<items_order> {
    private history_order main;
    Activity context;
    ArrayList<items_order> arr_history = null;
    int layout_id;
    items_order line_items;
    SimpleDateFormat dateFormat;
    private DatabaseHelper databaseHelper;

    public apdapter_lichsudonhang(@NonNull Activity context, int resource, ArrayList<items_order> arr, history_order main,DatabaseHelper dbHelper) {
        super(context, resource, arr);
        this.context = context;
        this.layout_id = resource;
        this.arr_history = arr;
        this.databaseHelper = dbHelper;
        this.main = main;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout_id, null);
        TextView madon = convertView.findViewById(R.id.order_number);
        TextView ngay_dat_don = convertView.findViewById(R.id.order_date);
        TextView Trangthai = convertView.findViewById(R.id.order_status);
        TextView tongtien = convertView.findViewById(R.id.order_tien);
//        TextView tensanpham = convertView.findViewById(R.id.order_ten);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
//        String tenSanPham = sharedPreferences.getString("tenSanPham_tên_món_an_bạn_muốn_lấy", null);
//         line_items = arr_history.get(position);
//        tensanpham.setText("Sản Phẩmn: " +arr_history.get(position));
//        Button chitietdonhang = convertView.findViewById(R.id.btn_view_order_details);
        madon.setText("DH" + arr_history.get(position).getMadonhang());
        Trangthai.setText(arr_history.get(position).getTrangthai());
        tongtien.setText("Thành Tiền: "+arr_history.get(position).getTongtien()+" VND");
        //Định dạng ngày thành chuỗi
         dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngay_dat_hang = dateFormat.format(arr_history.get(position).getNgaydathang());
        ngay_dat_don.setText(ngay_dat_hang);
//        // Sự kiện click cho nút chi tiết đơn hàng
//        chitietdonhang.setOnClickListener(v -> {
//            // Hiển thị hộp thoại chi tiết đơn hàng
//            showOrderDetailsDialog(Integer.parseInt(line_items.getMadonhang()));
//        });
        return convertView;
    }

    // Phương thức hiển thị hộp thoại chi tiết đơn hàng
//    private void showOrderDetailsDialog(int maDonHang) {
//        // Tạo và cấu hình hộp thoại
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater dialogInflater = context.getLayoutInflater();
//        View dialogView = dialogInflater.inflate(R.layout.dialog_order_chitietdonhang, null);
//        builder.setView(dialogView);
//
//        TextView orderNumberDialog = dialogView.findViewById(R.id.order_number_dialog);
//        TextView orderDateDialog = dialogView.findViewById(R.id.order_date_dialog);
//        TextView orderStatusDialog = dialogView.findViewById(R.id.order_status_dialog);
//        TextView orderItemsDialog = dialogView.findViewById(R.id.order_items_dialog);
//        TextView totalAmountDialog = dialogView.findViewById(R.id.order_gia_dialog); // Thêm view cho tổng tiền
//
//        // Thông tin đơn hàng từ bảng donhang
//        orderNumberDialog.setText("Mã đơn hàng: ĐH" + maDonHang);
//        orderDateDialog.setText("Ngày đặt: " + dateFormat.format(line_items.getNgaydathang()));
//        orderStatusDialog.setText("Trạng thái: " + line_items.getTrangthai());
//
//        // Truy vấn chi tiết đơn hàng từ bảng ChiTietDonHang
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT td.TenMonAn AS tenMonAn, dh.TongTien AS tongTien " +
//                "FROM ChiTietDonHang ctdh " +
//                "JOIN ThucDon td ON ctdh.MaMonAn = td.MaMonAn " +
//                "JOIN DonHang dh ON ctdh.MaDonHang = dh.MaDonHang " +
//                "WHERE ctdh.MaChiTietDonHang = ?", new String[]{String.valueOf(maDonHang)});
//
//        // Chuỗi chứa chi tiết món ăn
//        StringBuilder orderItems = new StringBuilder();
//        double totalOrderAmount = 0;
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String foodName = "";
//                double amount = 0;
//
//                // Kiểm tra và lấy tên món ăn
//                int foodNameIndex = cursor.getColumnIndex("tenMonAn");
//                if (foodNameIndex != -1) {
//                    foodName = cursor.getString(foodNameIndex);
//                } else {
//                    foodName = "Tên món ăn không tìm thấy";
//                }
//
//                // Kiểm tra và lấy tổng tiền
//                int amountIndex = cursor.getColumnIndex("tongTien");
//                if (amountIndex != -1) {
//                    amount = cursor.getDouble(amountIndex);
//                } else {
//                    amount = 0;
//                }
//
//                // Thêm thông tin vào chuỗi
//                orderItems.append("Tên món ăn: ").append(foodName)
//                        .append(", Tổng tiền: ").append(amount)
//                        .append("\n");
//
//                totalOrderAmount += amount; // Cộng dồn vào tổng tiền
//            } while (cursor.moveToNext());
//            cursor.close(); // Đóng cursor sau khi sử dụng
//        }
//
//        // Hiển thị chi tiết món ăn trong hộp thoại
//        orderItemsDialog.setText(orderItems.toString());
//        // Hiển thị tổng tiền của đơn hàng
//        totalAmountDialog.setText("Tổng tiền: " + totalOrderAmount);
//        // Hiển thị hộp thoại
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}
