package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {

    private TextInputEditText edtTaskTitle, edtTaskContent;
    private Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mapViews();
        addEvents();
    }

    private void mapViews() {
        edtTaskTitle = findViewById(R.id.edt_task_title);
        edtTaskContent = findViewById(R.id.edt_task_content);
        btnAddTask = findViewById(R.id.btnAddTask);
    }

    private void addEvents() {
        btnAddTask.setOnClickListener(view -> {
            try {
                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                long result = db.addTask(String.valueOf(edtTaskTitle.getText()).trim(),
                        String.valueOf(edtTaskContent.getText()).trim());

                if (result == -1) {
                    Toast.makeText(AddActivity.this, "Thêm công việc thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "Thêm công việc thành công", Toast.LENGTH_SHORT).show();

                    // Clear text và focus vào EditText.
                    edtTaskTitle.setText("");
                    edtTaskContent.setText("");
                    edtTaskTitle.requestFocus();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}