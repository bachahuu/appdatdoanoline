package com.example.datdoanonline.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.datdoanonline.R;

public class thongbaothanhcong extends Activity {
    Button btn_lichsudonhang ;
    TextView madonhang;
    ImageView home ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xacnhan);
        madonhang = findViewById(R.id.order_number);

        home = findViewById(R.id.icon_home);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long maDonHang = sharedPreferences.getLong("MaDonHang", -1);// lấy mã đơn
        if (maDonHang != -1) {
            madonhang.setText("Mã đơn hàng:"+String.valueOf(maDonHang));
        } else {
            Toast.makeText(this, "không tìm thấy mã đơn hàng", Toast.LENGTH_SHORT).show();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(thongbaothanhcong.this,Trangchu_Activity.class);
                startActivity(it);
            }
        });

    }
}
