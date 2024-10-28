package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.UserDomain;
import com.example.datdoanonline.R;

import java.util.regex.Pattern;

public class ForgotPassword_Activity extends AppCompatActivity {
    private EditText usernameEditText, emailEditText;
    private Button submitButton, loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        submitButton = findViewById(R.id.submitButton);
        loginButton = findViewById(R.id.loginButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Kiểm tra định dạng email hợp lệ
                if (!isValidEmail(email)) {
                    Toast.makeText(ForgotPassword_Activity.this, "Email không hợp lệ. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDomain user = dbHelper.getUser(username);
                if (user != null && user.getEmail().equals(email)) {
                    Intent intent = new Intent(ForgotPassword_Activity.this, ForgotPasswordNew_Activity.class);
                    intent.putExtra("USERNAME", username);  // Truyền tên đăng nhập
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPassword_Activity.this, "Tên người dùng hoặc email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    // Kiểm tra điều kiện email bằng regex
    private boolean isValidEmail(String email) {
        // Email phải chứa ký tự "@" và kết thúc với ".com"
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.com$");
        return emailPattern.matcher(email).matches();
    }
}
