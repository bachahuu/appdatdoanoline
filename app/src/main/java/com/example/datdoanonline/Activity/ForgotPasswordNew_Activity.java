package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.R;

import java.util.regex.Pattern;

public class ForgotPasswordNew_Activity extends AppCompatActivity {
    private EditText newPasswordEditText, confirmNewPasswordEditText;
    private Button resetPasswordButton, loginButton;
    private DatabaseHelper dbHelper;
    private String username;  // Lấy từ intent của ForgotPasswordActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_new_password);

        dbHelper = new DatabaseHelper(this);

        // Lấy tên đăng nhập từ Intent (từ ForgotPasswordActivity)
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("USERNAME");
        }

        newPasswordEditText = findViewById(R.id.newPassword);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        loginButton = findViewById(R.id.loginButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

                if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordNew_Activity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem mật khẩu có khớp không
                if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(ForgotPasswordNew_Activity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra điều kiện mật khẩu: ít nhất 1 số, 1 chữ cái in hoa, 1 chữ cái thường
                if (!isValidPassword(newPassword)) {
                    Toast.makeText(ForgotPasswordNew_Activity.this, "Mật khẩu phải bao gồm ít nhất 1 chữ số, 1 chữ cái in hoa, và 1 chữ cái thường", Toast.LENGTH_LONG).show();
                    return;
                }

                // Cập nhật mật khẩu mới trong databasez
                dbHelper.updatePassword(username, newPassword);
                Toast.makeText(ForgotPasswordNew_Activity.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();

                // Chuyển đến trang đăng nhập
                Intent intent = new Intent(ForgotPasswordNew_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordNew_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    // Kiểm tra điều kiện mật khẩu bằng regex
    private boolean isValidPassword(String password) {
        // Mật khẩu phải có ít nhất 1 chữ số, 1 chữ cái thường và 1 chữ cái in hoa
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
        return passwordPattern.matcher(password).matches();
    }
}
