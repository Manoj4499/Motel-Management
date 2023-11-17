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

}
