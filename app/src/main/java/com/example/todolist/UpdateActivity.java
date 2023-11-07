package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateActivity extends AppCompatActivity {

    private TextInputEditText edtTaskTitleUpdate, edtTaskContentUpdate;
    private Button btnUpdateTask, btnDeleteTask;

    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mapViews();
        addEvents();

        myTask = (MyTask) getIntent().getSerializableExtra("myTask");
        edtTaskTitleUpdate.setText(myTask.getTitle());
        edtTaskContentUpdate.setText(myTask.getContent());
    }

    private void mapViews() {
        edtTaskTitleUpdate = findViewById(R.id.edt_task_title_update);
        edtTaskContentUpdate = findViewById(R.id.edt_task_content_update);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
    }

    private void addEvents() {
        btnUpdateTask.setOnClickListener(view -> {
            myTask.setTitle(String.valueOf(edtTaskTitleUpdate.getText()));
            myTask.setContent(String.valueOf(edtTaskContentUpdate.getText()));

            DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
            long result = db.updateTask(myTask.getId(), myTask.getTitle(), myTask.getContent(), myTask.getComplete());

            if (result == -1) {
                Toast.makeText(UpdateActivity.this, "Cập nhật công việc thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UpdateActivity.this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteTask.setOnClickListener(view -> {
            confirmDeleteTaskDialog();
        });
    }

    private void confirmDeleteTaskDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateActivity.this);
        alertDialogBuilder.setTitle("Xóa " + myTask.getTitle() + "?");
        alertDialogBuilder.setMessage("Bạn có chắc muốn xóa?");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                db.deleteTask(myTask.getId());
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}