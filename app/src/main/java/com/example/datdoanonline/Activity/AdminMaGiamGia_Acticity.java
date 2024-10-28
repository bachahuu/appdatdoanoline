package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Adapter.MaGGListAdapter;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.maGiamGiaDomain;
import com.example.datdoanonline.Food_Menu.main_menu;
import com.example.datdoanonline.R;

import java.util.ArrayList;
import java.util.List;

public class AdminMaGiamGia_Acticity extends AppCompatActivity {
    ImageView DONHANG_ADMIN,THUCDON_ADMIN,THONGKE_ADMIN, DANGXUAT;
    Button btn_InsertMGG, btn_SearchMGG;
    EditText searchMGG;
    RecyclerView recyclerViewMaGG; // Khai báo RecyclerView
    MaGGListAdapter maGGListAdapter; // Khai báo adapter
    List<maGiamGiaDomain> maGGList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ma_giam_gia);
        DONHANG_ADMIN = findViewById(R.id.img_DonHang);
        THUCDON_ADMIN = findViewById(R.id.img_ThucDon);
        THONGKE_ADMIN = findViewById(R.id.img_ThongKe);
        DANGXUAT = findViewById(R.id.img_dangxuat);
        // Khởi tạo RecyclerView
        recyclerViewMaGG = findViewById(R.id.recyclerViewCoupons);
        recyclerViewMaGG.setLayoutManager(new LinearLayoutManager(this));

        btn_InsertMGG = findViewById(R.id.btn_AddCoupon);
        btn_SearchMGG = findViewById(R.id.btn_SearchCoupon);
        searchMGG = findViewById(R.id.searchCoupon);
        DONHANG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(AdminMaGiamGia_Acticity.this, admin_order.class);
                startActivity(it1);
            }
        });
        THUCDON_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent(AdminMaGiamGia_Acticity.this, main_menu.class);
                startActivity(it2);
            }
        });
        // Khởi tạo databaseHelper
        databaseHelper = new DatabaseHelper(this);

        btn_InsertMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMaGiamGia_Acticity.this, AdminMGG_Insert_Admin.class);
                startActivity(intent);
            }
        });
        THONGKE_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it4 = new Intent(AdminMaGiamGia_Acticity.this, StatisticsActivity.class);
                startActivity(it4);
            }
        });
        DANGXUAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(AdminMaGiamGia_Acticity.this, Login_Activity.class);
                startActivity(it5);
                finish(); // Đóng Activity hiện tại
            }
        });
        // Lấy danh sách mã giảm giá từ database
        List<maGiamGiaDomain> maGGList = getCouponsFromDatabase();

        // Khởi tạo adapter và gán vào RecyclerView
        maGGListAdapter = new MaGGListAdapter(this, maGGList);
        recyclerViewMaGG.setAdapter(maGGListAdapter);

        // Thêm sự kiện nhấn nút Tìm kiếm
        btn_SearchMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchMGG.getText().toString();
                List<maGiamGiaDomain> searchResults = databaseHelper.searchCouponsByName(keyword);

                // Cập nhật adapter với kết quả tìm kiếm
                maGGListAdapter.updateList(searchResults);
            }
        });
    }

    private List<maGiamGiaDomain> getCouponsFromDatabase() {
        // Thực hiện query từ database và trả về danh sách MaGiamGiaDomain
        // Đây chỉ là ví dụ, bạn cần thay thế bằng code tương tác với SQLite hoặc bất kỳ cơ sở dữ liệu nào bạn đang sử dụng
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getAllCoupons();
    }
}
