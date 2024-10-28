package com.example.datdoanonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datdoanonline.Activity.chitietsp;
import com.example.datdoanonline.Domain.FoodDomain;
import com.example.datdoanonline.Domain.ThucDonDomain;
import com.example.datdoanonline.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private List<ThucDonDomain> foodList;
    private OnFoodItemClickListener listener;
    public interface OnFoodItemClickListener {
        void onFoodItemClick(ThucDonDomain food,boolean goToComment);
    }

    public FoodListAdapter(List<ThucDonDomain> foodList, OnFoodItemClickListener listener, Context context) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        ThucDonDomain food = foodList.get(position);
        holder.bind(food, listener);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFoodName, tvFoodDescription, tvFoodPrice;
        private ImageView img_monan;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img_monan = itemView.findViewById(R.id.img_thucdon);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
        }

        public void bind(ThucDonDomain food, OnFoodItemClickListener listener) {

            tvFoodName.setText(food.getTenMonAn());
            tvFoodDescription.setText(food.getMoTa());
            tvFoodPrice.setText("Giá: " + food.getGia() + " VND");
            // Sử dụng Glide để tải ảnh từ URI
            Glide.with(itemView.getContext())
                    .load(Uri.parse(food.getAnhMinhHoa())) // Chuyển đổi chuỗi thành Uri
                    .into(img_monan); // Gán vào ImageView

            itemView.setOnClickListener(v -> {
                // Gọi listener để xử lý sự kiện nhấp chuột
                listener.onFoodItemClick(food, false); // Thay đổi thành true  nếu muốn chuyển đến CommentActivity.class
            });
        }
    }
}
