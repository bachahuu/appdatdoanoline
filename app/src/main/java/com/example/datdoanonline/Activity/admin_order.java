package com.example.datdoanonline.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.datdoanonline.Adapter.adapter_order_admin;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.admin_items_order;
import com.example.datdoanonline.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class admin_order extends AppCompatActivity {
    SQLiteDatabase DB;
    DatabaseHelper SQL;
    ListView lv_order;
    Button btn_capnhat, btn_xoadonhang;
    ArrayList<admin_items_order> list_order;
    adapter_order_admin adapter;
    admin_items_order donhang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQL = new DatabaseHelper(this);
        DB = SQL.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order);
        lv_order = findViewById(R.id.listViewOrders);
        list_order = new ArrayList<>();

        // Truy vấn dữ liệu từ bảng DonHang
        Cursor cur = DB.rawQuery("SELECT * FROM DonHang", null);
        if (cur.moveToFirst()) {
            do {
                int madon = cur.getInt(cur.getColumnIndexOrThrow("MaDonHang"));
                int manguoidung = cur.getInt(cur.getColumnIndexOrThrow("MaNguoiDung"));
                String ngaydatdon = cur.getString(cur.getColumnIndexOrThrow("NgayDatHang"));
                double tongtien = cur.getDouble(cur.getColumnIndexOrThrow("TongTien"));
                String trangthai = cur.getString(cur.getColumnIndexOrThrow("TrangThai"));
                String magiamgia = cur.getString(cur.getColumnIndexOrThrow("MaGiamGia"));
                String diachigiaohang = cur.getString(cur.getColumnIndexOrThrow("DiaChiGiaoHang"));
                String thongtinlienlac = cur.getString(cur.getColumnIndexOrThrow("ThongTinLienLac"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date ngaydathang = null;
                try {
                    ngaydathang = sdf.parse(ngaydatdon);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                list_order.add(new admin_items_order(madon, manguoidung, ngaydathang, tongtien, trangthai, magiamgia, diachigiaohang, thongtinlienlac));
            } while (cur.moveToNext());
        }
        cur.close();
        adapter = new adapter_order_admin(this, R.layout.admin_order_items, list_order, this);
        lv_order.setAdapter(adapter);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                donhang = list_order.get(i);
            }
        });
    }

    public void showUpdateStatusDialog(admin_items_order order) {
        final String[] cacTrangThaiDonHang = {
                "Chờ xử lý",
                "Đang chuẩn bị",
                "Đang giao",
                "Giao thành công"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật trạng thái");
        builder.setItems(cacTrangThaiDonHang, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int viTriDaChon) {
                String trangThaiMoi = cacTrangThaiDonHang[viTriDaChon];
                SQL.updateOrderStatus(order.getMaDonHang(), trangThaiMoi);
                order.setTrangThai(trangThaiMoi);
                refreshListView();
                Toast.makeText(admin_order.this, "Đã cập nhật trạng thái: " + trangThaiMoi, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void showDeleteOrderDialog(admin_items_order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa đơn hàng");
        builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQL.deleteOrder(order.getMaDonHang());
                refreshListView();
                Toast.makeText(admin_order.this, "Đã xóa đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void refreshListView() {
        list_order.clear();
        Cursor cur = DB.rawQuery("SELECT * FROM DonHang", null);
        if (cur.moveToFirst()) {
            do {
                int madon = cur.getInt(cur.getColumnIndexOrThrow("MaDonHang"));
                int manguoidung = cur.getInt(cur.getColumnIndexOrThrow("MaNguoiDung"));
                String ngaydatdon = cur.getString(cur.getColumnIndexOrThrow("NgayDatHang"));
                double tongtien = cur.getDouble(cur.getColumnIndexOrThrow("TongTien"));
                String trangthai = cur.getString(cur.getColumnIndexOrThrow("TrangThai"));
                String magiamgia = cur.getString(cur.getColumnIndexOrThrow("MaGiamGia"));
                String diachigiaohang = cur.getString(cur.getColumnIndexOrThrow("DiaChiGiaoHang"));
                String thongtinlienlac = cur.getString(cur.getColumnIndexOrThrow("ThongTinLienLac"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date ngaydathang = null;
                try {
                    ngaydathang = sdf.parse(ngaydatdon);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                list_order.add(new admin_items_order(madon, manguoidung, ngaydathang, tongtien, trangthai, magiamgia, diachigiaohang, thongtinlienlac));
            } while (cur.moveToNext());
        }
        cur.close();
        adapter.notifyDataSetChanged();
    }
}