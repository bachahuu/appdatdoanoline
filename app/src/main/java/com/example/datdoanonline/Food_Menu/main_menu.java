package com.example.datdoanonline.Food_Menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Activity.AdminMaGiamGia_Acticity;
import com.example.datdoanonline.Activity.Login_Activity;
import com.example.datdoanonline.Activity.StatisticsActivity;
import com.example.datdoanonline.Activity.activity_main;
import com.example.datdoanonline.Activity.admin_order;
import com.example.datdoanonline.Adapter.Menu_Food_Adapter;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.Menu_Food_Domain;
import com.example.datdoanonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class main_menu extends Activity {
    Button Add_food, Search_food;
    EditText search_food;
    RecyclerView recyclerView_food;
    List<Menu_Food_Domain> Foodlist;
    Menu_Food_Adapter foodAdapter;
    DatabaseHelper databaseHelper;
    ImageView DONHANG_ADMIN,MGG_ADMIN,THONGKE_ADMIN, DANGXUAT;
    private ImageView imgMonImageView;
    private Uri selectedImageUri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        recyclerView_food = findViewById(R.id.recyclerViewfood);
        recyclerView_food.setLayoutManager(new LinearLayoutManager(this));

        Add_food = findViewById(R.id.btn_Addfood);
        search_food = findViewById(R.id.txt_search_food);
        DONHANG_ADMIN = findViewById(R.id.img_DonHang);
        MGG_ADMIN = findViewById(R.id.img_MaGiamGia);
        THONGKE_ADMIN = findViewById(R.id.img_ThongKe);
        DANGXUAT = findViewById(R.id.img_dangxuat);
        DONHANG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(main_menu.this, admin_order.class);
                startActivity(it1);
            }
        });

        MGG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(main_menu.this, AdminMaGiamGia_Acticity.class);
                startActivity(it3);
            }
        });
        THONGKE_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it4 = new Intent(main_menu.this, StatisticsActivity.class);
                startActivity(it4);
            }
        });
        DANGXUAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(main_menu.this, Login_Activity.class);
                startActivity(it5);
                finish(); // Đóng Activity hiện tại
            }
        });
        databaseHelper = new DatabaseHelper(this);
        Add_food.setOnClickListener(view -> {
            Intent t = new Intent(main_menu.this, Add_Food_admin.class);
            startActivity(t);
        });

        List<Menu_Food_Domain> FoodList = getFoodFromDatabase();
        // Khởi tạo adapter và gán vào RecyclerView
        foodAdapter = new Menu_Food_Adapter(this, FoodList, new Menu_Food_Adapter.OnItemClickListener() {
            @Override
            public void onEdit(Menu_Food_Domain menu_food) {
                update_Food_Dialog(menu_food, menu_food.getId());
            }

            @Override
            public void onDelete(Menu_Food_Domain menu_food) {
                delete_Food(menu_food);  // Xử lý chức năng xóa
            }
        });
        recyclerView_food.setAdapter(foodAdapter);

        search_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim();
                if (!TextUtils.isEmpty(searchText)) {
                    List<Menu_Food_Domain> searchResults = searchFoodByFirstLetter(searchText);
                    if (searchResults.isEmpty()) {
                        Toast.makeText(main_menu.this, "Không tìm thấy món ăn nào.", Toast.LENGTH_SHORT).show();
                        // Hiển thị lại danh sách món ăn ban đầu
                        foodAdapter.updateFoodList(FoodList);
                    } else {
                        foodAdapter.updateFoodList(searchResults); // Cập nhật danh sách theo kết quả tìm kiếm
                    }
                } else {
                    // Khi thanh tìm kiếm trống, hiển thị lại toàn bộ danh sách món ăn ban đầu
                    foodAdapter.updateFoodList(FoodList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý sau khi văn bản thay đổi
            }
        });

    }

    private List<Menu_Food_Domain> searchFoodByFirstLetter(String query) {
        List<Menu_Food_Domain> allFoods = getFoodFromDatabase();  // Lấy danh sách tất cả món ăn
        List<Menu_Food_Domain> result = new ArrayList<>();

        // Lọc danh sách theo ký tự đầu tiên của tên món ăn
        for (Menu_Food_Domain food : allFoods) {
            if (food.getTenmonan().toLowerCase().startsWith(query.toLowerCase())) {
                result.add(food);
            }
        }
        return result;
    }

    private List<Menu_Food_Domain> getFoodFromDatabase() {
        return databaseHelper.get_Food();
    }

    private void update_Food_Dialog(Menu_Food_Domain menu_food, Integer maMonan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa Thực đơn");

        // Inflate layout của dialog và khởi tạo view
        View view = LayoutInflater.from(this).inflate(R.layout.activity_admin_update_food, null);
        EditText tenMonEditText = view.findViewById(R.id.txt_name);
        EditText moTaEditText = view.findViewById(R.id.txt_mo_ta);
        EditText giaEditText = view.findViewById(R.id.txt_gia);
        EditText danhMucEditText = view.findViewById(R.id.txt_danh_muc);
        EditText nangLuongEditText = view.findViewById(R.id.txt_nang_luong);
        EditText thoiGianEditText = view.findViewById(R.id.txt_tgian_lam);
        EditText soLuongEditText = view.findViewById(R.id.txt_sl);
        imgMonImageView = view.findViewById(R.id.img_preview);

        // Set thông tin món ăn vào các EditText
        tenMonEditText.setText(menu_food.getTenmonan());
        moTaEditText.setText(menu_food.getMota());
        giaEditText.setText(String.valueOf(menu_food.getGia()));
        danhMucEditText.setText(menu_food.getDanhmuc());
        nangLuongEditText.setText(String.valueOf(menu_food.getNangluong()));
        thoiGianEditText.setText(String.valueOf(menu_food.getTime()));
        soLuongEditText.setText(String.valueOf(menu_food.getSl()));

        // Hiển thị hình ảnh món ăn (sử dụng Picasso)
        Picasso.get().load(menu_food.getImg()).into(imgMonImageView);

        imgMonImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100);  // Request code 100 để chọn ảnh
        });
        builder.setView(view);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String TenMon = tenMonEditText.getText().toString().trim();
            String MoTa = moTaEditText.getText().toString().trim();
            double Gia = Double.parseDouble(giaEditText.getText().toString().trim());
            String DanhMuc = danhMucEditText.getText().toString().trim();
            int NangLuong = Integer.parseInt(nangLuongEditText.getText().toString().trim());
            int ThoiGian = Integer.parseInt(thoiGianEditText.getText().toString().trim());
            int SoLuong = Integer.parseInt(soLuongEditText.getText().toString().trim());

            if (!TextUtils.isEmpty(TenMon) && !TextUtils.isEmpty(MoTa) && Gia > 0 && !TextUtils.isEmpty(DanhMuc)
                    && NangLuong > 0 && ThoiGian > 0 && SoLuong > 0) {
                // Cập nhật món ăn trong database
                menu_food.setTenmonan(TenMon);
                menu_food.setMota(MoTa);
                menu_food.setGia(Gia);
                menu_food.setDanhmuc(DanhMuc);
                menu_food.setNangluong(NangLuong);
                menu_food.setTime(ThoiGian);
                menu_food.setSl(SoLuong);
                if (selectedImageUri != null) {
                    menu_food.setImg(selectedImageUri.toString());
                }
                databaseHelper.update_Food(menu_food);

                Toast.makeText(this, "Món ăn đã được cập nhật!", Toast.LENGTH_SHORT).show();

                // Tải lại danh sách món ăn từ database
                Foodlist = getFoodFromDatabase(); // Lấy lại danh sách mới
                foodAdapter.updateFoodList(Foodlist); // Cập nhật adapter với danh sách mới
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // Lưu URI ảnh được chọn
            imgMonImageView.setImageURI(selectedImageUri); // Hiển thị ảnh mới trên ImageView
        }
    }

    private void delete_Food(Menu_Food_Domain menu_food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(main_menu.this);
        builder.setTitle("Xóa món ăn");
        builder.setMessage("Bạn có chắc chắn muốn xóa món ăn này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            databaseHelper.delete_Food(menu_food.getId());

            // Tải lại danh sách món ăn từ database
            Foodlist = getFoodFromDatabase(); // Lấy lại danh sách mới
            foodAdapter.updateFoodList(Foodlist); // Cập nhật adapter với danh sách mới

            Toast.makeText(main_menu.this, "Món ăn đã được xóa.", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void load_Food(Integer maMonAn) {
        Foodlist = databaseHelper.get_Food_ByProductId(maMonAn);
        foodAdapter = new Menu_Food_Adapter(this, Foodlist, new Menu_Food_Adapter.OnItemClickListener() {
            @Override
            public void onEdit(Menu_Food_Domain menu_food) {
                update_Food_Dialog(menu_food, maMonAn);
            }

            @Override
            public void onDelete(Menu_Food_Domain menu_food) {
                delete_Food(menu_food);  // Thêm chức năng xóa
            }
        });

        recyclerView_food.setAdapter(foodAdapter);
    }
}
