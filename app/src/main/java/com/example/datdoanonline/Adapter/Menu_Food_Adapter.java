package com.example.datdoanonline.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Domain.Menu_Food_Domain;
import com.example.datdoanonline.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Menu_Food_Adapter extends RecyclerView.Adapter<Menu_Food_Adapter.Menu_Food_ViewHolder> {
    private List<Menu_Food_Domain> menuList;
    private Context context;
    private OnItemClickListener listener;

    // Constructor với listener được truyền vào
    public Menu_Food_Adapter(Context context, List<Menu_Food_Domain> menuList, OnItemClickListener listener) {
        this.context = context;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Menu_Food_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_food, parent, false);
        return new Menu_Food_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Menu_Food_ViewHolder holder, int position) {
        Menu_Food_Domain foodItem = menuList.get(position);

        // Set data vào các TextView
        holder.tenMonAnTextView.setText(foodItem.getTenmonan());
        holder.moTaTextView.setText(foodItem.getMota());
        holder.giaTextView.setText(String.valueOf(foodItem.getGia()) + " VND");
        holder.danhMucTextView.setText(foodItem.getDanhmuc());
        holder.nangLuongTextView.setText(String.valueOf(foodItem.getNangluong()) + " Kcal");
        holder.thoiGianLamTextView.setText(foodItem.getTime() + " phút");
        holder.soLuongTextView.setText("Còn lại: " + String.valueOf(foodItem.getSl()));

        // Sử dụng Picasso để load hình ảnh
        Picasso.get().load(foodItem.getImg()).into(holder.anhMonAnImageView);

        // Thiết lập sự kiện click cho các nút
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(foodItem));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(foodItem));
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    // Interface để bắt sự kiện click
    public interface OnItemClickListener {
        void onEdit(Menu_Food_Domain menu_food);
        void onDelete(Menu_Food_Domain menu_food);  // Thêm sự kiện xóa
    }

    public static class Menu_Food_ViewHolder extends RecyclerView.ViewHolder {
        TextView tenMonAnTextView, moTaTextView, giaTextView, danhMucTextView, nangLuongTextView, thoiGianLamTextView, soLuongTextView;
        ImageView anhMonAnImageView;
        ImageButton btnEdit, btnDelete;

        @SuppressLint("WrongViewCast")
        public Menu_Food_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenMonAnTextView = itemView.findViewById(R.id.item_name);
            moTaTextView = itemView.findViewById(R.id.item_mota);
            giaTextView = itemView.findViewById(R.id.items_gia);
            danhMucTextView = itemView.findViewById(R.id.item_danhmuc);
            nangLuongTextView = itemView.findViewById(R.id.item_nangluong);
            thoiGianLamTextView = itemView.findViewById(R.id.item_tgian);
            soLuongTextView = itemView.findViewById(R.id.item_soluong);
            anhMonAnImageView = itemView.findViewById(R.id.item_anh);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
    public void updateFoodList(List<Menu_Food_Domain> newFoodList) {
        this.menuList.clear(); // Xóa danh sách cũ
        this.menuList.addAll(newFoodList); // Thêm danh sách mới vào
        notifyDataSetChanged(); // Thông báo để cập nhật UI
    }

}
