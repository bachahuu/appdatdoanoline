package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.maGiamGiaDomain;
import com.example.datdoanonline.R;

public class AdminMGG_Insert_Admin extends AppCompatActivity {
    private EditText txtMaGiamGia, txtMoTa, txtPhanTramGiam, txtNgayBatDau, txtNgayKetThuc, txtSoLuongToiDa;
    private Button btn_Luu, btn_Thoat;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert_ma_giam_gia);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        txtMaGiamGia = findViewById(R.id.txt_ma_giam_gia);
        txtMoTa = findViewById(R.id.txt_mo_ta_mgg);
        txtPhanTramGiam = findViewById(R.id.txt_phan_tram_giam);
        txtNgayBatDau = findViewById(R.id.txt_ngay_bat_dau);
        txtNgayKetThuc = findViewById(R.id.txt_ngay_ket_thuc);
        txtSoLuongToiDa = findViewById(R.id.txt_so_luong_toi_da);
        btn_Luu = findViewById(R.id.btn_Luu_mgg);
        btn_Thoat = findViewById(R.id.btn_Thoat_mgg);

        btn_Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaGiamGia(); // Gọi phương thức thêm mã giảm giá
            }
        });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMGG_Insert_Admin.this, AdminMaGiamGia_Acticity.class);
                startActivity(intent);
            }
        });
    }

    private void addMaGiamGia() {
        String maGiamGia = txtMaGiamGia.getText().toString().trim();
        String moTa = txtMoTa.getText().toString().trim();
        double phanTramGiamGia = Double.parseDouble(txtPhanTramGiam.getText().toString().trim());
        String ngayBatDau = txtNgayBatDau.getText().toString().trim();
        String ngayKetThuc = txtNgayKetThuc.getText().toString().trim();
        int soLuongToiDa = Integer.parseInt(txtSoLuongToiDa.getText().toString().trim());

        maGiamGiaDomain maGiamGiaObj = new maGiamGiaDomain(maGiamGia, moTa, phanTramGiamGia, ngayBatDau, ngayKetThuc, soLuongToiDa, 0, "Hoạt động");

        // Thêm mã giảm giá vào database
        boolean isInserted = databaseHelper.addMaGiamGia(maGiamGiaObj);

        if (isInserted) {
            Toast.makeText(this, "Thêm mã giảm giá thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển về AdminMaGiamGia_Activity và reset lại adapter
            Intent intent = new Intent(AdminMGG_Insert_Admin.this, AdminMaGiamGia_Acticity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kết thúc Activity hiện tại
        } else {
            Toast.makeText(this, "Lỗi khi thêm mã giảm giá!", Toast.LENGTH_SHORT).show();
        }
    }
}