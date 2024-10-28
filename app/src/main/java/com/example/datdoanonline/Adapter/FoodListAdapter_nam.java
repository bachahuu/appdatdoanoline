package com.example.datdoanonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.datdoanonline.Activity.Trangchu_Activity;
import com.example.datdoanonline.Activity.chitietsp;
import com.example.datdoanonline.Domain.FoodDomain;
import com.example.datdoanonline.R;

import java.util.ArrayList;

public class FoodListAdapter_nam extends RecyclerView.Adapter<FoodListAdapter_nam.ViewHolder> {
    ArrayList<FoodDomain> items;
    Context context;

    public FoodListAdapter_nam(ArrayList<FoodDomain> items, Trangchu_Activity trangchuActivity) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food_list,parent,false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodDomain food = items.get(position);//bac
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.timeTxt.setText(items.get(position).getTime()+" min");
        holder.scoreTxt.setText(""+items.get(position).getScore());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);
       // Đặt OnClickListener để xử lý các nhấp chuột vào mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo một Intent để điều hướng đến chitietsp
                Intent intent = new Intent(context, chitietsp.class);
                // Pass the food details to chitietsp activity
                intent.putExtra("foodName", food.getTitle());
                intent.putExtra("foodPrice", food.getPrice());
                intent.putExtra("foodDescription", food.getDescription());
                intent.putExtra("foodImage", food.getPicUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt, timeTxt, scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}