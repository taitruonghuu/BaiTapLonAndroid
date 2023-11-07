package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Views.
    private RecyclerView rcTasks;
    private FloatingActionButton btnAdd;
    // Database.
    private DatabaseHelper db;
    private ArrayList<MyTask> taskList;
    private MyTaskAdapter myTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(MainActivity.this);
        taskList = new ArrayList<>();

        mapViews();
        addEvents();

        myTaskAdapter = new MyTaskAdapter(MainActivity.this, taskList);
        rcTasks.setAdapter(myTaskAdapter);
        rcTasks.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void mapViews() {
        rcTasks = findViewById(R.id.rc_tasks);
        btnAdd = findViewById(R.id.add_button);
    }

    private void addEvents() {
        // Add task.
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (taskList != null) {
            taskList.clear();
        }
        Cursor cursor = db.getAllTasks();

        if (cursor == null) {
            Toast.makeText(MainActivity.this, "Không có công việc nào trong danh sách", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                MyTask myTask = new MyTask();
                myTask.setId(cursor.getInt(0));
                myTask.setTitle(cursor.getString(1));
                myTask.setContent(cursor.getString(2));
                myTask.setComplete(cursor.getInt(3));
                taskList.add(myTask);
            }
            myTaskAdapter.notifyDataSetChanged();
        }
    }
}