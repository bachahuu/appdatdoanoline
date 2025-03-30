package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Adapter.FoodListAdapter;
import com.example.datdoanonline.Adapter.FoodListAdapter_nam;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.FoodDomain;
import com.example.datdoanonline.Domain.ThucDonDomain;
import com.example.datdoanonline.R;

import java.util.ArrayList;
import java.util.List;

public class Trangchu_Activity extends AppCompatActivity implements FoodListAdapter.OnFoodItemClickListener {
    private RecyclerView.Adapter FoodListAdapter_nam;
    private RecyclerView recyclerViewFood;
    private List<ThucDonDomain> food_list;
    ImageView giohang , donhang , thongtin,thucdon;
    TextView soluongmathang, hoTen, sdt;
    SQLiteDatabase DB;
    DatabaseHelper SQL ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQL = new DatabaseHelper(this);
        DB = SQL.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu1);
        giohang=findViewById(R.id.icon_giohang);
        soluongmathang = findViewById(R.id.activity_menu_sl);
        donhang = findViewById(R.id.img_donhang);
        thongtin = findViewById(R.id.img_thongtin);
        thucdon = findViewById(R.id.img_thucdon);

        // Khởi tạo TextView cho họ tên và số điện thoại
        hoTen = findViewById(R.id.textView5); // Họ tên
        sdt = findViewById(R.id.textView6); // Số điện thoại

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String hoten = sharedPreferences.getString("ho_ten", "Khách hàng"); // Lấy họ tên, mặc định là "Khách hàng"
        String soDienThoai = sharedPreferences.getString("so_dien_thoai", "Không có số điện thoại"); // Lấy số điện thoại, mặc định là "Không có số điện thoại"

        // Hiển thị thông tin vào TextView
        hoTen.setText(hoten);
        sdt.setText(soDienThoai);

        // Cập nhật số lượng mặt hàng lúc đầu khi Activity được tạo
        capNhatSoLuongMatHang();
        initRecyclerView();
        menu_quan();
        giohang_activity();
        DONHANG();
        thongtin_activity();
    }

    private void capNhatSoLuongMatHang() {
        // Lấy SharedPreferences để đọc thông tin người dùng đã đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null); // Lấy username
        int Manguoidung = SQL.getusername(username); // Lấy mã người dùng từ username

        // Gọi hàm dem_mat_hang để đếm số lượng mặt hàng trong giỏ hàng
        int soluong = SQL.dem_mat_hang(Manguoidung);

        // Cập nhật số lượng lên TextView soluongmathang
        soluongmathang.setText(String.valueOf(soluong));
    }

    private void menu_quan() {
     thucdon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(Trangchu_Activity.this,FoodListActivity.class);
             startActivity(intent);
         }
     });
    }

    private void DONHANG() {
        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_donhang = new Intent(Trangchu_Activity.this, history_order.class);
                startActivity(it_donhang);
            }
        });
    }

    private void giohang_activity() {
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Trangchu_Activity.this , shopping_cart.class);
                startActivity(it);
            }
        });
    }

    private void thongtin_activity(){
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_thongtin = new Intent(Trangchu_Activity.this,ShowUserInfo_Activity.class);
                startActivity(it_thongtin);
            }
        });
    }

    private void initRecyclerView(){
        //        ArrayList<ThucDonDomain> items = new ArrayList<>();
//        items.add(new FoodDomain("Cheese Burger",4,120,20,15,"fast_1","Thưởng thức Burger Phô Mai – sự kết hợp hoàn hảo giữa bánh mì mềm mịn, thịt bò nướng thơm lừng và lớp phô mai béo ngậy tan chảy. Mỗi miếng cắn mang đến hương vị đậm đà từ thịt tươi chất lượng cao, hòa quyện với phô mai vàng óng, kèm theo rau xà lách tươi xanh và cà chua mọng nước, tạo nên món ăn vừa thỏa mãn vị giác vừa no nê bụng đói. Burger Phô Mai – lựa chọn tuyệt vời cho những ai yêu thích sự đơn giản nhưng tinh tế."));
//        items.add(new FoodDomain("Pizza Peperoni",5,200,25,10,"fast_2","Pizza Pepperoni là một trong những loại pizza được yêu thích nhất trên toàn thế giới. Với lớp vỏ giòn rụm, phô mai mozzarella thơm béo tan chảy và những lát xúc xích pepperoni đậm đà, món pizza này mang đến hương vị hấp dẫn, cân bằng giữa độ béo ngậy của phô mai và vị cay nhẹ của pepperoni. Mỗi chiếc pizza pepperoni là sự kết hợp hoàn hảo giữa truyền thống ẩm thực Ý và hương vị đậm chất Mỹ, thích hợp cho những bữa tiệc gia đình, bạn bè hay những buổi tụ tập vui vẻ."));
//        items.add(new FoodDomain("Vegetable Pizza",4.5,100,30,13,"fast_3","Vegetable Pizza là sự kết hợp hoàn hảo giữa lớp vỏ bánh giòn tan, sốt cà chua đậm đà và các loại rau củ tươi ngon. Với nguyên liệu phong phú như ớt chuông, hành tây, cà chua, nấm, và ô liu, món pizza này không chỉ hấp dẫn về màu sắc mà còn bổ dưỡng. Mỗi miếng cắn đều mang đến hương vị thanh mát của rau củ, hoà quyện cùng phô mai mozzarella béo ngậy, làm cho Vegetable Pizza trở thành lựa chọn lý tưởng cho những ai yêu thích ẩm thực lành mạnh và cân bằng dinh dưỡng."));
        food_list = SQL.getAllThucDon();
        recyclerViewFood = findViewById(R.id.view1);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

//        FoodListAdapter_nam = new FoodListAdapter_nam(items);
//        recyclerViewFood.setAdapter(FoodListAdapter_nam);
        //bac
        FoodListAdapter food_list_adapter = new FoodListAdapter(food_list, this, this);
        recyclerViewFood.setAdapter(food_list_adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại số lượng mặt hàng mỗi khi Activity quay lại
        capNhatSoLuongMatHang();
    }


    @Override
    public void onFoodItemClick(ThucDonDomain food, boolean goToComment) {
        Intent intent;
        int maMonAn = food.getMaMonAn();
        // Hiển thị mã món ăn (để kiểm tra)
        Toast.makeText(this, "Mã món ăn: " + maMonAn, Toast.LENGTH_SHORT).show();
        if (!goToComment) {
            // Ngược lại, chuyển đến chitietsp.class
            intent = new Intent(this, chitietsp.class);
            intent.putExtra("foodName", food.getTenMonAn());  // Truyền tên món ăn đúng key
            intent.putExtra("foodPrice", food.getGia());      // Truyền giá món ăn
            intent.putExtra("foodDescription", food.getMoTa());
            intent.putExtra("maMonAn", maMonAn); // Truyền mã món ăn
            // Xử lý đặc biệt cho ảnh
            String imagePath = food.getAnhMinhHoa();
            if (imagePath.startsWith("drawable/")) {
                // Nếu là ảnh drawable, truyền nguyên chuỗi
                intent.putExtra("imageType", "drawable");
                intent.putExtra("foodImage", imagePath);
            } else {
                // Nếu là URI từ thiết bị
                intent.putExtra("imageType", "uri");
                intent.putExtra("foodImage", imagePath);
            }
            startActivity(intent);
        }
    }
}
