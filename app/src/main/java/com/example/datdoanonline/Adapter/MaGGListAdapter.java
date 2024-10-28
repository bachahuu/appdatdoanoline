package com.example.datdoanonline.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Activity.AdminMGG_Update_Admin;
import com.example.datdoanonline.Domain.DatabaseHelper;
import com.example.datdoanonline.Domain.maGiamGiaDomain;
import com.example.datdoanonline.R;

import java.util.List;

public class MaGGListAdapter extends RecyclerView.Adapter<MaGGListAdapter.MaGGViewHolder> {

    private List<maGiamGiaDomain> maGGList;
    private Context context;
    private DatabaseHelper databaseHelper;

    // Constructor
    public MaGGListAdapter(Context context, List<maGiamGiaDomain> maGGList) {
        this.context = context;
        this.maGGList = maGGList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MaGGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item của RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ma_giam_gia_item, parent, false);
        return new MaGGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaGGViewHolder holder, int position) {
        // Bind data từ maGGList vào view holder
        maGiamGiaDomain maGG = maGGList.get(position);
        holder.maGiamGiaTextView.setText(maGG.getMaGiamGia());
        holder.moTaTextView.setText(maGG.getMoTa());
        holder.phanTramGiamGiaTextView.setText(String.valueOf(maGG.getPhanTramGiamGia()) + "%");
        holder.ngayBatDauTextView.setText(maGG.getNgayBatDau());
        holder.ngayKetThucTextView.setText(maGG.getNgayKetThuc());
        holder.soLuongDaSuDungTextView.setText("Đã dùng: " + maGG.getSoLuongDaSuDung() + "/" + maGG.getSoLuongToiDa());
        holder.trangThaiTextView.setText(maGG.getTrangThai());

        // Thêm sự kiện click cho item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển trang AdminMGG_Update_Activity
                Intent intent = new Intent(context, AdminMGG_Update_Admin.class);

                // Truyền thông tin mã giảm giá qua Intent
                intent.putExtra("maGiamGia", maGG.getMaGiamGia());
                intent.putExtra("moTa", maGG.getMoTa());
                intent.putExtra("phanTramGiamGia", maGG.getPhanTramGiamGia());
                intent.putExtra("ngayBatDau", maGG.getNgayBatDau());
                intent.putExtra("ngayKetThuc", maGG.getNgayKetThuc());
                intent.putExtra("soLuongToiDa", maGG.getSoLuongToiDa());
                intent.putExtra("soLuongDaSuDung", maGG.getSoLuongDaSuDung());
                intent.putExtra("trangThai", maGG.getTrangThai());

                // Bắt đầu Activity AdminMGG_Update_Activity
                context.startActivity(intent);
            }
        });

        // Thêm sự kiện nhấn lâu để xác nhận xóa
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteConfirmationDialog(maGG, holder);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return maGGList.size();
    }

    // ViewHolder cho mỗi item của RecyclerView
    public static class MaGGViewHolder extends RecyclerView.ViewHolder {
        TextView maGiamGiaTextView, moTaTextView, phanTramGiamGiaTextView, ngayBatDauTextView, ngayKetThucTextView, soLuongDaSuDungTextView, trangThaiTextView;

        public MaGGViewHolder(@NonNull View itemView) {
            super(itemView);
            maGiamGiaTextView = itemView.findViewById(R.id.txtMaGiamGia);
            moTaTextView = itemView.findViewById(R.id.txtMoTa);
            phanTramGiamGiaTextView = itemView.findViewById(R.id.txtPhanTramGiamGia);
            ngayBatDauTextView = itemView.findViewById(R.id.txtNgayBatDau);
            ngayKetThucTextView = itemView.findViewById(R.id.txtNgayKetThuc);
            soLuongDaSuDungTextView = itemView.findViewById(R.id.txtSoLuongDaSuDung);
            trangThaiTextView = itemView.findViewById(R.id.txtTrangThai);
        }
    }

    private void showDeleteConfirmationDialog(maGiamGiaDomain maGG, MaGGViewHolder holder) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa Mã Giảm Giá")
                .setMessage("Bạn có chắc chắn muốn xóa mã giảm giá " + maGG.getMaGiamGia() + " không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy vị trí hiện tại của adapter
                        int position = holder.getAdapterPosition(); // Sử dụng getAdapterPosition() để lấy vị trí hiện tại
                        if (position != RecyclerView.NO_POSITION) { // Kiểm tra vị trí có hợp lệ không
                            // Xóa mã giảm giá khỏi cơ sở dữ liệu
                            databaseHelper.deleteDiscountCode(maGG.getMaGiamGia());

                            // Xóa item khỏi danh sách và thông báo cho adapter
                            maGGList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, maGGList.size()); // Cập nhật các item còn lại
                        }
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    // Cập nhật danh sách mã giảm giá
    public void updateList(List<maGiamGiaDomain> newList) {
        maGGList = newList;
        notifyDataSetChanged();
    }
}