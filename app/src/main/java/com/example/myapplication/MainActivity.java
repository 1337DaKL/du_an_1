package com.example.a01_recyview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RoomAdapter adapter;
    private List<Room> roomList;
    private FloatingActionButton fab;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewRooms);
        fab = findViewById(R.id.fabAddRoom);

        roomList = new ArrayList<>();
        // Dữ liệu mẫu
        roomList.add(new Room("Nhom1", "Phòng 101", 2000000, false, "", ""));
        roomList.add(new Room("Nhom 1", "Phòng 102", 2500000, true, "Nguyễn Văn A", "0123456789"));

        adapter = new RoomAdapter(roomList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoomActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
    }

    @Override
    public void onItemClick(Room room, int position) {
        Intent intent = new Intent(MainActivity.this, RoomActivity.class);
        intent.putExtra("room", room);
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onItemLongClick(Room room, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, roomList.size());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Room room = (Room) data.getSerializableExtra("room");
            int position = data.getIntExtra("position", -1);

            if (requestCode == REQUEST_CODE_ADD) {
                roomList.add(room);
                adapter.notifyItemInserted(roomList.size() - 1);
            } else if (requestCode == REQUEST_CODE_EDIT && position != -1) {
                roomList.set(position, room);
                adapter.notifyItemChanged(position);
            }
        }
    }
}
