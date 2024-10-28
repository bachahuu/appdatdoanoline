package com.example.datdoanonline.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Adapter.apdapter_lichsudonhang;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.items_giohang;
import com.example.datdoanonline.Domain.items_order;
import com.example.datdoanonline.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class history_order extends AppCompatActivity {
    SQLiteDatabase DB;
    DatabaseHelper SQL;
    ListView lv_order;
    ArrayList<items_order> ds_order;
    apdapter_lichsudonhang adap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQL = new DatabaseHelper(this);
        DB = SQL.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsudonhang);
        lv_order = findViewById(R.id.order_history_list_view);
                ds_order = new ArrayList<>();
        int manguoidung = getManguoidung();
        Cursor cur = DB.rawQuery(
                "SELECT dh.MaDonHang, dh.NgayDatHang, dh.TrangThai , dh.TongTien FROM DonHang dh WHERE dh.MaNguoiDung = " + manguoidung,
                null);
        if (cur.moveToFirst()){
            do {
                String madonhang = cur.getString(0);
               String ngaydatdon = cur.getString(1);
                String trangthai = cur.getString(2);
                String tongtien = cur.getString(3);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date ngaydathang = null;
                try {
                    ngaydathang = sdf.parse(ngaydatdon);
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle the exception as needed
                }
                ds_order.add(new items_order(madonhang,ngaydathang,trangthai,tongtien));
            }while (cur.moveToNext());
        }
        cur.close();
//        try {
//            // Định dạng ngày hiện tại
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            // Thêm các đơn hàng mẫu vào danh sách
//            ds_order.add(new items_order("DH001", sdf.parse("2024-10-10"), "Đã giao"));
//            ds_order.add(new items_order("DH002", sdf.parse("2024-10-11"), "Đang giao"));
//            ds_order.add(new items_order("DH003", sdf.parse("2024-10-12"), "Đã hủy"));
//            ds_order.add(new items_order("DH004", sdf.parse("2024-10-13"), "Đã giao"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        adap = new apdapter_lichsudonhang(this,R.layout.single_history_donhang,ds_order,this,databaseHelper);
        lv_order.setAdapter(adap);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                items_order dohang = ds_order.get(i);
            }
        });
    }
    public int getManguoidung() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("username", "default_value"); // "default_value" là giá trị mặc định nếu không tìm thấy
        return SQL.getusername(Username); // Lấy mã người dùng từ DatabaseHelper
    }
}
