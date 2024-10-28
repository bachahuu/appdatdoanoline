package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.R;

public class ShowUserInfo_Activity extends AppCompatActivity {
    private TextView txtFullname, txtPhone, txtEmail, txtAddress, txtUsername, txtPassword;
    private Button btnLogout, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        // Ánh xạ các view
        txtFullname = findViewById(R.id.txt_hoTen);
        txtPhone = findViewById(R.id.txt_sdt);
        txtEmail = findViewById(R.id.txt_email);
        txtAddress = findViewById(R.id.txt_diaChi);
        txtUsername = findViewById(R.id.txt_tenDangNhap);
        txtPassword = findViewById(R.id.txt_matKhau);
        btnLogout = findViewById(R.id.btn_dangXuat);
        btnHome = findViewById(R.id.btn_home);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String fullName = preferences.getString("ho_ten", "Không có thông tin");
        String phone = preferences.getString("so_dien_thoai", "Không có thông tin");
        String email = preferences.getString("email", "Không có thông tin");
        String address = preferences.getString("dia_chi", "Không có thông tin");
        String username = preferences.getString("ten_dangnhap", "Không có thông tin");
        String password = preferences.getString("mat_khau", "Không có thông tin");

        // Hiển thị thông tin người dùng
        txtFullname.setText("Họ và tên: " + fullName);
        txtPhone.setText("Số điện thoại: " + phone);
        txtEmail.setText("Email: " + email);
        txtAddress.setText("Địa chỉ: " + address);

        // Hiển thị 4 ký tự đầu của tên tài khoản và mật khẩu, còn lại là '*'
        txtUsername.setText("Tên tài khoản: " + maskString(username, 2));
        txtPassword.setText("Mật khẩu: " + maskString(password, 2));

        // Xử lý sự kiện nút "Quay lại trang chủ"
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserInfo_Activity.this, Trangchu_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý sự kiện nút "Đăng xuất"
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa session hoặc dữ liệu người dùng
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear(); // Xóa dữ liệu session
                editor.apply();

                // Chuyển về trang login
                Intent intent = new Intent(ShowUserInfo_Activity.this, Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Đóng Activity hiện tại
            }
        });
    }

    // Hàm để ẩn bớt ký tự với dấu '*'
    private String maskString(String str, int visibleChars) {
        if (str.length() <= visibleChars) {
            return str; // Nếu chuỗi ngắn hơn số ký tự cần hiển thị
        }
        String visiblePart = str.substring(0, visibleChars);
        String maskedPart = new String(new char[str.length() - visibleChars]).replace("\0", "*");
        return visiblePart + maskedPart;
    }
}
