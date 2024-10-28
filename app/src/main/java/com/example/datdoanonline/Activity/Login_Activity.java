//package com.example.datdoanonline.Activity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.datdoanonline.Domain.DatabaseHelper;
//import com.example.datdoanonline.R;
//
//public class Login_Activity extends AppCompatActivity {
//    private EditText usernameEditText, passwordEditText;
//    private Button loginButton, forgotPasswordButton, signupButton;
//    private DatabaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        dbHelper = new DatabaseHelper(this);
//
//        usernameEditText = findViewById(R.id.username);
//        passwordEditText = findViewById(R.id.password);
//        loginButton = findViewById(R.id.loginButton);
//        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
//        signupButton = findViewById(R.id.signupButton);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = usernameEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//                if (dbHelper.checkUser(username, password)) {
//                    // Lấy quyền của người dùng
//                    String userRole = dbHelper.getUserRole(username, password);
//                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username", username); // Lưu username
//                    editor.apply(); // Lưu dữ liệu không đồng bộ (không cần chờ)
//                    if (userRole != null) {
//                        if (userRole.equals("Admin")) {
//                            // Nếu là admin, chuyển sang AdminActivity
//                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công với quyền Admin", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Login_Activity.this, activity_main.class);
//                            startActivity(intent);
//                        } else if (userRole.equals("User")) {
//                            // Nếu là user, chuyển sang UserActivity
//                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công với quyền User", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Login_Activity.this, Trangchu_Activity.class);
//                            startActivity(intent);
//                        }
//
//                        // Kết thúc Login_Activity để không quay lại
//                        finish();
//                    } else {
//                        Toast.makeText(Login_Activity.this, "Lỗi khi lấy phân quyền", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(Login_Activity.this, "Tên người dùng hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        signupButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Login_Activity.this, ForgotPassword_Activity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}

package com.example.datdoanonline.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.UserDomain;
import com.example.datdoanonline.R;

public class    Login_Activity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, forgotPasswordButton, signupButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        signupButton = findViewById(R.id.signupButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Lưu username vào SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.apply();
                if (dbHelper.checkUser(username, password)) {
                    // Lấy thông tin người dùng từ database
                    UserDomain user = dbHelper.getUserByUsername(username);

                    if (user != null) {
                        // Lưu thông tin người dùng vào SharedPreferences
                        SharedPreferences sharedPreference = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
                        SharedPreferences.Editor edito = sharedPreference.edit();
                        edito.putString("ho_ten", user.getFullName());
                        edito.putString("so_dien_thoai", user.getPhone());
                        edito.putString("email", user.getEmail());
                        edito.putString("dia_chi", user.getAddress());
                        edito.putString("ten_dangnhap", user.getUsername());
                        edito.putString("mat_khau", user.getPassword());
                        edito.apply(); // Lưu dữ liệu không đồng bộ

                        // Lấy phân quyền của người dùng
                        String userRole = user.getRole();

                        if (userRole.equals("Admin")) {
                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công với quyền Admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login_Activity.this, activity_main.class);
                            startActivity(intent);
                        } else if (userRole.equals("User")) {
                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công với quyền User", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login_Activity.this, Trangchu_Activity.class);
                            startActivity(intent);
                        }

                        // Kết thúc Login_Activity để không quay lại
                        finish();
                    } else {
                        Toast.makeText(Login_Activity.this, "Lỗi khi lấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login_Activity.this, "Tên người dùng hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, ForgotPassword_Activity.class);
                startActivity(intent);
            }
        });
    }
}
