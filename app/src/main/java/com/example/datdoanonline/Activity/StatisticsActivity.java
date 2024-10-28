package com.example.datdoanonline.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.ThucDonDomain;
import com.example.datdoanonline.Food_Menu.main_menu;
import com.example.datdoanonline.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {
    private EditText editTextDateStart;
    private EditText editTextDateEnd;
    private Button buttonGetStatistics;
    private TextView textViewBestSellingItem;
    private TextView textViewTotalRevenue;
    private ImageView DANGXUAT;
    ImageView DONHANG_ADMIN, THUCDON_ADMIN, MGG_ADMIN;
    private DatabaseHelper databaseHelper;
    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        databaseHelper = new DatabaseHelper(this);
        editTextDateStart = findViewById(R.id.editTextDateStart);
        editTextDateEnd = findViewById(R.id.editTextDateEnd);
        buttonGetStatistics = findViewById(R.id.buttonGetStatistics);
        textViewBestSellingItem = findViewById(R.id.textViewBestSellingItem);
        textViewTotalRevenue = findViewById(R.id.textViewTotalRevenue);
        DANGXUAT = findViewById(R.id.img_dangxuat);
        DONHANG_ADMIN = findViewById(R.id.img_DonHang);
        THUCDON_ADMIN = findViewById(R.id.img_ThucDon);
        MGG_ADMIN = findViewById(R.id.img_MaGiamGia);

        // Lắng nghe sự kiện click vào EditText để hiển thị DatePickerDialog
        editTextDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextDateStart, calendarStart);
            }
        });

        editTextDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextDateEnd, calendarEnd);
            }
        });

        buttonGetStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatistics();
            }
        });

        DANGXUAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(StatisticsActivity.this, Login_Activity.class);
                startActivity(it5);
                finish(); // Đóng Activity hiện tại
            }
        });
        DONHANG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(StatisticsActivity.this, admin_order.class);
                startActivity(it1);
            }
        });
        THUCDON_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent(StatisticsActivity.this, main_menu.class);
                startActivity(it2);
            }
        });
        MGG_ADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(StatisticsActivity.this, AdminMaGiamGia_Acticity.class);
                startActivity(it3);
            }
        });
    }

    private void showDatePickerDialog(final EditText editText, final Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                StatisticsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(editText, calendar);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void updateLabel(EditText editText, Calendar calendar) {
        String format = "yyyy-MM-dd"; // Định dạng ngày
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    private void getStatistics() {
        String startDate = editTextDateStart.getText().toString();
        String endDate = editTextDateEnd.getText().toString();

        // Tính tổng doanh thu
        BigDecimal totalRevenue = databaseHelper.getTotalRevenue(startDate, endDate);
        textViewTotalRevenue.setText("Tổng doanh thu từ " + startDate + " đến " + endDate + ": " + totalRevenue.toString());
    }
}
