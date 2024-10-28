package com.example.datdoanonline.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Adapter.CommentAdapter;
import com.example.datdoanonline.Domain.DanhGiaDomain;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chitietsp extends AppCompatActivity {
    TextView tensp, giasp, motasp, soluong;
    Button btn_them, btn_cong, btn_tru, btn_send_comment;
    EditText et_comment;
    ImageView img_hinhanh;
    RecyclerView rv_comments;
    SQLiteDatabase db;
    DatabaseHelper sqlhelper;
    private int SOLUONG = 1;
    private double foodPrice;
    private int maxSoLuong;
    List<DanhGiaDomain> commentsList;
    CommentAdapter commentAdapter;
    int maMonAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sqlhelper = new DatabaseHelper(this);
        db = sqlhelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        initview();

        Intent intent = getIntent();
        String foodName = intent.getStringExtra("foodName");
        foodPrice = intent.getDoubleExtra("foodPrice", 0);
        String foodDescription = intent.getStringExtra("foodDescription");
        String foodImage = intent.getStringExtra("foodImage");
        maMonAn = intent.getIntExtra("maMonAn", 0);

        tensp.setText(foodName);
        update_tien();
        giasp.setText("Giá: " + String.valueOf(foodPrice) + " VND");
        motasp.setText(foodDescription);

        Picasso.get().load(foodImage).into(img_hinhanh);

        btn_cong.setOnClickListener(view -> {
            int availableQty = sqlhelper.getAvailableQuantity(maMonAn);
            if (SOLUONG < availableQty) {
                SOLUONG++;
                update_tien();
            } else {
                Toast.makeText(chitietsp.this,
                        "Không thể thêm. Chỉ còn " + availableQty + " sản phẩm trong kho",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btn_tru.setOnClickListener(view -> {
            if (SOLUONG > 1) {
                SOLUONG--;
                update_tien();
            } else {
                Toast.makeText(chitietsp.this, "Số lượng tối đa là 1", Toast.LENGTH_SHORT).show();
            }
        });

        btn_them.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            int Manguoidung = sqlhelper.getusername(username);
            if (Manguoidung == 0) {
                Toast.makeText(chitietsp.this, "Người dùng " + username + " không tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                insert_giohang(Manguoidung, maMonAn);
            }
        });

        loadComments(maMonAn);

        btn_send_comment.setOnClickListener(v -> {
            String commentText = et_comment.getText().toString().trim();
            if (!TextUtils.isEmpty(commentText)) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", null);
                int Manguoidung = sqlhelper.getusername(username);

                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                DanhGiaDomain newComment = new DanhGiaDomain(0, Manguoidung, maMonAn, 5, commentText, currentDate);
                sqlhelper.addComment(newComment);

                Toast.makeText(chitietsp.this, "Bình luận đã được thêm!", Toast.LENGTH_SHORT).show();
                loadComments(maMonAn);
                et_comment.setText("");
            } else {
                Toast.makeText(chitietsp.this, "Vui lòng nhập nội dung bình luận.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditCommentDialog(DanhGiaDomain comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa bình luận");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_comment, null);
        EditText etEditComment = view.findViewById(R.id.et_edit_comment);
        etEditComment.setText(comment.getNhanXet());

        builder.setView(view);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String updatedCommentText = etEditComment.getText().toString().trim();
            if (!TextUtils.isEmpty(updatedCommentText)) {
                comment.setNhanXet(updatedCommentText);
                sqlhelper.updateComment(comment);
                Toast.makeText(chitietsp.this, "Đã cập nhật bình luận!", Toast.LENGTH_SHORT).show();
                loadComments(maMonAn);
            } else {
                Toast.makeText(chitietsp.this, "Nội dung bình luận không được để trống.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void loadComments(Integer maMonAn) {
        commentsList = sqlhelper.getCommentsByProductId(maMonAn);
        commentAdapter = new CommentAdapter(this, commentsList, new CommentAdapter.OnItemClickListener() {
            @Override
            public void onEdit(DanhGiaDomain comment) {
                if (isUserCommentOwner(comment)) {
                    showEditCommentDialog(comment);
                } else {
                    Toast.makeText(chitietsp.this, "Bạn không có quyền chỉnh sửa bình luận này!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDelete(DanhGiaDomain comment) {
                if (isUserCommentOwner(comment)) {
                    sqlhelper.deleteComment(comment.getMaDanhGia());
                    Toast.makeText(chitietsp.this, "Bình luận đã được xóa!", Toast.LENGTH_SHORT).show();
                    loadComments(maMonAn);
                } else {
                    Toast.makeText(chitietsp.this, "Bạn không có quyền xóa bình luận này!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rv_comments.setLayoutManager(new LinearLayoutManager(this));
        rv_comments.setAdapter(commentAdapter);
    }

    private boolean isUserCommentOwner(DanhGiaDomain comment) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("username", "default_value"); // "default_value" là giá trị mặc định nếu không tìm thấy
        int currentUserId = sqlhelper.getusername(Username);
        return comment.getMaNguoiDung() == currentUserId;
    }

    private void update_tien() {
        soluong.setText(String.valueOf(SOLUONG));
        double totalPrice = foodPrice * SOLUONG;
        giasp.setText("Giá: " + String.format("%.0f", totalPrice) + " VND");
    }

    private void insert_giohang(int Manguoidung, int MaMonAn) {
        int Soluongsanpham = Integer.parseInt(soluong.getText().toString());

            // Kiểm tra món này đã có trong giỏ hàng chưa
            if (sqlhelper.kiemTraMonAnTrongGioHang(MaMonAn, Manguoidung)) {
                // Nếu có rồi thì cộng dồn số lượng
                db.execSQL("UPDATE GioHang SET SoLuong = SoLuong + " + Soluongsanpham +
                        " WHERE MaMonAn = " + MaMonAn + " AND MaNguoiDung = " + Manguoidung);
                Toast.makeText(chitietsp.this, "Đã cập nhật số lượng trong giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu chưa có thì thêm mới
                db.execSQL("INSERT INTO GioHang(MaMonAn, MaNguoiDung, SoLuong) " +
                        "VALUES (" + MaMonAn + ", " + Manguoidung + ", " + Soluongsanpham + ")");
                Toast.makeText(chitietsp.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
    }


    private void initview() {
        tensp = findViewById(R.id.txt_tensanpham);
        giasp = findViewById(R.id.txt_giatien);
        motasp = findViewById(R.id.txt_mota_sp);
        btn_them = findViewById(R.id.btn_themvaogiohang);
        btn_tru = findViewById(R.id.btn_minus);
        soluong = findViewById(R.id.txt_quantity);
        btn_cong = findViewById(R.id.btn_plus);
        img_hinhanh = findViewById(R.id.imgchitiet);
        et_comment = findViewById(R.id.et_comment);
        btn_send_comment = findViewById(R.id.btn_send_comment);
        rv_comments = findViewById(R.id.rv_comments);
    }
}
