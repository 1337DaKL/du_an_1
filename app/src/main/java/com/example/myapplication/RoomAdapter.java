package com.example.a01_recyview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Room room, int position);
        void onItemLongClick(Room room, int position);
    }

    public RoomAdapter(List<Room> roomList, OnItemClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.bind(room, listener);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewRoomName);
            tvPrice = itemView.findViewById(R.id.textViewRoomPrice);
            tvStatus = itemView.findViewById(R.id.textViewRoomStatus);
        }

        public void bind(final Room room, final OnItemClickListener listener) {
            tvName.setText(room.getName());
            tvPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            
            if (room.isRented()) {
                tvStatus.setText("Đã thuê");
                tvStatus.setBackgroundColor(Color.RED);
            } else {
                tvStatus.setText("Còn trống");
                tvStatus.setBackgroundColor(Color.GREEN);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(room, getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(room, getAdapterPosition());
                return true;
            });
        }
    }
}
