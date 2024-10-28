package com.example.datdoanonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datdoanonline.Domain.DanhGiaDomain;
import com.example.datdoanonline.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<DanhGiaDomain> commentList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(DanhGiaDomain comment);
        void onDelete(DanhGiaDomain comment);
    }

    public CommentAdapter(Context context, List<DanhGiaDomain> commentList, OnItemClickListener listener) {
        this.context = context;
        this.commentList = commentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        DanhGiaDomain comment = commentList.get(position);
        holder.tvAuthor.setText("User " + comment.getMaNguoiDung());
        holder.tvTimestamp.setText(comment.getNgayDanhGia());
        holder.tvComment.setText(comment.getNhanXet());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(comment));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(comment));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvTimestamp, tvComment;
        ImageButton btnEdit, btnDelete;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
            tvComment = itemView.findViewById(R.id.tv_comment);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
