package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.maGiamGiaDomain;
import com.example.datdoanonline.R;

public class AdminMGG_Update_Admin extends AppCompatActivity {
    private EditText maGiamGiaEditText, moTaEditText, phanTramGiamGiaEditText, ngayBatDauEditText, ngayKetThucEditText, soLuongToiDaEditText, soLuongDaDungEditText;
    private Spinner trangThaiSpinner;
    private Button btnLuu, btnThoat;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_ma_giam_gia);

        // Ánh xạ các thành phần giao diện với id trong file XML
        maGiamGiaEditText = findViewById(R.id.txt_ma_giam_gia);
        moTaEditText = findViewById(R.id.txt_mo_ta_mgg);
        phanTramGiamGiaEditText = findViewById(R.id.txt_phan_tram_giam);
        ngayBatDauEditText = findViewById(R.id.txt_ngay_bat_dau);
        ngayKetThucEditText = findViewById(R.id.txt_ngay_ket_thuc);
        soLuongToiDaEditText = findViewById(R.id.txt_so_luong_toi_da);
        soLuongDaDungEditText = findViewById(R.id.txt_so_luong_da_dung);
        trangThaiSpinner = findViewById(R.id.spinner_trang_thai);
        btnLuu = findViewById(R.id.btn_Luu_mgg);
        btnThoat = findViewById(R.id.btn_Thoat_mgg);

        Intent intent = getIntent();
        String maGiamGia = intent.getStringExtra("maGiamGia");
        String moTa = intent.getStringExtra("moTa");
        double phanTramGiamGia = intent.getDoubleExtra("phanTramGiamGia", 0);
        String ngayBatDau = intent.getStringExtra("ngayBatDau");
        String ngayKetThuc = intent.getStringExtra("ngayKetThuc");
        int soLuongToiDa = intent.getIntExtra("soLuongToiDa", 0);
        int soLuongDaSuDung = intent.getIntExtra("soLuongDaSuDung", 0);
        String trangThai = intent.getStringExtra("trangThai");

        // Gán dữ liệu vào các EditText
        maGiamGiaEditText.setText(maGiamGia);
        moTaEditText.setText(moTa);
        phanTramGiamGiaEditText.setText(String.valueOf(phanTramGiamGia));
        ngayBatDauEditText.setText(ngayBatDau);
        ngayKetThucEditText.setText(ngayKetThuc);
        soLuongToiDaEditText.setText(String.valueOf(soLuongToiDa));
        soLuongDaDungEditText.setText(String.valueOf(soLuongDaSuDung));

        // Gán giá trị vào Spinner trạng thái
        if (trangThai != null) {
            switch (trangThai) {
                case "Hoạt động":
                    trangThaiSpinner.setSelection(0);
                    break;
                case "Hết":
                    trangThaiSpinner.setSelection(1);
                    break;
                case "Hủy":
                    trangThaiSpinner.setSelection(2);
                    break;
            }
        }

        databaseHelper = new DatabaseHelper(this);

        // Xử lý sự kiện nhấn nút Lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                String newMoTa = moTaEditText.getText().toString();
                double newPhanTramGiamGia = Double.parseDouble(phanTramGiamGiaEditText.getText().toString());
                String newNgayBatDau = ngayBatDauEditText.getText().toString();
                String newNgayKetThuc = ngayKetThucEditText.getText().toString();
                int newSoLuongToiDa = Integer.parseInt(soLuongToiDaEditText.getText().toString());
                int newSoLuongDaSuDung = Integer.parseInt(soLuongDaDungEditText.getText().toString());
                String newTrangThai = trangThaiSpinner.getSelectedItem().toString();

                // Tạo đối tượng maGiamGiaDomain với dữ liệu mới
                maGiamGiaDomain updatedMaGG = new maGiamGiaDomain(maGiamGia, newMoTa, newPhanTramGiamGia, newNgayBatDau, newNgayKetThuc, newSoLuongToiDa, newSoLuongDaSuDung, newTrangThai);

                // Cập nhật mã giảm giá trong cơ sở dữ liệu
                boolean isUpdated = databaseHelper.updateDiscountCode(updatedMaGG);

                if (isUpdated) {
                    Toast.makeText(AdminMGG_Update_Admin.this, "Cập nhật mã giảm giá thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển về AdminMaGiamGia_Activity và reset lại adapter
                    Intent intent = new Intent(AdminMGG_Update_Admin.this, AdminMaGiamGia_Acticity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Kết thúc Activity hiện tại
                } else {
                    Toast.makeText(AdminMGG_Update_Admin.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}