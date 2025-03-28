package com.example.datdoanonline.Food_Menu;

import androidx.annotation.NonNull;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.Menu_Food_Domain;
import com.example.datdoanonline.R;

public class Add_Food_admin extends Activity {
    EditText txt_name, txt_mota, txt_gia, txt_danh_muc, txt_nang_luong, txt_tgian_lam, txt_sl;
    Button btn_Luu, btn_thoat, btn_anh;
    ImageView img_preview;
    DatabaseHelper databaseHelper;
    Uri imageUri; // Lưu URI của ảnh đã chọn

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_food);

        // Khởi tạo các view
        txt_name = findViewById(R.id.txt_name);
        txt_mota = findViewById(R.id.txt_mo_ta);
        txt_gia = findViewById(R.id.txt_gia);
        txt_danh_muc = findViewById(R.id.txt_danh_muc);
        txt_nang_luong = findViewById(R.id.txt_nang_luong);
        txt_tgian_lam = findViewById(R.id.txt_tgian_lam);
        txt_sl = findViewById(R.id.txt_sl);

        btn_Luu = findViewById(R.id.btn_Luu);
        btn_anh = findViewById(R.id.btn_anh);
        img_preview = findViewById(R.id.img_preview); // ImageView để hiển thị ảnh

        databaseHelper = new DatabaseHelper(this);
        // Kiểm tra và yêu cầu quyền truy cập bộ nhớ
        checkPermission();

        // Sự kiện chọn ảnh
        btn_anh.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); // Chỉ cho phép chọn ảnh
            startActivityForResult(intent, 100); // 100 là mã request code
        });

        // Lưu thông tin món ăn
        btn_Luu.setOnClickListener(view -> add_food());
    }

    // Phương thức thêm món ăn vào database
    private void add_food() {
        String tenmonan = txt_name.getText().toString().trim();
        String mota = txt_mota.getText().toString().trim();
        double gia = Double.parseDouble(txt_gia.getText().toString().trim());
        String danhmuc = txt_danh_muc.getText().toString().trim();
        String nangluongStr = txt_nang_luong.getText().toString().trim();
        int nangluong = Integer.parseInt(nangluongStr);
        int time = Integer.parseInt(txt_tgian_lam.getText().toString().trim());
        int sl = Integer.parseInt(txt_sl.getText().toString().trim());

        // Sử dụng URI của ảnh đã chọn nếu có
        String imagePath = (imageUri != null) ? imageUri.toString() : "default_image"; // Đường dẫn ảnh

        // Tạo đối tượng Menu_Food_Domain
        Menu_Food_Domain Food_Obj = new Menu_Food_Domain(0, danhmuc, time, sl, mota, imagePath, gia, nangluong, tenmonan);

        boolean isInserted = databaseHelper.add_Food(Food_Obj);

        if (isInserted) {
            Toast.makeText(this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển đến màn hình main_menu
            Intent intent = new Intent(Add_Food_admin.this, main_menu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Đóng Activity hiện tại
        } else {
            Toast.makeText(this, "Lỗi khi thêm món ăn", Toast.LENGTH_SHORT).show();
        }
    }

    // Xử lý kết quả sau khi chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy URI của ảnh được chọn
            imageUri = data.getData();

            // Hiển thị ảnh trong ImageView
            img_preview.setImageURI(imageUri);
        }
    }

    // Yêu cầu quyền truy cập bộ nhớ
    private void checkPermission() {
        // Kiểm tra quyền đọc bộ nhớ cho Android 9 (API 28) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Yêu cầu quyền READ_EXTERNAL_STORAGE thay vì READ_MEDIA_IMAGES
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // Kiểm tra kết quả cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền truy cập bộ nhớ!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
