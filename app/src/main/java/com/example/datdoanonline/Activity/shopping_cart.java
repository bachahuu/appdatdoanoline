package com.example.datdoanonline.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datdoanonline.Adapter.adapter_giohang;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.items_giohang;
import com.example.datdoanonline.R;

import java.util.ArrayList;

public class shopping_cart extends AppCompatActivity {
TextView giohangtrong,tongtien;
Toolbar toolbar;
ListView listView;
Button thanhtoan , menu;
ArrayList<items_giohang> danhsach_items;
adapter_giohang adap ;
SQLiteDatabase db;
DatabaseHelper sqlhelper;
int cart_sl =0 ;
float tongTien = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //gọi kết nối database
        sqlhelper = new DatabaseHelper(this);
        db = sqlhelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        activity_giohang();
        activity_muasam();
//        capnhatsoluongmathang();
        capNhatTongTien();
        btn_thanhtoan();
    }
    public int getManguoidung() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("username", "default_value"); // "default_value" là giá trị mặc định nếu không tìm thấy
        return sqlhelper.getusername(Username); // Lấy mã người dùng từ DatabaseHelper
    }
    public  int getmagiohang(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int manguoidung = sqlhelper.getusername(username);
        return sqlhelper.getmagiohang(manguoidung);
    }
//    public int capnhatsoluongmathang() {
//        for (items_giohang items : danhsach_items) {
//            cart_sl ++;  // Cộng dồn số lượng các mặt hàng trong giỏ
//        }
//        return  cart_sl;
//    }
    public void capNhatTongTien() {
         tongTien = 0;
        for (items_giohang item : danhsach_items) {
            tongTien += item.getGiaSanPham() * item.getSoLuong();  // Tính tiền cho mỗi sản phẩm
        }
        tongtien.setText(tongTien + " VND");
    }
    private void btn_thanhtoan() {
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (danhsach_items.isEmpty()) {
                    // Hiển thị thông báo nếu giỏ hàng trống
                    Toast.makeText(shopping_cart.this, "Giỏ hàng hiện đang trống. Vui lòng thêm sản phẩm trước khi thanh toán.", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang trang thongtindonhang nếu giỏ hàng không trống
                    Intent it = new Intent(shopping_cart.this, thongtindonhang.class);
                    it.putExtra("tongtien", tongTien);
                    startActivity(it);
                }
            }
        });
    }
    private void activity_giohang() {
        setContentView(R.layout.shopping_cart);
        tongtien = findViewById(R.id.tongtien);
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        toolbar = findViewById(R.id.toolbar);
       listView = findViewById(R.id.listview_items);
        thanhtoan = findViewById(R.id.btn_thanhtoan);
        menu = findViewById(R.id.btn_mua);
        danhsach_items = new ArrayList<>();
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        String username = sharedPreferences.getString("username", null); // Lấy username
//        int Manguoidung = sqlhelper.getusername(username);
        int Manguoidung =getManguoidung();
//       danhsach_items.add(new items_giohang("gà rán",R.drawable.gatay,70000,2));
//      danhsach_items.add(new items_giohang("hamburger",R.drawable.hamburger,100000,2));
//       danhsach_items.add(new items_giohang("gà rán",R.drawable.gatay,35000,1));
        // Truy vấn kết hợp TABLE_GIO_HANG và TABLE_THUC_DON để lấy chi tiết sản phẩm
        Cursor cur = db.rawQuery(
                "SELECT gh.MaMonAn, td.TenMonAn, td.AnhMinhHoa, td.Gia, gh.SoLuong , gh.MaNguoiDung " +
                        "FROM GioHang gh " +
                        "INNER JOIN ThucDon td ON gh.MaMonAn = td.MaMonAn"+
                        " INNER JOIN NguoiDung ON NguoiDung.MaNguoiDung = gh.MaNguoiDung"+
                " WHERE NguoiDung.MaNguoiDung == ?  ",
                new String[]{String.valueOf(Manguoidung)});  // Truyền giá trị username vào đây)
        if(cur.moveToFirst()){
            SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            do {
                int mamonan = cur.getInt(0);                 // Mã món ăn
                String tenSanPham = cur.getString(1);        // Tên món ăn
                String hinhAnhSanPham = cur.getString(2);          // Hình ảnh minh họa
                int giaSanPham = cur.getInt(3);              // Giá sản phẩm
                int soLuong = cur.getInt(4);                 // Số lượng trong giỏ hàng
                danhsach_items.add(new items_giohang(tenSanPham,hinhAnhSanPham,giaSanPham,soLuong,mamonan));
                //Lưu 'mamonan' và 'soLuong' vào SharedPreferences
//                editor.putInt("mamonan_" + mamonan, soLuong);
                // Lưu tên món ăn vào SharedPreferences
                editor.putString("tenSanPham_" + tenSanPham, tenSanPham);
            }while (cur.moveToNext());
            editor.apply();//Áp dụng các thay đổi cho SharedPreferences
//            capnhatsoluongmathang();
        }
        cur.close();
        adap = new adapter_giohang(this,R.layout.items_cart,danhsach_items,this);
        listView.setAdapter(adap);
        // Kiểm tra xem giỏ hàng có trống không
        if (danhsach_items.isEmpty()) {
            // Nếu giỏ hàng trống, hiện TextView "Giỏ hàng trống" và ẩn ListView
            giohangtrong.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            // Nếu giỏ hàng không trống, hiện ListView và ẩn TextView "Giỏ hàng trống"
            giohangtrong.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                items_giohang sanpham = danhsach_items.get(i);

            }
        });
    }
    private void activity_muasam() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shopping_cart.this,Trangchu_Activity.class);
                startActivity(intent);
            }
        });
    }
}
