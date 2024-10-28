package com.example.datdoanonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Adapter.FoodListAdapter;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.ThucDonDomain;
import com.example.datdoanonline.R;

import java.util.List;

public class FoodListActivity extends AppCompatActivity implements FoodListAdapter.OnFoodItemClickListener {

    private RecyclerView recyclerView;
    private FoodListAdapter adapter;
    private List<ThucDonDomain> foodList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        db = new DatabaseHelper(this);
        foodList = db.getAllThucDon();  // Lấy tất cả món ăn từ bảng ThucDon
        recyclerView = findViewById(R.id.recyclerViewFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FoodListAdapter(foodList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFoodItemClick(ThucDonDomain food, boolean goToComment) {
        // Lấy mã món ăn từ đối tượng food
        int maMonAn = food.getMaMonAn();
        // Hiển thị mã món ăn (để kiểm tra)
        Toast.makeText(this, "Mã món ăn: " + maMonAn, Toast.LENGTH_SHORT).show();

        if (!goToComment) {
            // Ngược lại, chuyển đến chitietsp.class
            Intent intent = new Intent(this, chitietsp.class);
            intent.putExtra("foodName", food.getTenMonAn());  // Truyền tên món ăn đúng key
            intent.putExtra("foodPrice", food.getGia());      // Truyền giá món ăn
            intent.putExtra("foodDescription", food.getMoTa());
            intent.putExtra("foodImage", food.getAnhMinhHoa());  // Tên hình ảnh trong thư mục drawable
            intent.putExtra("maMonAn", maMonAn); // Truyền mã món ăn
            startActivity(intent);
        }
    }
}
