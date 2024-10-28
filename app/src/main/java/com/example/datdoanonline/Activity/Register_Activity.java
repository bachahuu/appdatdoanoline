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

public class Register_Activity extends AppCompatActivity {
    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private EditText nameEditText, phoneEditText, addressEditText;
    private Button registerButton, loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        // Ánh xạ các trường từ layout
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                // Kiểm tra nếu có trường nào bị bỏ trống
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(Register_Activity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem tên đăng nhập đã tồn tại hay chưa
                if (dbHelper.getUser(username) != null) {
                    Toast.makeText(Register_Activity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra định dạng email hợp lệ
                if (!isValidEmail(email)) {
                    Toast.makeText(Register_Activity.this, "Email không hợp lệ. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra điều kiện mật khẩu: ít nhất 1 số, 1 chữ cái in hoa, 1 chữ cái thường
                if (!isValidPassword(password)) {
                    Toast.makeText(Register_Activity.this, "Mật khẩu phải bao gồm ít nhất 1 chữ số, 1 chữ cái in hoa, và 1 chữ cái thường", Toast.LENGTH_LONG).show();
                    return;
                }

                // Kiểm tra mật khẩu có khớp không
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register_Activity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra số điện thoại: Đúng 10 chữ số
                if (!isValidPhone(phone)) {
                    Toast.makeText(Register_Activity.this, "Số điện thoại phải có 10 chữ số và chỉ chứa số", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Phân quyền mặc định là "User"
                String role = "User";

                // Tạo đối tượng UserDomain với thông tin mới
                UserDomain user = new UserDomain(0, username, email, password, name, phone, address, role);
                dbHelper.addUser(user);
                Toast.makeText(Register_Activity.this, "Đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    // Kiểm tra điều kiện mật khẩu bằng regex
    private boolean isValidPassword(String password) {
        // Mật khẩu phải có ít nhất 1 chữ số, 1 chữ cái thường, và 1 chữ cái in hoa
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
        return passwordPattern.matcher(password).matches();
    }

    // Kiểm tra số điện thoại (phải có đúng 10 chữ số)
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    // Kiểm tra điều kiện email bằng regex
    private boolean isValidEmail(String email){
        // Email phải chứa ký tự "@" và kết thúc với ".com"
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.com$");
        return emailPattern.matcher(email).matches();
    }
}
