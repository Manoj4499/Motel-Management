package com.example.motel_management;// ExampleAdapter.java

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RoomsListAdapter extends RecyclerView.Adapter<RoomsListAdapter.RoomsViewHolder> {
    private final List<Room> dataList;
    private final Context context;

    public RoomsListAdapter(Context context, List<Room> RoomList) {
        this.context = context;
        this.dataList = RoomList;
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rooms, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsViewHolder holder, int position) {
        Room currentItem = dataList.get(position);
        holder.tvDesc.setText(currentItem.getDescription());
        holder.tvType.setText(currentItem.gettype());
        holder.tvPrice.setText("$ " + currentItem.getPrice());
        holder.tvDesc.setText(currentItem.getDescription());
        String isAvailable;
        if (currentItem.getOccupied()) {
            isAvailable = "Room Occupied";
        } else {
            isAvailable = "Room Available";
        }
        holder.tvOccupied.setText(isAvailable);
        Glide.with(context).load(currentItem.getImageUrl()).into(holder.iv);
        if (currentItem.getOccupied()) {
            holder.btMakeRes.setVisibility(View.GONE);
        } else {
            holder.btMakeRes.setVisibility(View.VISIBLE);
        }
        holder.btMakeRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Reservation.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("room", currentItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
