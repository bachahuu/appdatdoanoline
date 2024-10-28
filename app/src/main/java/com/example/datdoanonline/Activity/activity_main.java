package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.R;
import com.example.datdoanonline.Food_Menu.main_menu;
import org.w3c.dom.Text;
public class activity_main extends AppCompatActivity {
    ImageView DONHANG_ADMIN,THUCDON_ADMIN,MGG_ADMIN,THONGKE_ADMIN, DANGXUAT;
    TextView sodon, somon , tongsomagg;
    SQLiteDatabase DB;
    DatabaseHelper SQL ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQL = new DatabaseHelper(this);
        DB = SQL.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DONHANG_ADMIN = findViewById(R.id.img_DonHang);
        THUCDON_ADMIN = findViewById(R.id.img_ThucDon);
        MGG_ADMIN = findViewById(R.id.img_MaGiamGia);
        THONGKE_ADMIN = findViewById(R.id.img_ThongKe);
        DANGXUAT = findViewById(R.id.img_dangxuat);
        sodon = findViewById(R.id.txt_TotalOrders);
        somon = findViewById(R.id.txt_TotalDishes);
        tongsomagg = findViewById(R.id.txt_TotalDiscounts);
        int tong_don = SQL.dem_don_hang();
        int tong_MGG = SQL.dem_MGG();
        int tong_mon = SQL.dem_so_mon();
        sodon.setText("Tổng đơn hàng:"+ String.valueOf(tong_don));
        tongsomagg.setText("Tổng Mã giảm giá:"+String.valueOf(tong_MGG));
        somon.setText("Tổng số món ăn :"+String.valueOf(tong_mon));
        DONHANG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(activity_main.this, admin_order.class);
                startActivity(it1);
            }
        });
        THUCDON_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent(activity_main.this, main_menu.class);
                startActivity(it2);
            }
        });
        MGG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(activity_main.this, AdminMaGiamGia_Acticity.class);
                startActivity(it3);
            }
        });
        THONGKE_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it4 = new Intent(activity_main.this, StatisticsActivity.class);
                startActivity(it4);
            }
        });
        DANGXUAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(activity_main.this, Login_Activity.class);
                startActivity(it5);
                finish(); // Đóng Activity hiện tại
            }
        });
    }
}
