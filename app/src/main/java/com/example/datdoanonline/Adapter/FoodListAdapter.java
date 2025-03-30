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
            String imagePath = food.getAnhMinhHoa();
            Context context = itemView.getContext();
            if (imagePath.startsWith("drawable/")) {
                // ẢNH DRAWABLE
                String drawableName = imagePath.replace("drawable/", "").split("\\.")[0]; // Bỏ đuôi .jpg/png nếu có
                int resId = context.getResources().getIdentifier(
                        drawableName,
                        "drawable",
                        context.getPackageName()
                );

                Glide.with(context)
                        .load(resId)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(img_monan);

            } else {
                // ẢNH THIẾT BỊ - Xử lý cả Uri content:// và file://
                try {
                    Uri uri = Uri.parse(imagePath);
                    Glide.with(context)
                            .load(uri)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(img_monan);
                } catch (Exception e) {
                    Glide.with(context)
                            .load(R.drawable.ic_launcher_foreground)
                            .into(img_monan);
                }
            }
            itemView.setOnClickListener(v -> {
                // Gọi listener để xử lý sự kiện nhấp chuột
                listener.onFoodItemClick(food, false); // Thay đổi thành true  nếu muốn chuyển đến CommentActivity.class
            });
        }
    }
}
