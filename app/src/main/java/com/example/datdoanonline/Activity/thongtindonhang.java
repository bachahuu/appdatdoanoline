package com.example.datdoanonline.Activity;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.R;

import java.util.ArrayList;
public class thongtindonhang extends AppCompatActivity {
    TextView soluong_items, tongtienhang,phigiaohang,TongTien,Chietkhau;
    EditText diachi,ho,ten,numberphone;
    Button dathang;
    float tongtatca = 0;
    ImageView cart,home;
    Spinner magiamgia;
    SQLiteDatabase db;
    DatabaseHelper sqlhelper;
    ArrayList<String> list_magiamgia;
    ArrayAdapter<String> adapter_magiamgia;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //gọi kết nối database
        sqlhelper = new DatabaseHelper(this);
        db = sqlhelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtindonhang);
        diachi = findViewById(R.id.diachi_giaohang);
        ho = findViewById(R.id.ho);
        ten = findViewById(R.id.ten);
        numberphone = findViewById(R.id.sdt_kh);
        dathang = findViewById(R.id.insert_don_hang);
        tongtienhang = findViewById(R.id.tongtienhang);
        phigiaohang = findViewById(R.id.phigiaohang);
        TongTien = findViewById(R.id.tongtien);
        cart = findViewById(R.id.cart_giohang);
        home = findViewById(R.id.trangchu_activity);
        magiamgia = findViewById(R.id.spinner_magiamgia);
        Chietkhau = findViewById(R.id.chietkhau);
        MaGiamGia();
        trangchu_activity();
        icon_cart();
        kiemtratrong();
        tiendonhang();

        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = numberphone.getText().toString().trim();
                // Kiểm tra số điện thoại: Đúng 10 chữ số
                if (!isValidPhone(phone)) {
                    Toast.makeText(thongtindonhang.this, "Số điện thoại phải có 10 chữ số ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kiemtratrong()){
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", null); // Lấy username

                    if (username != null) {
                        Toast.makeText(thongtindonhang.this,"tim thay user"+username,Toast.LENGTH_SHORT).show();
                    }
                    int Manguoidung = sqlhelper.getusername(username);

                    if (Manguoidung == 0){
                        Toast.makeText(thongtindonhang.this,"Người dùng "+username+"không tồn tại",Toast.LENGTH_SHORT).show();
                    }else {
                        luudonhang();
                    }
                    Intent intent = new Intent(thongtindonhang.this,thongbaothanhcong.class);
                   startActivity(intent);
                }else {
                    Toast.makeText(thongtindonhang.this,"Vui lòng điền đẩy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void luudonhang() {
        String diachigiaohhang = diachi.getText().toString();
        String hovaten = ho.getText().toString()+""+ten.getText().toString();
        String SDT = numberphone.getText().toString();
        String thongtinlienlac = hovaten +"-"+ SDT;
        String selected_Magiamgia = null; // Khởi tạo biến selected_Magiamgia với giá trị null
        if (magiamgia.getSelectedItem() != null) { // Kiểm tra xem có mục nào được chọn trong Spinner không
            String selectedItem = magiamgia.getSelectedItem().toString(); // Lấy mục được chọn dưới dạng chuỗi
            String[] parts = selectedItem.split("-"); // Tách chuỗi dựa trên dấu "-"
            selected_Magiamgia = parts[0]; // Lấy phần mã giảm giá (phần đầu tiên của chuỗi)
        }
        String trangThai = "Chờ xử lý";
        float tongTienDonHang = tongtatca;
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("username", "default_value"); // "default_value" là giá trị mặc định nếu không tìm thấy
        int Manguoidung = sqlhelper.getusername(Username);
        String sql_insert_donhang = " INSERT INTO DonHang(MaNguoiDung, NgayDatHang, TongTien, TrangThai, MaGiamGia, DiaChiGiaoHang, ThongTinLienLac)"+
"VALUES ("+Manguoidung +",datetime('now')," + tongTienDonHang  + ",'" + trangThai + "','" + selected_Magiamgia + "','" + diachigiaohhang + "','" + thongtinlienlac  +"')";
try {
    db.execSQL(sql_insert_donhang);
    // Lấy mã đơn hàng vừa thêm
    Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
    long maDonHang = -1;
    if (cursor.moveToFirst()) {
        maDonHang = cursor.getLong(0);
    }
    cursor.close();
    if (maDonHang != -1) {
        // Lưu mã đơn hàng vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("MaDonHang", maDonHang);
        editor.apply();
        // Cập nhật số lượng món ăn trong ThucDon
        Cursor cartCursor = db.rawQuery("SELECT MaMonAn, SoLuong FROM GioHang WHERE MaNguoiDung = ?", new String[]{String.valueOf(Manguoidung)});
        if (cartCursor.moveToFirst()) {
            do {
                int maMonAn = cartCursor.getInt(0);
                int soLuongTrongGio = cartCursor.getInt(1);

                Cursor thucDonCursor = db.rawQuery("SELECT SoLuong FROM ThucDon WHERE MaMonAn = ?", new String[]{String.valueOf(maMonAn)});
                if (thucDonCursor.moveToFirst()) {
                    int soLuongHienTai = thucDonCursor.getInt(0);
                    int soLuongMoi = Math.max(0, soLuongHienTai - soLuongTrongGio);
                    db.execSQL("UPDATE ThucDon SET SoLuong = ? WHERE MaMonAn = ?", new Object[]{soLuongMoi, maMonAn});
                }
                thucDonCursor.close();
            } while (cartCursor.moveToNext());
        }
        cartCursor.close();
// Sau khi lưu đơn hàng thành công, xóa giỏ hàng của khách hàng
        String sql_delete_giohang = "DELETE FROM GioHang WHERE MaNguoiDung = ?";
        try {
            db.execSQL(sql_delete_giohang, new Object[]{Manguoidung});
            Toast.makeText(thongtindonhang.this, "Đơn hàng đã được lưu thành công! Mã đơn hàng: " + maDonHang, Toast.LENGTH_SHORT).show();
            // Cập nhật lại số lượng hiển thị trên icon giỏ hàng
            soluong_items.setText("0");
        }catch (Exception e){
            Log.d(TAG, "Lỗi khi xóa giỏ hàng: " + e.getMessage());
            Toast.makeText(thongtindonhang.this, "Lỗi khi xóa giỏ hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    } else {
        Toast.makeText(thongtindonhang.this, "Lỗi khi lấy mã đơn hàng", Toast.LENGTH_LONG).show();
    }

}catch (Exception e ){
    Log.d(TAG, "luudonhang: "+e.getMessage());
    Toast.makeText(thongtindonhang.this, "Lỗi :"+e.getMessage(), Toast.LENGTH_LONG).show();
}
    }

    private void MaGiamGia() {
        list_magiamgia = new ArrayList<>();
        Cursor cur = db.rawQuery(
                "SELECT MGG.MaGiamGia , MGG.TrangThai " +
                        "FROM MaGiamGia MGG " +
                        "WHERE MGG.TrangThai == 'Hoạt động'",
                null);
        if (cur.moveToFirst()){
            do {
                String magiamgia = cur.getString(0);
                String trangthai = cur.getString(1);
                list_magiamgia.add(magiamgia+"-"+trangthai);
            }while (cur.moveToNext());
        }
        cur.close();
        adapter_magiamgia = new ArrayAdapter<>(thongtindonhang.this, android.R.layout.simple_spinner_item,list_magiamgia);
        adapter_magiamgia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// tao thanh cuon
        magiamgia.setAdapter(adapter_magiamgia);
        magiamgia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Lấy MaGiamGia được chọn từ Spinner
                String selectedMagiamgia = list_magiamgia.get(i).split("-")[0]; // Lấy phần MaGiamGia

                Cursor cursor = db.rawQuery(
                        "SELECT MGG.PhanTramGiamGia " +
                                "FROM MaGiamGia MGG " +
                                "WHERE MGG.MaGiamGia == ?",
                        new String[]{selectedMagiamgia});

                if (cursor.moveToFirst()) {
                    String phantram = cursor.getString(0);
                    Chietkhau.setText(phantram + "%");
                    tiendonhang();// Hiển thị phần trăm giảm giá
                }
                cursor.close(); // Đóng Cursor sau khi sử dụng
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void trangchu_activity() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongtindonhang.this,Trangchu_Activity.class);
                startActivity(intent);
            }
        });
    }
    private void tiendonhang() {
            Intent it = getIntent();
        float tongtiendonhang = it.getFloatExtra("tongtien",0);
        tongtienhang.setText(String.valueOf(tongtiendonhang) + " VND");
        String tiengiaohang = phigiaohang.getText().toString();
        tiengiaohang = tiengiaohang.replace("VND","").trim();//loại bỏ ký tự không cần thiết
        String discount = Chietkhau.getText().toString();
        discount = discount.replace("%","").trim();
        // Chuyển đổi chiết khấu và tính toán
        float tien_chiet_khau = 0;
        if (!discount.isEmpty()) {
            try {
                tien_chiet_khau = (Float.parseFloat(discount) * tongtiendonhang) / 100;
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Xử lý trường hợp không thể chuyển đổi
            }
        }
        // Chuyển đổi phí giao hàng
        float tienship = 0;
        if (!tiengiaohang.isEmpty()) {
            try {
                tienship = Float.parseFloat(tiengiaohang);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Xử lý trường hợp không thể chuyển đổi
            }
        }
        tongtatca = tongtiendonhang - tien_chiet_khau + tienship;
        TongTien.setText(tongtatca+" VND");
    }
    private boolean kiemtratrong() {
        if (diachi.getText().toString().isEmpty() || ho.getText().toString().isEmpty() ||
                ten.getText().toString().isEmpty() || numberphone.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    // Kiểm tra số điện thoại (phải có đúng 10 chữ số)
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
    private void icon_cart() {
        soluong_items = findViewById(R.id.menu_sl);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null); // Lấy username
        int Manguoidung = sqlhelper.getusername(username);
        // Lấy số lượng mặt hàng từ intent
        int cart_sl = sqlhelper.dem_mat_hang(Manguoidung);
        soluong_items.setText(String.valueOf(cart_sl));
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(thongtindonhang.this, shopping_cart.class);
                startActivity(it);
            }
        });
    }
}
