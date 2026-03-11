package com.example.a01_recyview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RoomActivity extends AppCompatActivity {

    private EditText etId, etName, etPrice, etTenant, etPhone;
    private CheckBox cbIsRented;
    private Button btnSave;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        etId = findViewById(R.id.editTextRoomId);
        etName = findViewById(R.id.editTextRoomName);
        etPrice = findViewById(R.id.editTextRoomPrice);
        etTenant = findViewById(R.id.editTextTenantName);
        etPhone = findViewById(R.id.editTextPhoneNumber);
        cbIsRented = findViewById(R.id.checkBoxIsRented);
        btnSave = findViewById(R.id.buttonSave);

        Room room = (Room) getIntent().getSerializableExtra("room");
        position = getIntent().getIntExtra("position", -1);

        if (room != null) {
            etId.setText(room.getId());
            etId.setEnabled(false);
            etName.setText(room.getName());
            etPrice.setText(String.valueOf(room.getPrice()));
            etTenant.setText(room.getTenantName());
            etPhone.setText(room.getPhoneNumber());
            cbIsRented.setChecked(room.isRented());
        }

        btnSave.setOnClickListener(v -> {
            if (validate()) {
                Room newRoom = new Room(
                        etId.getText().toString(),
                        etName.getText().toString(),
                        Double.parseDouble(etPrice.getText().toString()),
                        cbIsRented.isChecked(),
                        etTenant.getText().toString(),
                        etPhone.getText().toString()
                );
                Intent intent = new Intent();
                intent.putExtra("room", newRoom);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean validate() {
        if (etId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã phòng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên phòng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá phòng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
