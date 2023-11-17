package com.example.motel_management;// ExampleAdapter.java

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MotelsListAdapter extends RecyclerView.Adapter<MotelsListAdapter.MotelsViewHolder> {
    private final List<Motel> dataList;
    private final Context context;

    public MotelsListAdapter(Context context, List<Motel> motelList) {
        this.context = context;
        this.dataList = motelList;
    }

    @NonNull
    @Override
    public MotelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motel, parent, false);
        return new MotelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MotelsViewHolder holder, int position) {
        Motel currentItem = dataList.get(position);
        holder.tvDesc.setText(currentItem.getDescription());
        holder.tvName.setText(currentItem.getName());
        holder.tvLocation.setText(currentItem.getLocation());
        Glide.with(context).load(currentItem.getImageUrl()).into(holder.iv);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MotelDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("motel", currentItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    static class MotelsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvLocation, tvDesc;
        public ImageView iv;
        public CardView cv;

        public MotelsViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            iv = itemView.findViewById(R.id.iv);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
